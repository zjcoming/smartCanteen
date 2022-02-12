package com.common.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseApplication;
import com.base.UIUtils;
import com.swu.lib_common.R;

import java.util.HashMap;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 展示各种Dialog的工具类
 */
public class DialogUtil {
    /**
     * 用来缓存loadingDialog
     */
    public static HashMap<Context,Dialog> loadingDialogHashMap = new HashMap<>();
    /**
     * 展示loadingDialog
     *
     * 根据context来缓存。如果不想用缓存好的，可以在isUseCache传入false，就会重新创建一个dialog
     *
     * 使用示例
     * Dialog loadingDialog = DialogUtil.createLoadingDialog(getContext(),"加载中",true,true);
     *         //进行网络请求。。。。
     *         //在成功或失败的回调中
     * loadingDialog.dismiss();
     *
     */
    public static Dialog createLoadingDialog(Context context, String msg, boolean cancelable,boolean isUseCache) {
        //检查看有没有缓存
        Dialog cacheDialog = loadingDialogHashMap.get(context);
        if (isUseCache && cacheDialog != null){
            //cacheDialog.show();
            return cacheDialog;
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到dialogview
        LinearLayout layout = v.findViewById(R.id.dialog_loading_linear_view);// 加载包裹住ProgressBar的LinearLayout
        TextView tipText = (TextView) v.findViewById(R.id.dialog_loading_tipTextView);// 提示文字
        tipText.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(cancelable); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域也不可消失
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        //loadingDialog.show();
        loadingDialogHashMap.put(context,loadingDialog);//放入到缓存中
        return loadingDialog;
    }
    /**
     * 返回自定义布局的dialog
     */
    public static Dialog showSelfDialog(Context context,int res){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(context, res, null);
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(false);//点击外面 dialog不能消失
        return dialog;
    }
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
                UIUtils.INSTANCE.showToast(BaseApplication.getContext(),"您取消了权限授予");
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
