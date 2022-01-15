package com.example.module_person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swu.module_person.R;

import java.util.List;

/**
 * @ClassName AddressListAdapter
 * @Author zhangJun
 * @Date 2022/1/15 2:16 下午
 * @Description
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder> {

    private List<TestBean> datas;
    private Context mContext;
    public AddressListAdapter(List<TestBean> datas,Context context){
        this.datas = datas;
        mContext = context;
    }

    @NonNull
    @Override
    public AddressListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListViewHolder holder, int position) {
        holder.campus.setText(datas.get(position).campus);
        holder.address.setText(datas.get(position).address);
        holder.userName.setText(datas.get(position).name);
        holder.phoneNumber.setText(datas.get(position).phone);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    protected static class AddressListViewHolder extends RecyclerView.ViewHolder{
        private TextView address;
        private TextView phoneNumber;
        private TextView campus;
        private TextView userName;
        public AddressListViewHolder(@NonNull View itemView) {
            super(itemView);
            campus = itemView.findViewById(R.id.address_campus);
            address = itemView.findViewById(R.id.address_detail);
            userName = itemView.findViewById(R.id.address_user_name);
            phoneNumber = itemView.findViewById(R.id.address_phone_number);
        }
    }
}
