package com.common.constants;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 登录注册页面 需要用到的常量
 */
public class LoginAndRegisterConstants {
    //生成页面时，据此判断是生成手机号登录页还是忘记密码页
    public final static int LOGIN_BY_PHONE = 1;
    public final static int FORGET_PASSWORD = 0;

    //是否可以发送验证码
    public static boolean CAN_SEND_CODE = false;
    //保存用户被发送的验证码
    public static String USER_SENDED_CODE = "";
    //保存当前是什么页
    public static String CURRENT_PAGE_IS_REGISTER = "resgister";
    public static String CURRENT_PAGE_IS_LOGINBYPHONECODE = "loginByPhoneCode";
    public static String CURRENT_PAGE_IS_FORGETPASSWORD = "forgetPassword";
}
