package com.common.interceptor;

import com.common.constants.BaseAppConstants;
import com.common.constants.LoginAndRegisterConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这是添加请求头的拦截器，目前负责添加
 *
 *  ①用户是否是手机验证码登录
 */
public class AddHeaderIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("loginByPhone", LoginAndRegisterConstants.IS_LOGIN_BY_PHONE)
                .build();
        Response response = chain.proceed(request);

        return response;
    }
}
