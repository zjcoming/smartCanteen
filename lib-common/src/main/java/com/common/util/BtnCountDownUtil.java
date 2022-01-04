package com.common.util;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.common.constants.LoginAndRegisterConstants;
import com.swu.lib_common.R;

import java.util.HashMap;


/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 点击 发送验证码后 设置按钮的状态
 *
 * 使用：BtnCountDownUtil.getCountDownRunnable(button,60);
 */
public class BtnCountDownUtil {
    public static synchronized void continueCountDown(Button button,long currentTime,String beforeTime,String afterTime){
        //如果当前时间与上一次发送验证码的时间相差60s以上，则返回
        if(currentTime - LoginAndRegisterConstants.LAST_SEND_CODE_TIME >= 60 * 1000){
            return;
        }
        int time = 60 - (int)(currentTime - LoginAndRegisterConstants.LAST_SEND_CODE_TIME) / 1000;

        new Handler().postDelayed(getCountDownRunnable(button,time,beforeTime,afterTime),0);
    }
    public static synchronized BtnCountDownRunnable getCountDownRunnable(Button button,int time,String beforeTime,String afterTime){
        BtnCountDownRunnable btnCountDownRunnable = hashMap.get(button);
        if(btnCountDownRunnable == null){
            //说明之前没有过 要重新创建
            btnCountDownRunnable = new BtnCountDownRunnable(button,time,beforeTime,afterTime);
        }else {
            btnCountDownRunnable.time = time;
        }

        return btnCountDownRunnable;
    }


    //得到倒计时工具的容器 Button
    private static HashMap<Button,BtnCountDownRunnable> hashMap = new HashMap<>();

    private static class BtnCountDownRunnable implements Runnable{
        Button button;//需要变化的按钮
        int time;//倒计时的时间
        String beforeTime;
        String afterTime;

        public BtnCountDownRunnable(Button button, int time,String beforeTime,String afterTime) {
            this.button = button;
            this.time = time;
            this.beforeTime = beforeTime;
            this.afterTime = afterTime;
        }

        @Override
        public void run() {
            time--;
            button.setText(beforeTime + time + afterTime);
            if (time == 0) {
                if (beforeTime.equals("")){
                    button.setText("重新发送");
                }
                button.setClickable(true);
                //@android:color/transparent
            }else {
                if (!beforeTime.equals("跳过 ")){
                    button.setClickable(false);
                }
                new Handler().postDelayed(this, 1000);
            }
        }
    }
}
