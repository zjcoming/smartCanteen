package com.base.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by chenxiong
 * date 1/8/22
 */
abstract class BaseAdapter<VB: ViewBinding> : RecyclerView.Adapter<BaseHolder<VB>>() {

    protected var clickOutCallBack: OnClickOutCallBack? = null

    fun setItemClickOutCallBack(onClickOutCallBack: OnClickOutCallBack) {
        clickOutCallBack = onClickOutCallBack
    }

    override fun onBindViewHolder(holder: BaseHolder<VB>, position: Int) {
        initInnerClickListener(holder, position)
    }

    inline fun <reified VB> initViewStatus(vb: VB, block:(VB) -> Unit?) {
             block.invoke(vb)
    }

    interface OnClickOutCallBack{
        fun onItemClick(position: Int)
    }

    protected open fun initInnerClickListener(holder: BaseHolder<VB>, position: Int) {}

}