package com.common.retrofitservice;

import com.base.bean.UserBean;
import com.common.requestbase.ResponseModel;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 刘金豪 on 2021/1/19
 * desc: 用户信息相关的请求接口
 */
public interface UserInfoService {

    @POST("user/profilePhoto")
    Observable<ResponseModel<HashMap<String,String>>> setUserPhoto(@Body RequestBody file);

    @GET("user/{uid}")
    Observable<ResponseModel<UserBean>> getUserInfo(@Path("uid") String uid);
}
