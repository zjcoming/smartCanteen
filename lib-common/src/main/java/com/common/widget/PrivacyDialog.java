package com.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.base.util.UIUtils;
import com.swu.lib_common.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 刘金豪 on 2021/1/12
 * desc: 隐私协议dialog
 */

public class PrivacyDialog extends Dialog{
    //一些位置参数
    int proto_start = 0;
    int proto_end = 0;
    int proto_index = 0;
    int user_start = 0;
    int user_end = 0;
    int user_index = 0;
    TextView dialogContent;//dialog的内容
    TextView agree;//“同意”

    public PrivacyDialog(@NonNull Context context,PrivacyDialogListenser privacyDialogListenser) {
        super(context, R.style.privacy_dialog_style);
        this.privacyDialogListenser = privacyDialogListenser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy);
        agree = findViewById(R.id.privacy_dialog_agree);
        dialogContent = findViewById(R.id.privacy_dialog_content);

        //用户点击不同意，则退出app
        findViewById(R.id.privacy_dialog_disagree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                System.exit(0);
            }
        });
        //用户点击同意，则执行回调
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privacyDialogListenser != null) {
                    privacyDialogListenser.agree();
                }
                dismiss();
            }
        });

        //无法取消dialog，除非用户点击了“同意”or“不同意”
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return true;
            }
        });

        //显示dialog的内容
        String content = getContext().getString(R.string.privacy_dialog_content);//privacydialog的内容
        String proto_in_content = getContext().getString(R.string.privacy_dialog_proto);//内容里面的《隐私协议》
        String user_in_content = getContext().getString(R.string.privacy_dialog_user);//内容里面的《用户协议》
        proto_index = content.indexOf(proto_in_content);
        //《隐私协议》在content中的起点和重点
        proto_start = proto_index;
        proto_end = proto_index + proto_in_content.length();
        //《用户协议》在content中的起点和重点
        user_index = content.indexOf(user_in_content);
        user_start = user_index;
        user_end = user_index + user_in_content.length();

        //将内容嵌到dialog里面
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                UIUtils.INSTANCE.showToast(getContext(),proto_in_content);
            }
        }, proto_start, proto_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                UIUtils.INSTANCE.showToast(getContext(),user_in_content);
            }
        }, user_start, user_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.blue)), proto_start, proto_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.blue)), user_start, user_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        dialogContent.setText(spannableString);
        dialogContent.setMovementMethod(LinkMovementMethod.getInstance());//可以移动
    }


    //回调
    PrivacyDialogListenser privacyDialogListenser;

    public interface PrivacyDialogListenser {
        void agree();
    }

}

