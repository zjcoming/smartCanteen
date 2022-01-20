package com.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 刘金豪 on 2021/1/20
 * desc: 后台返回的用户消息
 */
public class MessageModel implements Serializable {
    String id;
    String title;
    String content;
    Date time;
    int isRead;//消息是否已读 1：已读 0：未读

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
