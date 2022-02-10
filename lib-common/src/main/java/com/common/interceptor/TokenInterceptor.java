package com.common.interceptor;

import android.content.DialogInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.ApplicationContext;
import com.common.constants.BaseUserInfo;
import com.common.constants.LoginAndRegisterConstants;
import com.common.constants.RouteConstants;
import com.common.util.DialogUtil;
import com.common.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这是全局的Token拦截器，用于判断Token是否过期。如果过期，则强制下线
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("token", BaseUserInfo.getToken())
                .build();
        Response response = chain.proceed(request);

        //根据和服务端的约定判断token过期
        if (isTokenExpired(response)) {
            LogUtil.d("Token失效了");
            //提示弹框，强制下线
            DialogUtil.showOneBtnDialog(ApplicationContext.getContext(),
                    "您被强制下线了",
                    "您的账户已在另一台设备上登录，点击确定重新登录",
                    "确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ARouter.getInstance().build(RouteConstants.Module_app.PAGER_MAIN).navigation();//强制回到登录页面
                        }
                    });
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
        if (response == null){
            return true;
        }
        if(response.code() == LoginAndRegisterConstants.INVALID_TOKEN_RESPONSE_CODE){
            //token失效
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken(Response response) throws IOException {
        if (response == null){
            return "";
        }
        return "";
    }
}