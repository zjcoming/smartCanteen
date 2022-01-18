package com.swu.smartcanteen;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.base.bean.UserBean;
import com.base.util.UIUtils;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;
import com.common.constants.BaseUserInfo;
import com.common.constants.RouteConstants;
import com.common.handler.RequestHandler;
import com.common.util.MMKVUtil;
import com.common.widget.PrivacyDialog;
import com.swu.smartcanteen.databinding.ActivitySplashBinding;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;

@Route(path = RouteConstants.Module_app.PAGER_SPLASH)
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MMKVUtil.getMMKV(this).encode("auto_login_id","");
        MMKVUtil.getMMKV(this).encode("auto_login_pwd","");
        MMKVUtil.getMMKV(this).encode("remember_pwd_id","");
        MMKVUtil.getMMKV(this).encode("remember_pwd_pwd","");

        //状态栏透明
        setTranslucent(this);

        setContentView(R.layout.activity_splash);

        //ARouter依赖注入
        ARouter.getInstance().inject(this);

        //设置动画
        setAnimation(this);

        String isAgreePrivacy = MMKVUtil.getMMKV().decodeString("isAgreePrivacy","false");
        if(isAgreePrivacy != null && !isAgreePrivacy.equals("true")){
            //用户没有同意隐私协议
            PrivacyDialog privacyDialog = new PrivacyDialog(this, new PrivacyDialog.PrivacyDialogListenser() {
                @Override
                public void agree() {
                    MMKVUtil.getMMKV().encode("isAgreePrivacy","true");//保存到本地，表明用户已经同意了隐私协议
                    jump();
                }
            });
            privacyDialog.show();
        }else {
            jump();
        }
    }
    public void jump(){
        String autoLoginId = MMKVUtil.getMMKV(getApplicationContext()).decodeString("auto_login_id");
        if (autoLoginId != null && !autoLoginId.equals("")){
            //说明有自动登录的，则直接取出id登录
            String autoLoginPwd = MMKVUtil.getMMKV(getApplicationContext()).decodeString("auto_login_pwd");
            RequestHandler.login(new AppObserver<ResponseModel<HashMap<String,String>>>() {

                @Override
                public void onData(@NonNull ResponseModel<HashMap<String, String>> response) {
                    if(response == null){
                        return;
                    }
                    String result = response.getResult();
                    if (result == null || (result != null && result.equals("FAILED"))) {
                        UIUtils.INSTANCE.showToast(getApplicationContext(),response.getMessage());
                    } else if (result != null && result.equals("SUCCESS")){
                        //登录成功
                        String token = response.getData().get("token");
                        String uid = response.getData().get("uid");
                        BaseUserInfo.updateLogin(true,uid,autoLoginId,autoLoginPwd,token);
                        //如果勾选了”记住密码“或者”自动登录“，则需要把该账号密码保存到本地
                        //自动登录的优先级高

                        //登录成功，短暂停留后进入广告页面
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                ARouter.getInstance().build(RouteConstants.Module_app.PAGER_AD).navigation();
                                SplashActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
                            }
                        }, 1000);
                    }
                }
            },new UserBean(autoLoginId,autoLoginPwd));
        }else {
            //没有自动登录，则跳转到广告页面
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ARouter.getInstance().build(RouteConstants.Module_app.PAGER_AD).navigation();
                    SplashActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
                }
            }, 1000);
        }
    }
    //设置动画
    public static void setAnimation(Activity activity){
        //设置动画
        View splashView = activity.findViewById(R.id.splash_view);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.top_in);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());//速度曲线，先加速后减速
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(splashView,"alpha",0f,1.0f);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(600);
        //先启动从上往下滑的动画，再启动透明度动画
        splashView.startAnimation(animation);
        objectAnimator.start();
    }
    //状态栏透明
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //在闪屏页面 禁止按返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}