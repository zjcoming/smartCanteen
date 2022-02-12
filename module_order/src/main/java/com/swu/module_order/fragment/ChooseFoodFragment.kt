package com.swu.module_order.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.BaseFragment
import com.base.UIUtils
import com.base.bean.FoodListPageBean
import com.base.inflateBindingWithGeneric
import com.base.recyclerview.BaseAdapter
import com.common.smartcanteen_event_bus.SmartCanteenEventCallBack
import com.base.updateLayoutParams
import com.common.handler.RequestHandler
import com.common.loadsir.ErrorCallback
import com.common.loadsir.LoadingCallback
import com.common.requestbase.AppObserver
import com.common.requestbase.ResponseModel
import com.common.smartcanteen_event_bus.SmartCanteenEvent
import com.common.smartcanteen_event_bus.SmartCanteenEventController
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.swu.module_order.*
import com.swu.module_order.adapter.LeftMenuAdapter
import com.swu.module_order.adapter.RightMenuAdapter
import com.swu.module_order.databinding.FragmentChooseFoodBinding
import com.swu.module_order.databinding.ItemShopDetailsMenuRightGroupBinding
import com.swu.module_order.util.MockDataUtil
import com.swu.module_order.widget.CenterLayoutManager

class ChooseFoodFragment(private val mContext: Context) : BaseFragment<FragmentChooseFoodBinding>(), SmartCanteenEventCallBack,
    OnRefreshListener {

    private val places = arrayOf("一楼", "二楼", "三楼")
    private val leftMenuData = MockDataUtil.getLeftMenuData()
    private val rightMenuData = MockDataUtil.getRightMenuData()
    private val leftMenuAdapter = LeftMenuAdapter(mContext)
    private val rightMenuAdapter = RightMenuAdapter(mContext)
    private val leftRvLayoutManager = CenterLayoutManager(mContext)
    private var mRvState = RecyclerView.State()

    private lateinit var mLoadService: LoadService<*>

    override fun initData() {
        FoodMenuFetchHelper.fetchMainMenuData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mLoadService = LoadSir.getDefault().register(binding.root) {
            mLoadService.showCallback(LoadingCallback::class.java)
            FoodMenuFetchHelper.fetchMainMenuData()
        }
        return mLoadService.loadLayout
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
        SmartCanteenEventController.registerCallBack(SmartCanteenEvent.FETCH_FOOD_DATA, this)
        binding.smartRefresh.setOnRefreshListener(this)
    }

    override fun onEventChange(param: Map<*, *>?) {
        if (FoodMenuFetchHelper.fetchDataSuccess) {
            leftMenuAdapter.setLeftMenuData(FoodDataManager.sInstance.leftMenuList)
            rightMenuAdapter.setRightMenuData(FoodDataManager.sInstance.rightMenuList)
            mLoadService.showSuccess()
            binding.smartRefresh.finishRefresh()
        } else {
            mLoadService.showCallback(ErrorCallback::class.java)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        FoodMenuFetchHelper.fetchMainMenuData()
    }

    override fun onDestroy() {
        super.onDestroy()
        SmartCanteenEventController.unregisterCallBack(this)
    }

}