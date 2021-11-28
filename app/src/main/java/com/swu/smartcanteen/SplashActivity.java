package com.swu.smartcanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.common.constants.RouteConstants;
import com.common.util.LogUtil;
import com.swu.smartcanteen.databinding.ActivitySplashBinding;

import java.security.Key;

@Route(path = RouteConstants.Module_app.PAGER_SPLASH)
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //状态栏透明
        setTranslucent(this);

        setContentView(R.layout.activity_splash);

        //ARouter依赖注入
        ARouter.getInstance().inject(this);

        //设置动画
        setAnimation(this);

        //停留3s后，跳转到主页
        new Handler().postDelayed(new Runnable() {
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
                //跳转
                ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION).navigation();
                SplashActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
            }
        }, 2000);

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