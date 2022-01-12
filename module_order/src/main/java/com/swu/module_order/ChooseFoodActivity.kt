package com.swu.module_order

import com.base.BaseActivity
import com.common.util.FragmentUtil
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.swu.module_order.fragment.ChooseFoodFragment

class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {

    override fun initData() {
        FragmentUtil.getInstance().startFragment(
            this,
            ChooseFoodFragment(this).apply {
                setJumpFragmentCallBack {
                    FragmentUtil.getInstance().startFragment(
                        requireActivity(), it, R.id.container,
                        R.anim.page_from_bottom_to_top_in
                    )
                }
            },
            R.id.container
        )
    }

    override fun initListener() {

    }


}