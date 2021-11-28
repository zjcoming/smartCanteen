package com.common.anim;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.swu.lib_common.R;


/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 登录注册页面的Fragment的动画
 */
public class LoginRegisterFragmentAnimation {
    //设置动画 topinOrOut为true，则表示是进 false表示出
    public static void setAnimation(Activity activity,View view,boolean topinOrOut){
        Animation animation;
        //设置动画
        //现在暂时只设置了入场动画 出场动画与生命周期有关 现在还未进行研究
        if(topinOrOut){
            animation = AnimationUtils.loadAnimation(activity, R.anim.top_in_quick);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());//速度曲线，先加速后减速
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"alpha",0f,1.0f);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.setDuration(600);
            //先启动从上往下滑的动画，再启动透明度动画
            view.startAnimation(animation);
            objectAnimator.start();
        }else {
            animation = AnimationUtils.loadAnimation(activity, R.anim.top_out);
        }
    }
}
