package com.common.model;

import java.io.Serializable;

/**
 * Created by 刘金豪 on 2021/1/20
 * desc: 后台返回的用户消息
 */
public class MessageModel implements Serializable {
    String id;
    String title;
    String content;

    String time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", isRead=" + isRead +
                '}';
    }
}
