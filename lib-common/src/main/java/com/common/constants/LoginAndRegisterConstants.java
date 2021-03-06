package com.common.constants;

import androidx.fragment.app.Fragment;

import com.common.util.MMKVUtil;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 登录注册页面 需要用到的常量
 */
public class LoginAndRegisterConstants {
    //生成页面时，据此判断是生成手机号登录页还是忘记密码页
    public final static int LOGIN_BY_PHONE = 3;
    public final static int FORGET_PASSWORD = 4;
    //记录用户是否是手机验证码登录,默认是false
    public static String IS_LOGIN_BY_PHONE = "false";
    //当前页面是第几个fragment
    public final static int LOGIN_AND_REGISTER_FRAGMENT = 0;//登录注册页面
    public final static int REGISTER_FRAGMENT = 1;//注册页面
    public final static int LOGINBYPHONE_OR_FORGETPASSWORD = 2;//手机验证码登录页面或者是忘记密码页面
    public static Fragment[] fragments;//Fragment数组

    //是否可以发送验证码
    public final static boolean CAN_SEND_CODE = false;
    //记录上一次发送验证码的时间
    public static long LAST_SEND_CODE_TIME;
    //保存用户被发送的验证码  136258
    public static String USER_SENDED_CODE = "136258";
    //保存当前是什么页
    public static int CURRENT_PAGE_INT = 0;

    //token登录失败，返回的响应码
    public final static int INVALID_TOKEN_RESPONSE_CODE = 401;
    //baseUrl http://106.55.162.140:8888/ http://www.marsyr.top:8888/
    public final static String BASE_URL = "http://www.marsyr.top:8888/";
}
