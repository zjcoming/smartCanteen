package com.common.constants;

import android.widget.Toast;

import com.base.BaseApplication;
import com.base.dao.UserBean;
import com.common.repository.UserRepository;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;

import io.reactivex.annotations.NonNull;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 用来保存整个app的一些常量 比如保存正在登录的用户id等等
 *
 * 这里面变量的赋值，都是在app内部进行赋值的（本地）
 */
public class BaseUserInfo {
    private static boolean isLogin;//用户是否登录
    private static UserBean userBean = new UserBean();
    private static String token = "";//不能为null

    public static void updateLogin(boolean isLogin, String userid,String userPhone, String userPwd,String token){
        Toast.makeText(BaseApplication.getContext(), isLogin ? "登录成功" : "登录失败", Toast.LENGTH_SHORT).show();
        BaseUserInfo.isLogin = isLogin;
        BaseUserInfo.token = token;
        userBean.setUid(userid);
        userBean.setTelephone(userPhone);
        userBean.setPassword(userPwd);

        //登录成功后，需要请求用户的所有数据
        UserRepository.getUserRepository().getUserFromServer(new AppObserver<ResponseModel<UserBean>>() {
            @Override
            public void onData(@NonNull ResponseModel<UserBean> o) {
                if (o == null){
                    return;
                }

                userBean.setCreateTime(o.getData().getCreateTime());
                userBean.setDefaultAddress(o.getData().getDefaultAddress());
                userBean.setProfilePhoto(o.getData().getProfilePhoto());
                userBean.setScore(o.getData().getScore());
                userBean.setUsername(o.getData().getUsername());
            }
        },userid);
    }

    public static UserBean getUserBean() {
        return userBean;
    }

    public static void setUserBean(UserBean userBean) {
        BaseUserInfo.userBean = userBean;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseUserInfo.token = token;
    }
    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        BaseUserInfo.isLogin = isLogin;
    }


}
