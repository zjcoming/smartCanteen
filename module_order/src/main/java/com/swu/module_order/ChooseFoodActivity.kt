package com.swu.module_order

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseActivity
import com.common.constants.DiningWay
import com.common.constants.RouteConstants
import com.common.constants.TargetFragmentConstants
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.swu.module_order.fragment.ChooseFoodFragment
import com.swu.module_order.fragment.ConfirmOrderFragment
import com.swu.module_order.view_model.FoodPageViewModel
import com.swu.module_order.view_model.FoodViewModelFactory
import com.swu.module_order.widget.BottomShopCartDetailDialog
import com.swu.module_order.widget.BottomShopCartLayout

@Route(path = RouteConstants.Module_order.PAGER_CHOOSE_FOOD)
class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {

    @Autowired(name = DiningWay.DINING_WAY)
    @JvmField
    var diningWay: String? = null

    private val pageViewModel: FoodPageViewModel by lazy {
        ViewModelProvider(this, FoodViewModelFactory(this)).get(FoodPageViewModel::class.java)
    }



    override fun initData() {
        ARouter.getInstance().inject(this)
//        FragmentUtil.getInstance().startFragment(
//            this,
//            ChooseFoodFragment(this).apply {
//                setJumpFragmentCallBack(
//                    object : BaseFragment.FragmentJumpListener {
//                        override fun jumpToFragment(fragment: Fragment, anim: Int) {
//                            FragmentUtil.getInstance().startFragment(
//                                requireActivity(), fragment, R.id.container,
//                                anim
//                            )
//                        }
//                    }
//                )
//            },
//            R.id.container
//        )
        openFragment(ChooseFoodFragment(this))
        TargetFragmentConstants.CurrentChooseFoodActivity = TargetFragmentConstants.CHOOSE_FOOD_FRAGMENT;

        pageViewModel.diningWay = diningWay
        Log.e("cx", diningWay ?: "Null")
    }

    override fun onResume() {
        super.onResume()
        pageViewModel.getShopCart().attach(this)
        Log.e("cx", "ChooseFoodActivity-pageViewModel：${pageViewModel.hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        pageViewModel.getShopCart().detach()
    }
    override fun initListener() {
        pageViewModel.getShopCart().setOnBottomCarClickListener(object :
            BottomShopCartLayout.BottomCarClickListener {
            override fun onBottomCarClick() {
                BottomShopCartDetailDialog(this@ChooseFoodActivity).show()
            }
        })
        pageViewModel.getShopCart().setSettlementCallBack {
            openFragment(ConfirmOrderFragment(pageViewModel))
        }


    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }


}