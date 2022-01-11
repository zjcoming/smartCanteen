package com.swu.module_order.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.BaseFragment
import com.base.recyclerview.BaseAdapter
import com.base.util.UIUtils
import com.base.util.updateLayoutParams
import com.swu.module_order.FloatDecoration
import com.swu.module_order.adapter.LeftMenuAdapter
import com.swu.module_order.adapter.RightMenuAdapter
import com.swu.module_order.databinding.FragmentChooseFoodBinding
import com.swu.module_order.databinding.ItemShopDetailsMenuRightGroupBinding
import com.swu.module_order.util.MockDataUtil
import com.swu.module_order.widget.CenterLayoutManager

class ChooseFoodFragment : BaseFragment<FragmentChooseFoodBinding>() {

    private val places = arrayOf("一楼", "二楼", "三楼")
    private val leftMenuData = MockDataUtil.getLeftMenuData()
    private val rightMenuData = MockDataUtil.getRightMenuData()
    private val leftMenuAdapter = LeftMenuAdapter(requireActivity(), leftMenuData)
    private val rightMenuAdapter = RightMenuAdapter(requireActivity(), rightMenuData)
    private val leftRvLayoutManager = CenterLayoutManager(requireActivity())
    private var mRvState = RecyclerView.State()
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
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun initListener() {
        binding.rvRightMenu.addItemDecoration(
            FloatDecoration(
                ItemShopDetailsMenuRightGroupBinding.inflate(UIUtils.getLayoutInflater(requireActivity()),binding.rvRightMenu,false),
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
    }

}