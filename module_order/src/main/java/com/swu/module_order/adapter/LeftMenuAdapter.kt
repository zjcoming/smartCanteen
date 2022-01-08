package com.swu.module_order.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.base.recyclerview.BaseAdapter
import com.base.recyclerview.BaseCustomView
import com.base.recyclerview.BaseHolder
import com.swu.module_order.R
import com.swu.module_order.databinding.LeftMenuItemLayoutBinding
import com.swu.module_order.model.LeftMenuBean
import com.swu.module_order.util.MockDataUtil
import com.swu.module_order.widget.LeftMenuItemView

/**
 * Created by chenxiong
 * date 1/8/22
 */
class LeftMenuAdapter(private val context: Context, private val leftMenuData: List<LeftMenuBean>): BaseAdapter() {
    private var curSelectPos = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return BaseHolder(LeftMenuItemView(context))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(leftMenuData[position])
        initViewStatus((holder.itemView as BaseCustomView<LeftMenuBean, LeftMenuItemLayoutBinding>).getMBinding()){
                binding->
            binding.root.setBackgroundColor(
                if (leftMenuData[position].selected) Color.parseColor("#FFFFFF") else context.getColor(
                    R.color.rv_left_bg_not_selected)
            )
        }
    }

    fun reFreshSelectStatus(position: Int) {
        leftMenuData[curSelectPos].selected = false
        leftMenuData[position].selected = true
        curSelectPos = position
    }

    override fun getItemCount() = leftMenuData.size

    fun getCurSelectPos() = curSelectPos

}