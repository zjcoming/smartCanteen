package com.base;

import android.app.Application;
import android.content.Context;

/**
 * @ClassName BaseApplication
 * @Author zhangjun
 * @Date 2021/11/28 14:48
 * @Description BaseApplication封装
 */
public class BaseApplication extends Application {

    private static Application sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Application getContext() {
        return sContext;
    }

}
