package com.base.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chenxiong
 * date 11/26/21
 */
class BaseHolder(private val mItemView: ICustomView<IBaseCustomViewModel>) :
    RecyclerView.ViewHolder(mItemView as View) {

    fun bind(viewModel: IBaseCustomViewModel) {
        mItemView.setData(viewModel)
    }
}