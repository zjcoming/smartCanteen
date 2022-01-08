package com.base.recyclerview

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chenxiong
 * date 1/8/22
 */
abstract class BaseAdapter: RecyclerView.Adapter<BaseHolder>() {

    protected var clickCallBack: OnClickCallBack? = null

    fun setItemClickCallBack(onClickCallBack: OnClickCallBack) {
        clickCallBack = onClickCallBack
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickCallBack?.onItemClick(position)
        }
        //如果需要在Adapter内部设置点击事件的回调，请在super.onBindViewHolder(holder, position)前设置监听
    }

    inline fun <reified VB> initViewStatus(vb: VB, block:(VB) -> Unit?) {
             block.invoke(vb)
    }

    interface OnClickCallBack{
        fun onItemClick(position: Int)
    }
}