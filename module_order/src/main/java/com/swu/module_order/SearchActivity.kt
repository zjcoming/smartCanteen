package com.swu.module_order

import com.base.BaseActivity
import com.swu.module_order.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override fun initData() {

    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.page_alpha_in,R.anim.page_from_top_to_bottom_out)
        }
    }
}