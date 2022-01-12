package com.swu.module_order.fragment

import com.base.BaseFragment
import com.common.util.FragmentUtil
import com.swu.module_order.R
import com.swu.module_order.databinding.FragmentFoodDetailBinding

class FoodDetailFragment : BaseFragment<FragmentFoodDetailBinding>() {

    override fun initViews() {

    }

    override fun initListener() {
        binding.backBtn.setOnClickListener {
            FragmentUtil.getInstance().closeFragment(this, R.anim.page_from_top_to_bottom_out)
        }
    }
}