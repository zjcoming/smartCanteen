package com.common.repository;

import com.base.bean.UserBean;
import com.base.model.MessageModel;
import com.common.handler.RequestHandler;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;

/**
 * Created by 刘金豪 on 2021/1/18
 * desc: 与User相关的数据
 *
 * 使用示例
 *      比如拿用户的所有数据 可以这样写
 *      UserRepository.getUserRepository().getUserFromServer(new AppObserver<HashMap<String,String>>() {
 *             @Override
 *             public void onData(@NonNull HashMap<String, String> o) {
 *                 //对返回的数据做处理
 *             }
 *         });
 */
public class UserRepository {
    private static UserRepository mUserRepository;
    public static UserRepository getUserRepository(){
        if (mUserRepository == null) {
            mUserRepository = new UserRepository();
        }
        return mUserRepository;
    }

    /**
     * 通过uid，获取用户的所有数据
     */
    public void getUserFromServer(Observer<ResponseModel<UserBean>> observer, String uid){
        RequestHandler.getUserInfo(observer,uid);
    }

    /**
     * 设置用户头像
     */
    public void setUserIconToServer(Observer<ResponseModel<HashMap<String,String>>> observer, RequestBody multiBody){
        RequestHandler.setUserPhoto(observer,multiBody);
    }

    /**
     * 得到用户的消息
     */
    public void getUserMessagesFromServer(Observer<ResponseModel<ArrayList<MessageModel>>> observer, String uid){
        RequestHandler.getUserMessages(observer,uid);
    }

    /**
     * 设置用户的消息
     */
    public void setUserMessageToServer(Observer<ResponseModel<HashMap<String,String>>> observer,MessageModel messageModel){
        RequestHandler.setMessage(observer,messageModel);
    }
}
