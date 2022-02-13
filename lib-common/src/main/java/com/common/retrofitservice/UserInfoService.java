package com.common.retrofitservice;

import com.base.dao.UserBean;
import com.common.model.AddressModel;
import com.common.model.MessageModel;
import com.common.requestbase.ResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 刘金豪 on 2021/1/19
 * desc: 用户信息相关的请求接口
 */
public interface UserInfoService {

    /**
     * 设置用户头像
     * @param file
     * @return
     */
    @POST("user/profilePhoto")
    Observable<ResponseModel<HashMap<String,String>>> setUserPhoto(@Body RequestBody file);

    /**
     * 得到用户所有信息
     * @param uid
     * @return
     */
    @GET("user/{uid}")
    Observable<ResponseModel<UserBean>> getUserInfo(@Path("uid") String uid);

    /**
     * 得到用户的消息
     */
    @GET("messageList/{uid}")
    Observable<ResponseModel<ArrayList<MessageModel>>> getUserMessages(@Path("uid") String uid);

    /**
     * 设置用户消息属性
     */
    @POST("message/info")
    Observable<ResponseModel<HashMap<String,String>>> setMessage(@Body MessageModel messageModel);

    @GET("addressList/{uid}")
    Observable<ResponseModel<ArrayList<AddressModel>>> getAddressList(@Path("uid") String uid);

}
