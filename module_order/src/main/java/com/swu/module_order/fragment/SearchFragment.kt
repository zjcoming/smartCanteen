package com.swu.module_order.fragment

import com.base.BaseFragment
import com.common.util.FragmentUtil
import com.swu.module_order.R
import com.swu.module_order.databinding.FragmentSearchBinding
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun initViews() {

    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            FragmentUtil.getInstance().closeFragment(this, R.anim.page_from_top_to_bottom_out)
        }
    }


}