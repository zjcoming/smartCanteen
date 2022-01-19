package com.swu.module_order.widget

import android.content.Context
import com.base.recyclerview.BaseCustomView
import com.swu.module_order.databinding.LeftMenuItemLayoutBinding
import com.base.bean.LeftMenuModel

/**
 * Created by chenxiong
 * date 1/8/22
 */
class LeftMenuItemView(context: Context): BaseCustomView<LeftMenuModel, LeftMenuItemLayoutBinding>(context) {
    override fun setDataToView(data: LeftMenuModel) {
        binding.menuTitle.text = data.title
    }

}