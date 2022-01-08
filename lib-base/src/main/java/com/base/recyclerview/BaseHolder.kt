package com.base.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chenxiong
 * date 11/26/21
 */
/*
 * 在onCreateHolder中根据viewType来决定是什么 CustomView，BaseHolder仅仅只是做了一层包装
 * 每一个Item长什么样，取决于 mItemView 也就是 ICustomView的具体实现
   public class MyAdapter extends RecyclerView.Adapter<BaseHolder> {
    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseHolder(new MyView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bind(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
 */
class BaseHolder(itemView: ICustomView<*>) : RecyclerView.ViewHolder(itemView as View) {

    private  var mItemView: ICustomView<IBaseCustomViewModel> = itemView as ICustomView<IBaseCustomViewModel>

    fun bind(viewModelI: IBaseCustomViewModel) {
        mItemView.setData(viewModelI)
    }


}