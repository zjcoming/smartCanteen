package com.swu.module_order.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.common.bean.FoodItemBean;
import com.swu.module_order.R;
import com.swu.module_order.viewholder.ConfirmOrderViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘金豪 on 2021/1/11
 * desc: 确定订单页面，商品的Adapter
 */
public class ConfirmOrderGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<FoodItemBean> mDatas;

    public ConfirmOrderGoodsAdapter(Context mContext, List<FoodItemBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_buyingcar_layout,parent,false);
        return new ConfirmOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConfirmOrderViewHolder viewHolder = (ConfirmOrderViewHolder)holder;
        FoodItemBean foodItemBean = mDatas.get(position);
        viewHolder.foodName.setText(foodItemBean.getFoodName());
        viewHolder.foodCount.setText(foodItemBean.getFoodCount()+" 份");
        viewHolder.foodImg.setImageURI(Uri.parse(foodItemBean.getFoodImgUrl()));
        viewHolder.foodConsumption.setText(foodItemBean.getFoodConsumption());
        viewHolder.foodFlavor.setText(foodItemBean.getFoodFlavor());
        viewHolder.foodPrice.setText("价格:"+foodItemBean.getFoodPrice().toString());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
