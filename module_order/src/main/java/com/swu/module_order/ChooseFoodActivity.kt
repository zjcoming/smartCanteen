package com.swu.module_order

import com.base.BaseActivity
import com.common.util.FragmentUtil
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.swu.module_order.fragment.ChooseFoodFragment
import com.swu.module_order.fragment.ConfirmOrderFragment

class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {

    override fun initData() {
        FragmentUtil.getInstance().startFragment(
            this,
            ConfirmOrderFragment(this).apply {
                setJumpFragmentCallBack { fragment, anim ->
                    FragmentUtil.getInstance().startFragment(
                        requireActivity(), fragment, R.id.container,
                        anim
                    )
                }
            },
            R.id.container
        )
    }

    override fun initListener() {

    }


}