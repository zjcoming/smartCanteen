package com.common.retrofitservice;

import com.base.bean.UserBean;
import com.common.api.ResponseModel;


import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这是使用UserBean与后端进行交互的类，使用了retrofit
 */

public interface UserLoginService {
    //注册
    @POST("user")
    Observable<ResponseModel<HashMap<String,String>>> register(@Body UserBean user);
    //登录
    @POST("token")
    Observable<ResponseModel<HashMap<String,String>>> login(@Body UserBean user);
    //手机验证码登录
    @POST("user/password")
    Observable<ResponseModel<HashMap<String,String>>> resetPwd(@Body UserBean user);
    //发送验证码
    @GET("code")
    Observable<ResponseModel<HashMap<String,String>>> sendCode(@Query("telephone") String telephone);
}