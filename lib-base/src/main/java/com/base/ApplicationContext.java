package com.base;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @ClassName ApplicationContext
 * @Author zhangjun
 * @Date 2021/11/28 15:33
 * @Description 用于全局获取application的context
 */
public class ApplicationContext {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }
}
