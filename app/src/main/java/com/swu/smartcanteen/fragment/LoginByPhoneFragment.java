package com.swu.smartcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseFragment;
import com.base.bean.UserBean;
import com.base.util.BaseUtil;
import com.base.util.UIUtils;
import com.common.anim.LoginRegisterFragmentAnimation;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;
import com.common.constants.BaseAppConstants;
import com.common.constants.LoginAndRegisterConstants;
import com.common.handler.RequestHandler;
import com.common.util.BtnCountDownUtil;
import com.common.util.RandomCode;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.NavigationActivity;
import com.swu.smartcanteen.R;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 手机验证码登录 和 忘记密码的Fragment
 */
public class LoginByPhoneFragment extends BaseFragment {
    //验证码登录or忘记密码
    TextView phoneloginOrforgetpassword;
    //登录or验证
    Button loginOrverification;
    //发送验证码
    Button sendCode;
    //得到两个EditText
    EditText firstEdit;
    EditText secondEdit;
    String forgetPwdPhone;

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_login_by_phone, container, false);
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
    public void onResume() {
        super.onResume();

        BtnCountDownUtil.continueCountDown(sendCode,System.currentTimeMillis(),"","s后重新发送");
        int PHONELOGIN_OR_FORGETPASSWORD = LoginAndRegisterConstants.CURRENT_PAGE_INT;

        if(PHONELOGIN_OR_FORGETPASSWORD == LoginAndRegisterConstants.LOGIN_BY_PHONE){
            //是手机号登录页面，注册其点击事件即可
            //首先进行页面设置
            phoneloginOrforgetpassword.setText("手机验证码登录");
            loginOrverification.setText("登录");

            //注册 手机验证码登录页面中 登录 的点击事件
            loginButtonOnclick();
        }else {
            //是忘记密码页面

            //首先进行页面设置
            phoneloginOrforgetpassword.setText("忘记密码");
            loginOrverification.setText("验证");

            //注册 忘记密码页面中 验证 的点击事件
            verificationButtonOnclick();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置入场动画
        LoginRegisterFragmentAnimation.setAnimation(getActivity(),root,true);

        initViews();

        //不管是哪种页面，都有发送验证码的页面，所以先注册其点击事件
        //注册 发送验证码 点击事件
        sendCodeButtonOnclick();

        //PHONELOGIN_OR_FORGETPASSWORD的值决定是 手机号登录页面 还是 忘记密码页面
        //废弃Bundle，使用全局Constants Bundle bundle = getArguments();
        //int PHONELOGIN_OR_FORGETPASSWORD = bundle.getInt("show");

    }

    public void initViews(){
        //上面标题的TextView获取
        phoneloginOrforgetpassword = getActivity().findViewById(R.id.phoneOrforget);
        //下面是登录还是验证的按钮的获取
        loginOrverification = getActivity().findViewById(R.id.loginbyphone_login);
        //发送验证码按钮的获取
        sendCode = getActivity().findViewById(R.id.loginbyphone_sendcode);
        //两个输入框的获取
        firstEdit = getActivity().findViewById(R.id.loginbyphone_tele);
        secondEdit = getActivity().findViewById(R.id.loginbyphone_telecode);
    }

    //注册 发送验证码 点击事件
    public void sendCodeButtonOnclick(){
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗，进行人机验证
                RandomCode.createAlertAndCode(getContext(),(Button)v,getActivity(),firstEdit.getText().toString(),getResources().getColor(R.color.black),LoginAndRegisterConstants.CURRENT_PAGE_INT);
            }
        });
    }

    //注册 验证 的点击事件（忘记密码页面）
    public void verificationButtonOnclick(){
        //注册”验证“点击事件
        loginOrverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstEditString = firstEdit.getText().toString();
                String secondEditString = secondEdit.getText().toString();
                if (firstEditString.equals("")){
                    Toast.makeText(getContext(),"手机号不能为空，请输入手机号码",Toast.LENGTH_LONG).show();
                }else {
                    BaseUtil.INSTANCE.verifyCode(firstEditString, secondEditString, new Function1<Boolean, Unit>() {
                        @Override
                        public Unit invoke(Boolean aBoolean) {
                            if(aBoolean){
                                forgetPwdPhone = firstEditString;
                                //设置输入框为密码类型
                                firstEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                secondEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                phoneloginOrforgetpassword.setText("设置新密码");
                                loginOrverification.setText("完成");
                                sendCode.setVisibility(View.INVISIBLE);

                                firstEdit.setText("");
                                secondEdit.setText("");
                                firstEdit.setHint("请输入新密码");
                                secondEdit.setHint("请确认新密码");

//                                firstEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                                secondEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                                //注册完成的点击事件
                                setNewpwdButtonOnclick();
                            }else {
                                //用户输入的验证码错误
                                UIUtils.INSTANCE.showToast(getContext(), "验证码输入有误，请重新输入");
                            }

                            return Unit.INSTANCE;
                        }
                    });

                }
            }
        });
    }
    //注册 完成 的点击事件（忘记密码页面，设置完新密码后）
    public void setNewpwdButtonOnclick(){
        loginOrverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstEditString = firstEdit.getText().toString();
                String secondEditString = secondEdit.getText().toString();
                if(firstEditString == null || secondEditString == null){
                    return;
                }
                if (firstEditString.equals("") || secondEditString.equals("")){
                    UIUtils.INSTANCE.showToast(getContext(),"不能为空");
                }else if (!firstEditString.equals(secondEditString)){
                    UIUtils.INSTANCE.showToast(getContext(),"两次密码不一致哦~");
                }else {
                    RequestHandler.resetPwd(new AppObserver<ResponseModel<HashMap<String,String>>>() {
                        @Override
                        public void onData(@NonNull ResponseModel<HashMap<String, String>> o) {
                            UIUtils.INSTANCE.showToast(getContext(),"更改密码成功");
                            //跳转到登录页面
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            changeRegisterFrag(LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT);
                                        }
                                    }, 500);
                                }
                            }, 2000);
                        }
                    },new UserBean("","",firstEditString,forgetPwdPhone,"",0));
                }
            }
        });
    }
    //注册 登录 的点击事件（手机验证码登录页面）
    public void loginButtonOnclick(){
        loginOrverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstEditString = firstEdit.getText().toString();
                String secondEditString = secondEdit.getText().toString();
                BaseUtil.INSTANCE.verifyCode(firstEditString, secondEditString,
                        new Function1<Boolean, Unit>() {
                            @Override
                            public Unit invoke(Boolean aBoolean) {
                                if(aBoolean){
                                    //说明验证码没有异常，短信是对的
                                    //验证码输入正确
                                    LoginAndRegisterConstants.IS_LOGIN_BY_PHONE = "true";
                                    RequestHandler.loginByPhone(new AppObserver<ResponseModel<HashMap<String, String>>>() {
                                        @Override
                                        public void onData(@NonNull ResponseModel<HashMap<String, String>> response) {
                                            if (response == null){
                                                return;
                                            }

                                            String result = response.getResult();
                                            if (result != null && result.equals("FAILED")) {
                                                UIUtils.INSTANCE.showToast(getContext(), response.getMessage());
                                            } else if (result != null && result.equals("SUCCESS")){
                                                //登录成功
                                                String token = response.getData().get("token");
                                                BaseAppConstants.updateLogin(true,firstEditString,"",token);

                                                UIUtils.INSTANCE.showToast(getContext(), "登录成功");
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
                                        public void onComplete() {
                                            LoginAndRegisterConstants.IS_LOGIN_BY_PHONE = "false";
                                        }
                                    },new UserBean("",
                                            "",
                                            "",
                                            firstEditString,
                                            "", 0));
                                }else {
                                    //用户输入的验证码错误
                                    UIUtils.INSTANCE.showToast(getContext(), "验证码输入有误，请重新输入");
                                }

                                return Unit.INSTANCE;
                            }
                        });
            }
        });

    }

    public void changeRegisterFrag(int fragmentNum){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(fragmentNum);
    }
}