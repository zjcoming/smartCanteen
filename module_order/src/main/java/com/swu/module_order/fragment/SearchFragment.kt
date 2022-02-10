package com.swu.module_order.fragment

import com.base.BaseFragment
import com.base.FragmentUtil
import com.base.UIUtils
import com.common.util.HandlerManager
import com.swu.module_order.R
import com.swu.module_order.databinding.FragmentSearchBinding
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun initViews() {

    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            if (UIUtils.getInputMethodManager().isActive) {
                UIUtils.hideKeyBoard(binding.etSearch)
                HandlerManager.doInUIThread(40){
                    FragmentUtil.getInstance().closeFragment(this, R.anim.page_from_top_to_bottom_out)
                }
            } else {
                FragmentUtil.getInstance().closeFragment(this, R.anim.page_from_top_to_bottom_out)
            }
        }
    }
}