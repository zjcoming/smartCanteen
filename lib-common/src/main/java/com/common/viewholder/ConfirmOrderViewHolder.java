package com.common.viewholder;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.ApplicationContext;
import com.base.util.UIUtils;
import com.swu.lib_common.R;

/**
 * Created by 刘金豪 on 2021/1/11
 * desc: 确认订单页面 的 商品Item
 */
public class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView mTv;
    @SuppressLint("ResourceType")
    public ConfirmOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        mTv = (TextView) itemView.findViewById(R.id.confirm_order_item_tv);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ljh",mTv.getText().toString());
                //UIUtils.INSTANCE.showToast(mTv.getText().toString());
                //Toast.makeText(ApplicationContext.getContext(),mTv.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
