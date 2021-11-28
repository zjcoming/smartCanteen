package com.common.util;

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
            使用示例：
            RetrofitUtil.getService(myservice,baseUrl)得到service对象
            完整使用：
                MyService myService = RetrofitUtil.getService(MyService.class, BASE_URL);
                Observable<ResponseModel<Bean>> observable = myService.xxx();
                observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(new Observer<ResponseModel<Bean>>() {
                    //执行时，会先执行onSubscribe，后执行onNext处理返回的数据，后执行onError或者onComplete
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Bean bean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
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
