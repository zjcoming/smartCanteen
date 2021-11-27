package com.swu.smartcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.constants.LoginAndRegisterConstants;
import com.common.util.CheckUtil;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.NavigationActivity;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.SplashActivity;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 登录的Fragment
 */
public class LoginFragment extends Fragment {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化所有控件
        init();

        //注册所有点击事件
        registerAllClickEvent();
    }

    public void init(){
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
                        Intent intent = new Intent(getActivity(), NavigationActivity.class);
                        startActivity(intent);
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
                changeRegisterFrag(new RegisterFragment());
            }
        });
    }

    //手机验证码登录的点击事件注册
    public void loginByPhoneButtonOnclick(){
        loginByTeleCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("show", LoginAndRegisterConstants.LOGIN_BY_PHONE);
                LoginByPhoneFragment loginByPhoneFragment = new LoginByPhoneFragment();
                loginByPhoneFragment.setArguments(bundle);
                changeRegisterFrag(loginByPhoneFragment);
            }
        });
    }
    //忘记密码的点击事件注册
    public void forgetPasswordButtonOnclick(){
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("show", LoginAndRegisterConstants.FORGET_PASSWORD);
                LoginByPhoneFragment forgetPasswordFragment = new LoginByPhoneFragment();
                forgetPasswordFragment.setArguments(bundle);
                changeRegisterFrag(forgetPasswordFragment);
            }
        });
    }
    //更换Fragment
    public void changeRegisterFrag(Fragment newFragment){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(newFragment);
    }

}