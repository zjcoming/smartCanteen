package com.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.bmob.v3.Bmob;

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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //应用结束销毁资源 节约内存
        ARouter.getInstance().destroy();
    }
}
