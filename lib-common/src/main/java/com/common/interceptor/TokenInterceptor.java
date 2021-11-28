package com.common.interceptor;

import android.util.Log;

import com.common.constants.LoginAndRegisterConstants;
import com.common.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这是全局的Token拦截器，用于判断Token是否过期。如果过期，则再次发起请求
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        LogUtil.d("第一次请求的响应码为" + response.code());

        //根据和服务端的约定判断token过期
        if (isTokenExpired(response)) {
            LogUtil.d("Token失效了，正在自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newToken = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("token", newToken)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        //如果响应码是token失效的响应码，则返回true
        if (response.code() == LoginAndRegisterConstants.INVALID_TOKEN_RESPONSE_CODE) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        // 通过获取token的接口，同步请求接口
        String newToken = "";
        return newToken;
    }
}