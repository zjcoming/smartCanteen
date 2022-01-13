package com.swu.module_order

import androidx.fragment.app.Fragment
import com.base.BaseActivity
import com.base.BaseFragment
import com.common.util.FragmentUtil
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.swu.module_order.fragment.ChooseFoodFragment

class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {

    override fun initData() {
        FragmentUtil.getInstance().startFragment(
            this,
            ChooseFoodFragment(this).apply {
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
            },
            R.id.container
        )
    }

    override fun initListener() {

    }


}