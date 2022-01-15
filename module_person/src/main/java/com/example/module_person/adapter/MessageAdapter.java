package com.example.module_person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.common.constants.RouteConstants;
import com.example.module_person.viewholder.MessageViewHolder;
import com.swu.lib_common.R;


import java.util.ArrayList;

/**
 * Created by 刘金豪 on 2021/1/15
 * desc: 消息页面的Adapter
 */

public class MessageAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private FragmentActivity mHostActivity;
    private Context mContext;
    private ArrayList<String> mDatas;
    public MessageAdapter(FragmentActivity mHostActivity,Context mContext, ArrayList<String> mDatas){
        this.mHostActivity = mHostActivity;
        this.mContext = mContext;
        this.mDatas = mDatas;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.message_viewholder_layout, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        messageViewHolder.time.setText("15:50");
        messageViewHolder.title.setText("草泥马ging");
        messageViewHolder.content.setText("谢靖是个傻逼");
        messageViewHolder.itemView.setOnClickListener(v -> ARouter.getInstance().build(RouteConstants.Module_person.PAGER_DETAIL_MESSAGE_FRAGMENT).navigation());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
