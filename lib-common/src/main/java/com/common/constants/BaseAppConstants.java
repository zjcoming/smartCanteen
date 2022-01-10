package com.common.constants;

import android.content.Context;
import android.widget.Toast;

import com.base.ApplicationContext;
import com.base.BaseApplication;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 用来保存整个app的一些常量 比如保存正在登录的用户id等等
 *
 * 这里面变量的赋值，都是在app内部进行赋值的（本地），与RspAppInfo的区别在于，RspAppInfo是后端赋值的
 */
public class BaseAppConstants {
    private static boolean isLogin;//用户是否登录
    private static String userPhone;//已经登录的用户的手机号
    private static String userPwd;//已经登录的用户的密码
    private static String userIconUrl;//用户头像的url
    private static String token = "";//不能为null

    public static void updateLogin(boolean isLogin, String userPhone, String userPwd,String token){
        Toast.makeText(ApplicationContext.getContext(), isLogin ? "登录成功" : "登录失败", Toast.LENGTH_SHORT).show();
        BaseAppConstants.isLogin = isLogin;
        BaseAppConstants.userPhone = userPhone;
        BaseAppConstants.userPwd = userPwd;
        BaseAppConstants.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseAppConstants.token = token;
    }
    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        BaseAppConstants.isLogin = isLogin;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static void setUserPhone(String userPhone) {
        BaseAppConstants.userPhone = userPhone;
    }

    public static String getUserPwd() {
        return userPwd;
    }

    public static void setUserPwd(String userPwd) {
        BaseAppConstants.userPwd = userPwd;
    }

    public static String getUserIconUrl() {
        return userIconUrl;
    }

    public static void setUserIconUrl(String userIconUrl) {
        BaseAppConstants.userIconUrl = userIconUrl;
    }
}
