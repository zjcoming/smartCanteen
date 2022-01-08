package com.swu.module_order.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.recyclerview.BaseAdapter
import com.base.recyclerview.BaseHolder
import com.swu.module_order.model.RightMenuBean
import com.swu.module_order.util.MockDataUtil
import com.swu.module_order.widget.RightMenuItemView

/**
 * Created by chenxiong
 * date 1/8/22
 */
class RightMenuAdapter(private val context: Context, private val rightMenuDatas: List<RightMenuBean>): BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return BaseHolder(RightMenuItemView(context))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(rightMenuDatas[position])
    }

    override fun getItemCount() = rightMenuDatas.size
}