package com.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 主要功能是获取屏幕的大小参数
 */
public class ScreenSizeUtil {
    //获取控件应该展示的高度（即屏幕高度减去底部导航栏高度）
    public static int getViewRealHeight(Context context){
        if(!checkDeviceHasNavigationBar(context)){
            //表示没有底部导航栏
            return getScreenHeight(context) - getStatusBarHeight(context);
        }else {
            //表示有底部导航栏
            return getScreenHeight(context) - getNavigationBarHeight(context);
        }
    }
    //得到顶部状态栏的高度
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("ljh", "Status height:" + height);
        return height;
    }
    //得到底部导航栏的大小
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("ljh", "Navi height:" + height);
        return height;
    }
    //判断底部是否有导航栏
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
    //在页面未加载出来前，获取屏幕的宽和高
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Log.v("ljh", "screen height:" + dm.heightPixels);
        return dm.heightPixels;
    }
}
