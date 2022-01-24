package com.swu.module_order.fragment

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.BaseFragment
import com.base.bean.FoodListBean
import com.base.recyclerview.BaseAdapter
import com.base.util.UIUtils
import com.base.util.updateLayoutParams
import com.common.handler.RequestHandler
import com.common.requestbase.AppObserver
import com.common.requestbase.ResponseModel
import com.swu.module_order.FloatDecoration
import com.swu.module_order.R
import com.swu.module_order.SearchActivity
import com.swu.module_order.adapter.LeftMenuAdapter
import com.swu.module_order.adapter.RightMenuAdapter
import com.swu.module_order.databinding.FragmentChooseFoodBinding
import com.swu.module_order.databinding.ItemShopDetailsMenuRightGroupBinding
import com.swu.module_order.util.MockDataUtil
import com.swu.module_order.widget.CenterLayoutManager

class ChooseFoodFragment(private val mContext: Context) : BaseFragment<FragmentChooseFoodBinding>() {

    private val places = arrayOf("一楼", "二楼", "三楼")
    private val leftMenuData = MockDataUtil.getLeftMenuData()
    private val rightMenuData = MockDataUtil.getRightMenuData()
    private val leftMenuAdapter = LeftMenuAdapter(mContext, leftMenuData)
    private val rightMenuAdapter = RightMenuAdapter(mContext, rightMenuData)
    private val leftRvLayoutManager = CenterLayoutManager(mContext)
    private var mRvState = RecyclerView.State()

    override fun initData() {
        RequestHandler.fetchFoodList(object : AppObserver<ResponseModel<FoodListBean>>() {
            override fun onData(o: ResponseModel<FoodListBean>) {
                Log.e("cx",o.toString())
            }
        }, 1)
    }

    override fun initViews() {
        for (i in places.indices) {
            binding.placeTab.addTab(binding.placeTab.newTab().setText(places[i]))
        }
        binding.rvLeftMenu.apply {
            adapter = leftMenuAdapter
            layoutManager = leftRvLayoutManager
        }
        binding.rvRightMenu.apply {
            adapter = rightMenuAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    override fun initListener() {
        binding.rvRightMenu.addItemDecoration(
            FloatDecoration(
                ItemShopDetailsMenuRightGroupBinding.inflate(
                    UIUtils.getLayoutInflater(mContext),
                    binding.rvRightMenu,
                    false
                ),
                object : FloatDecoration.DecorationCallback<ItemShopDetailsMenuRightGroupBinding> {
                    override fun getDecorationFlag(position: Int): String {
                        return rightMenuData[position].groupName
                    }

                    override fun onBindView(
                        binding: ItemShopDetailsMenuRightGroupBinding,
                        position: Int
                    ) {
                        binding.tvGroupName.text = rightMenuData[position].groupName
                    }

                }
            )
        )
        binding.scrollView.post {
            binding.rvLeftMenu.updateLayoutParams<ViewGroup.LayoutParams> {
                height = binding.scrollView.height
            }
            binding.rvRightMenu.updateLayoutParams<ViewGroup.LayoutParams> {
                height = binding.scrollView.height
            }
        }
        leftMenuAdapter.setItemClickOutCallBack(object : BaseAdapter.OnClickOutCallBack {
            override fun onItemClick(position: Int) {
                for (i in rightMenuData.indices) {
                    if (leftMenuData[position].title == rightMenuData[i].groupName) {
                        (binding.rvRightMenu.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                            i, 0
                        )
                        leftMenuAdapter.reFreshSelectStatus(position)
                        leftMenuAdapter.notifyDataSetChanged()
                        break
                    }
                }
            }
        })
        binding.rvRightMenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position =
                    (binding.rvRightMenu.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (leftMenuData[leftMenuAdapter.getCurSelectPos()].title != rightMenuData[position].groupName) {
                    for (i in leftMenuData.indices) {
                        if (leftMenuData[i].title == rightMenuData[position].groupName) {
                            leftMenuAdapter.reFreshSelectStatus(i)
                            leftMenuAdapter.notifyDataSetChanged()
                            leftRvLayoutManager.smoothScrollToPosition(
                                binding.rvLeftMenu,
                                mRvState,
                                leftMenuAdapter.getCurSelectPos()
                            )
                            break
                        }
                    }
                }
            }
        })
        binding.tvSearch.setOnClickListener {
            Intent(activity, SearchActivity::class.java).also { startActivity(it) }
            activity?.overridePendingTransition(R.anim.page_from_bottom_to_top_in, 0)
        }
        rightMenuAdapter.setItemClickOutCallBack(object : BaseAdapter.OnClickOutCallBack {
            override fun onItemClick(position: Int) {
                jumpToFragment(FoodDetailFragment(), R.anim.page_from_right_to_left_in)
            }
        })
    }

}