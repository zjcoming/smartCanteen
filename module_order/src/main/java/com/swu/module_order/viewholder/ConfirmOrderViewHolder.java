package com.swu.module_order.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swu.module_order.R;

/**
 * Created by 刘金豪 on 2021/1/11
 * desc: 确认订单页面 的 商品Item
 */
public class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
    public ImageView foodImg;
    public TextView foodName;
    public TextView foodConsumption;//用量 大份 小份等等
    public TextView foodFlavor;//口味偏好 微辣等
    public TextView foodPrice;//价格
    public TextView foodCount;//商品数量
    public TextView foodSub;//减
    public TextView foodAdd;//加

    @SuppressLint("ResourceType")
    public ConfirmOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        foodName = itemView.findViewById(R.id.item_food_name);
        foodConsumption = itemView.findViewById(R.id.item_food_consumption);
        foodFlavor = itemView.findViewById(R.id.item_food_flavor);
        foodPrice = itemView.findViewById(R.id.item_food_price);
        foodCount = itemView.findViewById(R.id.item_food_count);
        foodImg = itemView.findViewById(R.id.item_food_img);
        foodSub = itemView.findViewById(R.id.item_food_sub);
        foodAdd = itemView.findViewById(R.id.item_food_add);
        foodSub.setVisibility(View.GONE);
        foodAdd.setVisibility(View.GONE);
    }
}