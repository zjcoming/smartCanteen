package com.swu.module_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.common.viewholder.ConfirmOrderViewHolder;
import com.swu.module_order.R;

import java.util.ArrayList;

/**
 * Created by 刘金豪 on 2021/1/11
 * desc: 确定订单页面，商品的Adapter
 */
public class ConfirmOrderGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mDatas;

    public ConfirmOrderGoodsAdapter(Context mContext,ArrayList<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.confirm_order_viewholder_layout,parent,false);
        return new ConfirmOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConfirmOrderViewHolder viewHolder = (ConfirmOrderViewHolder)holder;
        viewHolder.mTv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
