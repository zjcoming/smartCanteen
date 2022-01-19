package com.swu.module_order.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.base.recyclerview.BaseAdapter
import com.base.recyclerview.BaseHolder
import com.swu.module_order.R
import com.swu.module_order.databinding.LeftMenuItemLayoutBinding
import com.base.bean.LeftMenuModel
import com.swu.module_order.widget.LeftMenuItemView

/**
 * Created by chenxiong
 * date 1/8/22
 */
class LeftMenuAdapter(private val context: Context, private val leftMenuData: List<LeftMenuModel>): BaseAdapter<LeftMenuItemLayoutBinding>() {
    private var curSelectPos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<LeftMenuItemLayoutBinding> {
        val leftMenuItemView = LeftMenuItemView(context)
        return BaseHolder(leftMenuItemView, leftMenuItemView.getVBinding())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: BaseHolder<LeftMenuItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(leftMenuData[position])
        initViewStatus(holder.binding){ binding->
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

    override fun initInnerClickListener(
        holder: BaseHolder<LeftMenuItemLayoutBinding>,
        position: Int
    ) {
        holder.binding.root.setOnClickListener {
            clickOutCallBack?.onItemClick(position) }
    }

}