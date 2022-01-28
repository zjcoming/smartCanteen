package com.example.module_person.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.model.MessageModel;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.repository.UserRepository;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;
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
    private ArrayList<MessageModel> allMessages;

    public MessageAdapter(FragmentActivity mHostActivity,Context mContext, ArrayList<MessageModel> allMessages){
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

        messageViewHolder.time.setText(allMessages.get(position).getTime());
        messageViewHolder.title.setText(allMessages.get(position).getTitle());
        messageViewHolder.content.setText(allMessages.get(position).getContent());

        if(allMessages.get(position).getIsRead() == 1){
            //用户已读
            messageViewHolder.status.setText("已读");
            messageViewHolder.status.setTextColor(Color.parseColor("#777777"));
            messageViewHolder.point.setVisibility(View.GONE);
        }else {
            //用户未读
            messageViewHolder.status.setText("未读");
            messageViewHolder.status.setTextColor(Color.parseColor("#F24011"));
            messageViewHolder.point.setVisibility(View.VISIBLE);
        }
        messageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送请求，设置消息状态
                allMessages.get(position).setIsRead(1);
                //allMessages.get(position).getTime().
                //allMessages.get(position).setTime(sdf.format(allMessages.get(position).getTime()));
                UserRepository.getUserRepository().setUserMessageToServer(new AppObserver<ResponseModel<HashMap<String,String>>>() {
                    @Override
                    public void onData(@io.reactivex.annotations.NonNull ResponseModel<HashMap<String, String>> o) {
                        Log.v("ljh","设置消息状态成功"+o.getMessage());
                    }
                },allMessages.get(position));

                ARouter.getInstance().build(RouteConstants.Module_person.PAGER_UI_ACTIVITY)
                        .withString("targetFragment", TargetFragmentConstants.DETAIL_MESSAGE_FRAGMENT)
                        .withSerializable("msgDetail", allMessages.get(position))
                        .navigation();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allMessages.size();
    }
}
