package com.swu.module_order

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.BaseActivity
import com.base.recyclerview.BaseAdapter
import com.base.util.UIUtils
import com.base.util.updateLayoutParams
import com.common.util.FragmentUtil
import com.google.android.material.tabs.TabLayout
import com.swu.module_order.adapter.LeftMenuAdapter
import com.swu.module_order.adapter.RightMenuAdapter
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.swu.module_order.databinding.ItemShopDetailsMenuRightGroupBinding
import com.swu.module_order.fragment.BuyCarBottomFragment
import com.swu.module_order.fragment.BuyingCarFragment
import com.swu.module_order.fragment.ConfirmOrderFragment
import com.swu.module_order.util.MockDataUtil
import com.swu.module_order.widget.CenterLayoutManager

class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {
    lateinit var mFragmentUtil:FragmentUtil
    private val places = arrayOf("一楼", "二楼", "三楼")
    private val leftMenuData = MockDataUtil.getLeftMenuData()
    private val rightMenuData = MockDataUtil.getRightMenuData()
    private val leftMenuAdapter = LeftMenuAdapter(this, leftMenuData)
    private val rightMenuAdapter = RightMenuAdapter(this, rightMenuData)
    private val leftRvLayoutManager = CenterLayoutManager(this)
    private var mRvState = RecyclerView.State()
    private val mCarFragment = BuyingCarFragment()
    private val mFloatCar = BuyCarBottomFragment()
    private val mConfirmOrderFragment = ConfirmOrderFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun initData() {
        //FragmentUtil
        mFragmentUtil = FragmentUtil(binding.fragmentContainer.id);
        mFragmentUtil.bind(this);

        for (i in places.indices) {
            binding.placeTab.addTab(binding.placeTab.newTab().setText(places[i]))
        }
        binding.rvLeftMenu.apply {
            adapter = leftMenuAdapter
            layoutManager = leftRvLayoutManager
        }
        binding.rvRightMenu.apply {
            adapter = rightMenuAdapter
            layoutManager = LinearLayoutManager(this@ChooseFoodActivity)
        }
//        mFragmentUtil.startFragment(mCarFragment)
    }

    override fun initListener() {
        binding.rvRightMenu.addItemDecoration(
            FloatDecoration(
                ItemShopDetailsMenuRightGroupBinding.inflate(UIUtils.getLayoutInflater(this),binding.rvRightMenu,false),
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
        leftMenuAdapter.setItemClickCallBack(object : BaseAdapter.OnClickCallBack {
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
                val position = (binding.rvRightMenu.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
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

        binding.placeTab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1){
                    mFragmentUtil.startFragment(mCarFragment)
                }else if (tab?.position == 2){
                    mFragmentUtil.closeFragment(mCarFragment)
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

}