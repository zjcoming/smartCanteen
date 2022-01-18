package com.example.module_person.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 刘金豪 on 2021/1/18
 * desc: MessageFragment的ViewModel
 */
public class MessageViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<ArrayList<HashMap<String,String>>> allMessages;
    private MutableLiveData<Boolean> loadingLiveData;

    public MessageViewModel(){
        allMessages = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    //得到消息
    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    public void getMessages(){
        loadingLiveData.setValue(true);//显示加载dialog

        //模拟请求数据
        ArrayList<HashMap<String,String>> mDatas = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            HashMap<String,String> oneData = new HashMap<>();
            oneData.put("time","2022.1.18");
            oneData.put("title","这是第"+ i +"个标题");
            oneData.put("content","这是第" + i + "个内容");
            mDatas.add(oneData);
        }

        loadingLiveData.setValue(false);
        allMessages.setValue(mDatas);
    }

    public void setAllMessages(MutableLiveData<ArrayList<HashMap<String, String>>> allMessages) {
        this.allMessages = allMessages;
    }

    public MutableLiveData<ArrayList<HashMap<String, String>>> getAllMessages() {
        return allMessages;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void setLoadingLiveData(MutableLiveData<Boolean> loadingLiveData) {
        this.loadingLiveData = loadingLiveData;
    }


}
