package com.example.module_person.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.common.constants.BaseUserInfo;
import com.common.model.MessageModel;
import com.common.repository.UserRepository;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

/**
 * Created by 刘金豪 on 2021/1/18
 * desc: MessageFragment的ViewModel
 */
public class MessageViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<ArrayList<MessageModel>> allMessages;
    private MutableLiveData<Boolean> loadingLiveData;//暂时没用，因为效果不太好

    public MessageViewModel(){
        allMessages = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    //得到消息
    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    public void getMessages(){
        //loadingLiveData.setValue(true);//显示加载dialog
        //模拟请求数据
        ArrayList<MessageModel> mDatas = new ArrayList<>();
        UserRepository.getUserRepository().getUserMessagesFromServer(new AppObserver<ResponseModel<ArrayList<MessageModel>>>() {
            @Override
            public void onData(@NonNull ResponseModel<ArrayList<MessageModel>> o) {
                if (o == null){
                    return;
                }
                mDatas.addAll(o.getData());
//                loadingLiveData.setValue(false);
                allMessages.setValue(mDatas);
            }
        }, BaseUserInfo.getUserBean().getUid());
    }

    public MutableLiveData<ArrayList<MessageModel>> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(MutableLiveData<ArrayList<MessageModel>> allMessages) {
        this.allMessages = allMessages;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void setLoadingLiveData(MutableLiveData<Boolean> loadingLiveData) {
        this.loadingLiveData = loadingLiveData;
    }


}
