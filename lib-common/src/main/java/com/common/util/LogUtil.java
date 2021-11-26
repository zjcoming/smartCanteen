package com.common.util;

import android.util.Log;

/**
 * created by 刘金豪 on 2021.11.26
 * desc: Log日志的封装
 *
 * 使用示例
 * LogUtil.i("这里是日志打印");
 */
public class LogUtil {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("================");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")================:");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     * 通过 Thread.currentThread().getStackTrace(); 获得 StackTraceElement[]
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message){
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message){
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message){
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message){
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message){
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }
}
