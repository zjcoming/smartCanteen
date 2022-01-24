package com.swu.module_order

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.BaseActivity
import com.base.BaseFragment
import com.common.constants.RouteConstants
import com.common.util.FragmentUtil
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.swu.module_order.fragment.ChooseFoodFragment
import com.swu.module_order.widget.BottomShopCartLayout

@Route(path = RouteConstants.Module_order.PAGER_CHOOSE_FOOD)
class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {

    private val bottomShopCartLayout: BottomShopCartLayout by lazy {
        BottomShopCartLayout(this)
    }

    override fun initData() {
        FragmentUtil.getInstance().startFragment(
            this,
            ChooseFoodFragment(this).apply {
                setJumpFragmentCallBack(
                    object : BaseFragment.FragmentJumpListener {
                        override fun jumpToFragment(
                            fragment: Fragment,
                            anim: Int
                        ) {
                            FragmentUtil.getInstance().startFragment(
                                requireActivity(), fragment, R.id.container,
                                anim
                            )
                        }
                    }
                )
            },
            R.id.container
        )
    }

    override fun onResume() {
        super.onResume()
        bottomShopCartLayout.attach(this)
    }

    override fun onPause() {
        super.onPause()
        bottomShopCartLayout.detach()
    }
    override fun initListener() {

    }


}