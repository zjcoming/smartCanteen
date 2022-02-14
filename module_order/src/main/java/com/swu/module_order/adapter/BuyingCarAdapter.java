package com.swu.module_order.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.common.bean.FoodItemBean;
import com.swu.module_order.R;
import com.swu.module_order.model.BuyingCarBean;

import java.util.List;

/**
 * @ClassName BuyingCarAdapter
 * @Author zhangJun
 * @Date 2022/1/10 7:53 下午
 * @Description
 */
public class BuyingCarAdapter extends RecyclerView.Adapter<CartBaseHolder>{

    private OnClickCallback onClickListener;
    private Context mContext;

    private static final int HOLDER_TYPE_HEAD = 1;
    private static final int HOLDER_TYPE_ITEM = 2;

    public void setOnClickListener(OnClickCallback onClickListener) {
        this.onClickListener = onClickListener;
    }

    private List<FoodItemBean> carData;

    public BuyingCarAdapter(List<FoodItemBean> carData, Context context) {
        this.carData = carData;
        mContext = context;
    }

    @NonNull
    @Override
    public CartBaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        if (viewType == HOLDER_TYPE_HEAD) {
            root = LayoutInflater.from(mContext).inflate(R.layout.bottom_cart_detail_header_layout,
                    parent,false);
            return new BuyingHeadHolder(root);
        } else {
            root = LayoutInflater.from(mContext).inflate(R.layout.item_buyingcar_layout,
                    parent,false);
            return new BuyingCarViewHolder(root);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CartBaseHolder holder, int position) {
            holder.onBindViewHolder(position, carData.get(position));
    }


    @Override
    public int getItemCount() {
        if (carData != null) {
            return carData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0) {
            type = HOLDER_TYPE_HEAD;
        } else {
            type = HOLDER_TYPE_ITEM;
        }
        return type;
    }

    class BuyingCarViewHolder extends CartBaseHolder{
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
            name = itemView.findViewById(R.id.item_food_name);
            scale = itemView.findViewById(R.id.item_food_consumption);
            favor = itemView.findViewById(R.id.item_food_flavor);
            price = itemView.findViewById(R.id.item_food_price);
            count = itemView.findViewById(R.id.item_food_count);
            add = itemView.findViewById(R.id.item_food_add);
            sub = itemView.findViewById(R.id.item_food_sub);
            foodImg = itemView.findViewById(R.id.item_food_img);
        }

        @Override
        void onBindViewHolder(int position, FoodItemBean carBean) {

            name.setText(carBean.getFoodName());
            scale.setText(carBean.getFoodConsumption());
            favor.setText(carBean.getFoodFlavor());
            price.setText("￥"+carBean.getFoodPrice());
            foodImg.setImageURI(Uri.parse(carBean.getFoodImgUrl()));
            count.setText(String.valueOf(carBean.getFoodCount()));
            //item事件点击
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        onClickListener.onItemClick(view, position);
                    }
                }
            });
            //添加
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        onClickListener.onAddItemClick(view,position);
                    }
                }
            });
            //删除购物车商品
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        onClickListener.onSubItemClick(view,position);
                    }
                }
            });
        }

    }

    class BuyingHeadHolder extends CartBaseHolder {
        private TextView cartInfo;
        public BuyingHeadHolder(@NonNull View itemView) {
            super(itemView);
            cartInfo = itemView.findViewById(R.id.cart_info);
        }

        @Override
        void onBindViewHolder(int position, FoodItemBean carBean) {

        }

    }

    public interface OnClickCallback{
        void onItemClick(View view, int position);
        void onAddItemClick(View view, int position);
        void onSubItemClick(View view, int position);
    }

}


abstract class CartBaseHolder extends RecyclerView.ViewHolder{
    public CartBaseHolder(@NonNull View itemView) {
        super(itemView);
    }
    abstract void onBindViewHolder(int position, FoodItemBean carBean);
}
