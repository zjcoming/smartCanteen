package com.swu.module_order.adapter

import android.content.Context
import android.view.ViewGroup
import com.base.recyclerview.BaseAdapter
import com.base.recyclerview.BaseHolder
import com.swu.module_order.databinding.RightMenuItemLayoutBinding
import com.base.bean.RightMenuModel
import com.swu.module_order.widget.RightMenuItemView

/**
 * Created by chenxiong
 * date 1/8/22
 */
class RightMenuAdapter(private val context: Context): BaseAdapter<RightMenuItemLayoutBinding>() {

    private val rightMenuData: MutableList<RightMenuModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<RightMenuItemLayoutBinding> {
        val rightMenuItemView = RightMenuItemView(context)
        return BaseHolder(rightMenuItemView, rightMenuItemView.getVBinding())
    }

    override fun onBindViewHolder(holder: BaseHolder<RightMenuItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(rightMenuData[position])
    }

    override fun getItemCount() = rightMenuData.size

    override fun initInnerClickListener(
        holder: BaseHolder<RightMenuItemLayoutBinding>,
        position: Int
    ) {
        holder.binding.root.setOnClickListener {
            clickOutCallBack?.onItemClick(position)
        }
    }

    fun setRightMenuData(datas: List<RightMenuModel>) {
        rightMenuData.clear()
        rightMenuData.addAll(datas)
        notifyDataSetChanged()
    }
}