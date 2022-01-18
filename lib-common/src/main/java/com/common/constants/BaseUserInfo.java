package com.common.constants;

import android.widget.Toast;

import com.base.ApplicationContext;
import com.base.bean.UserBean;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 用来保存整个app的一些常量 比如保存正在登录的用户id等等
 *
 * 这里面变量的赋值，都是在app内部进行赋值的（本地）
 */
public class BaseUserInfo {
    private static boolean isLogin;//用户是否登录
    private static UserBean userBean;
    private static String token = "";//不能为null

    public static void updateLogin(boolean isLogin, String userPhone, String userPwd,String token){
        Toast.makeText(ApplicationContext.getContext(), isLogin ? "登录成功" : "登录失败", Toast.LENGTH_SHORT).show();
        BaseUserInfo.isLogin = isLogin;
        BaseUserInfo.token = token;
        userBean.setTelephone(userPhone);
        userBean.setPassword(userPwd);
    }
    public static void updateLogin(boolean isLogin, String userid,String userPhone, String userPwd,String token){
        Toast.makeText(ApplicationContext.getContext(), isLogin ? "登录成功" : "登录失败", Toast.LENGTH_SHORT).show();
        BaseUserInfo.isLogin = isLogin;
        BaseUserInfo.token = token;
        userBean.setUid(userid);
        userBean.setTelephone(userPhone);
        userBean.setPassword(userPwd);
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
