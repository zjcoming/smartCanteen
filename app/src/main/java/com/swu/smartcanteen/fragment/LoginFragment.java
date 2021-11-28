package com.swu.smartcanteen.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseFragment;
import com.base.bean.UserBean;
import com.common.anim.LoginRegisterFragmentAnimation;
import com.common.api.ResponseModel;
import com.common.constants.LoginAndRegisterConstants;
import com.common.retrofitservice.UserLoginService;
import com.common.util.CheckUtil;
import com.common.util.MMKVUtil;
import com.common.util.RetrofitUtil;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.NavigationActivity;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.SplashActivity;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 登录的Fragment
 */
public class LoginFragment extends BaseFragment {
    View root;
    //Login页面的所有控件
    EditText loginByTeleOrId;
    EditText password;
    CheckBox rememberPassword;
    CheckBox quickLogin;
    Button login;
    TextView register;
    Button loginByTeleCode;
    Button forgetPassword;
    ImageView loginByQQ;
    ImageView loginByWechat;
    ImageView loginByEmail;

    //声明一个Bundle，用于判断进入的是 用手机号登录页面  还是 忘记密码页面
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_login, container, false);
        }
        return root;
    }

    //设置入场和出场的动画
    @Override
    public void onPause() {
        super.onPause();
        LoginRegisterFragmentAnimation.setAnimation(getActivity(),root,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置入场动画
        LoginRegisterFragmentAnimation.setAnimation(getActivity(),root,true);

        //初始化所有控件
        initViews();

        //注册所有点击事件
        registerAllClickEvent();
    }

    public void initViews(){
        loginByTeleOrId = getActivity().findViewById(R.id.login_telephoneOrid);
        password = getActivity().findViewById(R.id.login_password);
        rememberPassword = getActivity().findViewById(R.id.login_rememberPassword);
        quickLogin = getActivity().findViewById(R.id.login_quicklogin);
        login = getActivity().findViewById(R.id.login_login_normal);
        register = getActivity().findViewById(R.id.login_register);
        loginByTeleCode = getActivity().findViewById(R.id.login_loginbyphone);
        forgetPassword = getActivity().findViewById(R.id.login_forget_password);
        loginByQQ = getActivity().findViewById(R.id.login_qq);
        loginByWechat = getActivity().findViewById(R.id.login_wechat);
        loginByEmail = getActivity().findViewById(R.id.login_email);
    }

    public void registerAllClickEvent(){
        //登录的点击事件注册
        loginButtonOnclick();

        //注册的点击事件注册
        registerButtonOnclick();

        //手机验证码登录的点击事件注册
        loginByPhoneButtonOnclick();

        //忘记密码的点击事件注册
        forgetPasswordButtonOnclick();
    }
    //登录的点击事件注册
    public void loginButtonOnclick(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephoneOrId = loginByTeleOrId.getText().toString();
                String userpassword = password.getText().toString();

                if(telephoneOrId.equals("")){
                    Toast.makeText(getContext(), "电话号码不能为空,请重新输入", Toast.LENGTH_LONG).show();
                }else if (userpassword.equals("")){
                    Toast.makeText(getContext(), "密码不能为空,请重新输入", Toast.LENGTH_LONG).show();
                }else{
                    //利用正则表达式，判断是电话号码还是账号
                    boolean isValidPhone = CheckUtil.isPhone(telephoneOrId);
                    if (isValidPhone){
                        //是有效的电话号码，此时进行网络请求
                        UserLoginService service = RetrofitUtil.getService(UserLoginService.class, LoginAndRegisterConstants.BASE_URL);
                        Observable<ResponseModel<HashMap<String,String>>> loginService = service.login(
                                new UserBean("",
                                "",
                                userpassword,
                                telephoneOrId,
                                "",
                                0));

                        loginService.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
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
                                            //登录成功
                                            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                            //进行判断，如果本地的Token为空，说明是第一次登录，如果不为空，则进行比较，如果一致则登录，不一致则更新
                                            String localToken = MMKVUtil.getMMKV(getActivity()).decodeString(telephoneOrId + "token","");
                                            if(!localToken.equals(response.getData().get("token"))){
                                                //有可能是本地没有保存token（用户直接登录的，没有注册）
                                                //有可能是本地token为""，（用户刚注册，还没登录）
                                                //有可能本地token过期
                                                //不管哪种情况，都要更新本地token
                                                MMKVUtil.getMMKV(getActivity()).encode(telephoneOrId+"token",response.getData().get("token"));
                                            }
                                            //登录成功，短暂停留后进入主页面
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    Intent intent = new Intent(getActivity(), NavigationActivity.class);
                                                    startActivity(intent);
                                                }
                                            }, 800);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });

                    }
                }
            }
        });
    }

    //注册的点击事件注册
    public void registerButtonOnclick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入注册的fragment
                changeRegisterFrag(LoginAndRegisterConstants.REGISTER_FRAGMENT);
            }
        });
    }

    //手机验证码登录的点击事件注册
    public void loginByPhoneButtonOnclick(){
        loginByTeleCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("show", LoginAndRegisterConstants.LOGIN_BY_PHONE);
                LoginAndRegisterConstants.fragments[LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD].setArguments(bundle);
                changeRegisterFrag(LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD);
            }
        });
    }
    //忘记密码的点击事件注册
    public void forgetPasswordButtonOnclick(){
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("show", LoginAndRegisterConstants.FORGET_PASSWORD);
                LoginAndRegisterConstants.fragments[LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD].setArguments(bundle);
                changeRegisterFrag(LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD);
            }
        });
    }
    //更换Fragment
    public void changeRegisterFrag(int fragmentNum){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(LoginAndRegisterConstants.fragments[fragmentNum]);
    }

}