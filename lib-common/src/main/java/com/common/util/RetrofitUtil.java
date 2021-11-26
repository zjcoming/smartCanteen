package com.common.util;

import android.util.Log;

import com.common.api.API;
import com.common.api.Bean;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
            RetrofitUtil.getAPIService(RetrofitUtil.createRetrofit("baseUrl"),serviceClass).XXXX()
                        .subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                        .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                        .subscribe(new Observer<Bean>(){
                                    //执行时，会先执行onSubscribe，后执行onNext处理返回的数据，后执行onError或者onComplete
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(Bean value) {
                                        //对返回的Bean进行处理
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
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl) //设置网络请求的Url地址
                    .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            retrofitMap.put(baseUrl,retrofit);
            return retrofit;
        }else {
            return (Retrofit)retrofitMap.get(baseUrl);
        }
    }

    //创建api实例
    public static API getAPIService(Retrofit retrofit,API serviceApi){
        return retrofit.create(serviceApi.getClass());
    }
}
