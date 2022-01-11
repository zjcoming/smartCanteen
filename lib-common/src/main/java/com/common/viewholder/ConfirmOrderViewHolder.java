package com.common.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.ApplicationContext;
import com.swu.lib_common.R;

/**
 * Created by 刘金豪 on 2021/1/11
 * desc: 确认订单页面的商品Item
 */
public class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView mTv;
    @SuppressLint("ResourceType")
    public ConfirmOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        mTv = (TextView) itemView.findViewById(R.layout.confirm_order_viewholder_layout);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApplicationContext.getContext(),mTv.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
