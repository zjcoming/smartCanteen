package com.swu.smartcanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.common.constants.BaseAppConstants;
import com.common.constants.RouteConstants;
import com.common.util.BtnCountDownUtil;
import com.swu.smartcanteen.databinding.ActivityAdBinding;

@Route(path = RouteConstants.Module_app.PAGER_AD)
public class AdActivity extends BaseActivity<ActivityAdBinding> {
    Button skip;
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ARouter依赖注入
        ARouter.getInstance().inject(this);

        initView();
        registerAllOnclickListener();

        mHandler.postDelayed(BtnCountDownUtil.getCountDownRunnable(skip,3,"跳过 ",""),0);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                jump();
            }
        }, 3000);
    }
    public void jump(){
        if (BaseAppConstants.getUserPhone() != null && !BaseAppConstants.getUserPhone().equals("")){
            //说明已经登录了，则跳转到主页
            ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION).navigation();
        }else {
            //说明还没登录，则跳转到登录页面
            ARouter.getInstance().build(RouteConstants.Module_app.PAGER_MAIN).navigation();
        }

        AdActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
    }
    public void registerAllOnclickListener(){
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                jump();
            }
        });
    }
    @SuppressLint("WrongViewCast")
    public void initView(){
        skip = findViewById(R.id.ad_skip);//跳过 按钮
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}