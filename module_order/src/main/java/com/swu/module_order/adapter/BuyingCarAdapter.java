package com.swu.module_order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.ApplicationContext;
import com.swu.module_order.R;
import com.swu.module_order.model.BuyingCarBean;

import java.util.List;

/**
 * @ClassName BuyingCarAdapter
 * @Author zhangJun
 * @Date 2022/1/10 7:53 下午
 * @Description
 */
public class BuyingCarAdapter extends RecyclerView.Adapter<BuyingCarAdapter.BuyingCarViewHolder>{

    private OnClickCallback onClickListener;
    private Context mContext;

    public void setOnClickListener(OnClickCallback onClickListener) {
        this.onClickListener = onClickListener;
    }

    private List<BuyingCarBean> carData;

    public BuyingCarAdapter(List<BuyingCarBean> carData,Context context) {
        this.carData = carData;
        mContext = context;
    }

    @NonNull
    @Override
    public BuyingCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_buyingcar_layout,
                parent,false);
        return new BuyingCarViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyingCarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(carData.get(position).getFoodName());
        holder.scale.setText(carData.get(position).getScale());
        holder.favor.setText(carData.get(position).getFavor());
        holder.price.setText(carData.get(position).getFoodPrice());
        holder.count.setText(String.valueOf(carData.get(position).getFoodCount()));
        //item事件点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener!=null){
                    onClickListener.onItemClick(view, position);
                }
            }
        });
        //添加
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener!=null){
                    onClickListener.onAddItemClick(view,position);
                }
            }
        });
        //删除购物车商品
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener!=null){
                    onClickListener.onSubItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (carData != null) {
            return carData.size();
        }
        return 0;
    }

     static class BuyingCarViewHolder extends RecyclerView.ViewHolder{
        private ImageView foodImg;
        private TextView name;
        private TextView scale;
        private TextView favor;
        private TextView price;
        private TextView count;
        private TextView add;
        private TextView sub;
        public BuyingCarViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            scale = itemView.findViewById(R.id.tv_scale);
            favor = itemView.findViewById(R.id.tv_favor);
            price = itemView.findViewById(R.id.tv_price);
            count = itemView.findViewById(R.id.tv_count);
            add = itemView.findViewById(R.id.tv_add);
            sub = itemView.findViewById(R.id.tv_sub);
        }
    }
    public interface OnClickCallback{
        void onItemClick(View view, int position);
        void onAddItemClick(View view, int position);
        void onSubItemClick(View view, int position);
    }
}
