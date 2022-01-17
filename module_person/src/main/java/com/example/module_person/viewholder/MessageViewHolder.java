package com.example.module_person.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swu.lib_common.R;

/**
 * Created by 刘金豪 on 2021/1/15
 * desc: 消息的ViewHolder
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView time;
    public TextView status;
    public TextView title;
    public TextView content;
    public View point;


    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.message_item_time);
        status = itemView.findViewById(R.id.message_item_status);
        title = itemView.findViewById(R.id.message_item_title);
        content = itemView.findViewById(R.id.message_item_content);
        point = itemView.findViewById(R.id.message_item_point);
    }
}
