package com.swu.smartcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.BaseFragment;
import com.base.bean.UserBean;
import com.base.util.UIUtils;
import com.common.anim.LoginRegisterFragmentAnimation;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;
import com.common.constants.BaseAppConstants;
import com.common.constants.LoginAndRegisterConstants;
import com.common.handler.RequestHandler;
import com.common.util.CheckUtil;
import com.common.util.MMKVUtil;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.NavigationActivity;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.databinding.FragmentLoginBinding;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 登录的Fragment
 */
public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
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
    private void setIdAndPwd(){
        //如果勾选了记住密码，则把用户手机号和密码保存到本地
        String rememberPwdId = MMKVUtil.getMMKV(getActivity()).decodeString("remember_pwd_id");
        if (rememberPwdId != null && !rememberPwdId.equals("")){
            //说明上次点了记住密码，就把账号和密码填在输入框上
            String rememberPwdPwd = MMKVUtil.getMMKV(getActivity()).decodeString("remember_pwd_pwd");
            loginByTeleOrId.getText().append(rememberPwdId);
            password.getText().append(rememberPwdPwd);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginAndRegisterConstants.CURRENT_PAGE_INT = LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT;
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

        //赋上控件的默认值
        setIdAndPwd();
    }

    public void registerAllClickEvent(){
        //登录的点击事件注册
        //loginButtonOnclick();
//        testLoginButtonOnclick();
        loginButtonOnclick();
        //注册的点击事件注册
        registerButtonOnclick();

        //手机验证码登录的点击事件注册
        loginByPhoneButtonOnclick();

        //忘记密码的点击事件注册
        forgetPasswordButtonOnclick();

        //QQ、微信、邮件登录的点击事件注册
        QQWechatEmainButtonOnclick();
    }
    public void testLoginButtonOnclick(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });
    }
    //登录的点击事件注册
    public void loginButtonOnclick(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephoneOrId = loginByTeleOrId.getText().toString();
                String userpassword = password.getText().toString();

                if(telephoneOrId.equals("")){
                    UIUtils.INSTANCE.showToast(getContext(),"电话号码不能为空,请重新输入");
                }else if (userpassword.equals("")){
                    UIUtils.INSTANCE.showToast(getContext(),"密码不能为空,请重新输入");
                }else{
                    //利用正则表达式，判断是电话号码还是账号
                    boolean isValidPhone = CheckUtil.isPhone(telephoneOrId);
                    if (isValidPhone){
                        RequestHandler.login(new AppObserver<ResponseModel<HashMap<String,String>>>() {
                            @Override
                            public void onData(@NonNull ResponseModel<HashMap<String, String>> response) {
                                if(response == null){
                                    return;
                                }
                                String result = response.getResult();
                                if (result == null || (result != null && result.equals("FAILED"))) {
                                    UIUtils.INSTANCE.showToast(getContext(),response.getMessage());
                                } else if (result != null && result.equals("SUCCESS")){
                                    //登录成功
                                    String token = response.getData().get("token");
                                    BaseAppConstants.updateLogin(true,telephoneOrId,userpassword,token);
                                    //如果勾选了”记住密码“或者”自动登录“，则需要把该账号密码保存到本地
                                    //自动登录的优先级高
                                    if(quickLogin != null && quickLogin.isChecked()){
                                        //如果勾选了自动登录，则把用户手机号和密码保存到本地
                                        MMKVUtil.getMMKV(getActivity()).encode("auto_login_id",telephoneOrId);
                                        MMKVUtil.getMMKV(getActivity()).encode("auto_login_pwd",userpassword);
                                    }else if (rememberPassword != null && rememberPassword.isChecked()){
                                        //如果勾选了记住密码，则也把用户手机号和密码保存到本地
                                        MMKVUtil.getMMKV(getActivity()).encode("remember_pwd_id",telephoneOrId);
                                        MMKVUtil.getMMKV(getActivity()).encode("remember_pwd_pwd",userpassword);
                                    }else {
                                        //什么都没勾选，则清空所有的
                                        MMKVUtil.getMMKV(getActivity()).encode("auto_login_id","");
                                        MMKVUtil.getMMKV(getActivity()).encode("auto_login_pwd","");
                                        MMKVUtil.getMMKV(getActivity()).encode("remember_pwd_id","");
                                        MMKVUtil.getMMKV(getActivity()).encode("remember_pwd_pwd","");
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
                        },new UserBean("", "", userpassword, telephoneOrId, "", 0));
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
                //LoginAndRegisterConstants.fragments[LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD].setArguments(bundle);
                //更改当前页面为LOGIN_BY_PHONE页面
                LoginAndRegisterConstants.CURRENT_PAGE_INT = LoginAndRegisterConstants.LOGIN_BY_PHONE;
                changeRegisterFrag(LoginAndRegisterConstants.LOGIN_BY_PHONE);
            }
        });
    }
    //忘记密码的点击事件注册
    public void forgetPasswordButtonOnclick(){
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginAndRegisterConstants.fragments[LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD].setArguments(bundle);
                LoginAndRegisterConstants.CURRENT_PAGE_INT = LoginAndRegisterConstants.FORGET_PASSWORD;
                changeRegisterFrag(LoginAndRegisterConstants.FORGET_PASSWORD);
            }
        });
    }

    //QQ、微信、邮件登录的点击事件注册
    public void QQWechatEmainButtonOnclick(){
        loginByQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.INSTANCE.showToast(getContext(),"暂未开放，敬请期待");
            }
        });
        loginByWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.INSTANCE.showToast(getContext(),"暂未开放，敬请期待");
            }
        });
        loginByEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.INSTANCE.showToast(getContext(),"暂未开放，敬请期待");
            }
        });
    }
    //更换Fragment
    public void changeRegisterFrag(int fragmentNum){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(fragmentNum);
    }

    @Override
    public void initListener() {

    }
}