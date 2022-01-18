package com.common.repository;

import com.base.bean.UserBean;
import com.common.handler.RequestHandler;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

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
     * 通过telephone，获取用户的所有数据
     */
    public void getUserFromServer(Observer<ResponseModel<HashMap<String,String>>> observer, UserBean userBean){
        RequestHandler.getUserInfo(observer,userBean);
    }
}
