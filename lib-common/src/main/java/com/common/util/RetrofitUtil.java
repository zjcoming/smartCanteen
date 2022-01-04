package com.common.util;

import com.common.interceptor.AddHeaderIntercepter;
import com.common.interceptor.TokenInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 封装Retrofit
 *
 * 介绍：
 *      这是Retrofit的封装类，同时也是单例类
 *
            完整使用示例：
        RequestHandler.login(new AppObserver<ResponseModel<HashMap<String,String>>>() {
            @Override
            public void onData(@NonNull ResponseModel<HashMap<String, String>> response) {
                //在这里执行请求成功的代码
            }
            @Override
            public void onFailed(@NonNull Throwable e) {
                //在这里执行请求失败的代码
            }
        },new UserBean());

 */

public class RetrofitUtil {
    //用Map集合 存储各种Retrofit实例，当后面用到相同baseUrl的retrofit时，就可以直接用
    private static Map retrofitMap = new HashMap<String,Retrofit>();

    //单例模式
    private static RetrofitUtil retrofitUtil;
    private RetrofitUtil(){}
    public static RetrofitUtil getRetrofitUtil(){
        if(retrofitUtil == null){
            synchronized (RetrofitUtil.class){
                if(retrofitUtil == null){
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }

        return retrofitUtil;
    }

    //创建Retrofit对象
    //此方法实现了同步
    public static synchronized Retrofit createRetrofit(String baseUrl){
        if(retrofitMap.get(baseUrl) == null){
            //说明此retrofit还没有创建过

            //首先创建OKHTTP，以便添加拦截器
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new TokenInterceptor())
                    .addInterceptor(new AddHeaderIntercepter())
                    .build();

            //然后创建Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl) //设置网络请求的Url地址
                    .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            retrofitMap.put(baseUrl,retrofit);
            return retrofit;
        }else {
            return (Retrofit)retrofitMap.get(baseUrl);
        }
    }

    public static <T> T getService(Class<T> serviceClass,String baseUrl){
        return RetrofitUtil.createRetrofit(baseUrl).create(serviceClass);
    }

}
