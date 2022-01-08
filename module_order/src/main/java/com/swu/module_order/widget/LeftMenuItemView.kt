package com.swu.module_order.widget

import android.content.Context
import com.base.recyclerview.BaseCustomView
import com.swu.module_order.databinding.LeftMenuItemLayoutBinding
import com.swu.module_order.model.LeftMenuBean

/**
 * Created by chenxiong
 * date 1/8/22
 */
class LeftMenuItemView(context: Context): BaseCustomView<LeftMenuBean, LeftMenuItemLayoutBinding>(context) {
    override fun setDataToView(data: LeftMenuBean) {
        binding.menuTitle.text = data.title
    }

}