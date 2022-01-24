package com.base;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.IOException;

import cn.bmob.v3.Bmob;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @ClassName BaseApplication
 * @Author zhangjun
 * @Date 2021/11/28 14:48
 * @Description BaseApplication封装
 */
public class BaseApplication extends Application {
    private final static String BMOB_APP_ID = "d638a1e12b050e32056d0c3c18643c2c";
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ApplicationContext
        ApplicationContext.init(this);
        //初始化ARouter
        ARouter.openLog();
        ARouter.openDebug();//上线之后需要关闭
        ARouter.init(BaseApplication.this);
        Bmob.initialize(this, BMOB_APP_ID);

        //解决相机问题
        solveCameraBug();//不加此方法，调用相机时会报错

        //解决RxJava的onError问题 不加此方法 Rxjava如果报错 就会崩溃
        solveRxJavaOnError();
    }
    private void solveRxJavaOnError(){
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) {
                if (e instanceof UndeliverableException) {
                    e = e.getCause();
                    Log.d("ljh", "UndeliverableException=" + e);
                    return;
                } else if ((e instanceof IOException)) {
                    // fine, irrelevant network problem or API that throws on cancellation
                    Log.d("ljh", "IOException=" + e.getCause());
                    return;
                } else if (e instanceof InterruptedException) {
                    // fine, some blocking code was interrupted by a dispose call
                    Log.d("ljh", "InterruptedException=" + e.getCause());
                    return;
                } else if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                    // that's likely a bug in the application
                    Log.d("ljh", "NullPointerException || IllegalArgumentException=" + e.getCause());
                    Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    return;
                } else if (e instanceof IllegalStateException) {
                    // that's a bug in RxJava or in a custom operator
                    Log.d("ljh", "IllegalStateException=" + e.getCause());
                    Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    return;
                }
                Log.d("ljh", "unknown exception=" + e);
            }
        });
    }
    private void solveCameraBug(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        //应用结束销毁资源 节约内存
        ARouter.getInstance().destroy();
    }
}
