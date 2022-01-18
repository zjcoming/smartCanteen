package com.example.module_person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.util.FragmentUtil;
import com.example.module_person.viewholder.MessageViewHolder;
import com.swu.lib_common.R;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 刘金豪 on 2021/1/15
 * desc: 消息页面的Adapter
 */

public class MessageAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private FragmentActivity mHostActivity;
    private Context mContext;
    private ArrayList<HashMap<String,String>> allMessages;
    public MessageAdapter(FragmentActivity mHostActivity,Context mContext, ArrayList<HashMap<String,String>> allMessages){
        this.mHostActivity = mHostActivity;
        this.mContext = mContext;
        this.allMessages = allMessages;
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
        messageViewHolder.time.setText(allMessages.get(position).get("time"));
        messageViewHolder.title.setText(allMessages.get(position).get("title"));
        messageViewHolder.content.setText(allMessages.get(position).get("content"));
        messageViewHolder.itemView.setOnClickListener(v -> ARouter.getInstance().build(RouteConstants.Module_person.PAGER_UI_ACTIVITY)
                .withString("targetFragment", TargetFragmentConstants.DETAIL_MESSAGE_FRAGMENT)
                .withSerializable("msgDetail",allMessages.get(position))
                .navigation());

    }

    @Override
    public int getItemCount() {
        return allMessages.size();
    }
}
