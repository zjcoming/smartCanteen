package com.swu.smartcanteen.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base.BaseFragment;
import com.base.bean.UserBean;
import com.common.anim.LoginRegisterFragmentAnimation;
import com.common.api.ResponseModel;
import com.common.constants.LoginAndRegisterConstants;
import com.common.retrofitservice.UserLoginService;
import com.common.util.CheckUtil;
import com.common.util.MMKVUtil;
import com.common.util.RandomCode;
import com.common.util.RetrofitUtil;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.NavigationActivity;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.SplashActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 注册的Fragment
 */
public class RegisterFragment extends BaseFragment {
    View root;
    Button register;
    Button sendCode;
    EditText passWord;
    EditText telePhone;
    EditText Code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_register, container, false);
        }
        return root;
    }

    //设置入场动画和出场动画
    @Override
    public void onPause() {
        super.onPause();
        LoginRegisterFragmentAnimation.setAnimation(getActivity(),root,false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //入场动画
        LoginRegisterFragmentAnimation.setAnimation(getActivity(),root,true);

        initViews();
        //注册 按钮点击事件
        registerButtonOnclick();

        //发送验证码  按钮点击事件
        sendCodeButtonOnclick();
    }
    //设置入场动画
    public static void setAnimation(Activity activity){
        //设置动画
        View splashView = activity.findViewById(R.id.register_fragment);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.top_in);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());//速度曲线，先加速后减速
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(splashView,"alpha",0f,1.0f);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(600);
        //先启动从上往下滑的动画，再启动透明度动画
        splashView.startAnimation(animation);
        objectAnimator.start();
    }
    public void initViews(){
        //得到控件
        passWord = (EditText)getActivity().findViewById(R.id.register_password);
        passWord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        telePhone = (EditText)getActivity().findViewById(R.id.register_telephone);
        Code = (EditText)getActivity().findViewById(R.id.register_telecode);
        register = getActivity().findViewById(R.id.register_register);
        sendCode = getActivity().findViewById(R.id.register_sendcode);
    }
    //注册按钮 点击事件
    public void registerButtonOnclick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行校验
                if(checkIsValid()){
                    //只有各参数合法的时候，才能继续
                    //首先判断用户输入的验证码是否正确
                    if(Code.getText().toString().equals(LoginAndRegisterConstants.USER_SENDED_CODE)){
                        //验证码输入正确
                        UserLoginService service = RetrofitUtil.getService(UserLoginService.class, LoginAndRegisterConstants.BASE_URL);
                        Observable<ResponseModel<HashMap<String,String>>> register = service.register(new UserBean("",
                                "",
                                passWord.getText().toString(),
                                telePhone.getText().toString(),
                                "", 0));

                        register.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                                .subscribe(new Observer<ResponseModel<HashMap<String,String>>>() {
                                    //执行时，会先执行onSubscribe，后执行onNext处理返回的数据，后执行onError或者onComplete
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(@NonNull ResponseModel<HashMap<String,String>> response) {
                                        String result = response.getResult();
                                        if (result.equals("FAILED")) {
                                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            //注册成功
                                            Toast.makeText(getContext(), "注册成功！！", Toast.LENGTH_SHORT).show();
                                            //本地添加Token
                                            MMKVUtil.getMMKV(getActivity()).encode(telePhone.getText().toString()+"token","");
                                            //注册成功，短暂停留后进入登录页面
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    changeRegisterFrag(LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT);
                                                }
                                            }, 800);

                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });
                    }else {
                        //用户输入的验证码错误
                        Toast.makeText(getContext(),"验证码输入有误，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //发送验证码按钮 点击事件
    public void sendCodeButtonOnclick(){
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomCode.createAlertAndCode(getContext(),getActivity(),telePhone.getText().toString(),getResources().getColor(R.color.black), LoginAndRegisterConstants.CURRENT_PAGE_IS_REGISTER);
            }
        });
    }
    //更换Fragment
    public void changeRegisterFrag(int fragmentNum){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(LoginAndRegisterConstants.fragments[fragmentNum]);
    }

    //校验各参数是否合法
    public boolean checkIsValid(){
        //如果手机号输入不合法，则提示用户
        if(!CheckUtil.isPhone(telePhone.getText().toString())){
            Toast.makeText(getActivity(),"手机号输入错误，请重新输入",Toast.LENGTH_SHORT).show();
            telePhone.getText().clear();
            return false;
        }
        //如果密码输入不合法，则提示用户
        if(CheckUtil.isEmpty(passWord.getText().toString())){
            Toast.makeText(getActivity(),"密码不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
            return false;
        }
        //如果验证码输入不合法，则提示用户
        if(CheckUtil.isEmpty(Code.getText().toString())){
            Toast.makeText(getActivity(),"验证码不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}