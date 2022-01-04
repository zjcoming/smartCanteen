package com.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.common.constants.BaseAppConstants;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 展示各种Dialog的工具类
 */
public class DialogUtil {
    public static void showOneBtnDialog(Context context,String title,String content,String btnName,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(btnName,onClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
