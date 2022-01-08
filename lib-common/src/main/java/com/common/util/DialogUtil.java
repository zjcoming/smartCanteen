package com.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.common.constants.BaseAppConstants;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 展示各种Dialog的工具类
 */
public class DialogUtil {
    /**
     * 只有一个按钮的dialog
     */
    public static void showOneBtnDialog(Context context,String title,String content,String btnName,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(btnName,onClickListener);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);//点击外面 dialog不能消失
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return true;
            }
        });
        dialog.show();
    }

    /**
     * 有两个按钮的dialog，另外一个默认是“取消”键，点击后dialog消失
     */
    public static void showTwoBtnDialog(Context context,String title,String content,String firstBtnName,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(firstBtnName,onClickListener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 有两个按钮的dialog，两个按钮都自己实现
     */
    public static void selfShowTwoBtnDialog(Context context,String title,String content,String firstBtnName,String secondBtnName,DialogInterface.OnClickListener firstBtn,DialogInterface.OnClickListener secondBtn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(firstBtnName,firstBtn);
        builder.setNegativeButton(secondBtnName,secondBtn);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return true;
            }
        });
        dialog.show();
    }
}
