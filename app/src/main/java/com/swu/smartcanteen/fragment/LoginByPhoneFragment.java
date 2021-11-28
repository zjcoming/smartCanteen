package com.swu.smartcanteen.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseFragment;
import com.common.anim.LoginRegisterFragmentAnimation;
import com.common.constants.LoginAndRegisterConstants;
import com.common.util.RandomCode;
import com.swu.smartcanteen.R;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置入场动画
        LoginRegisterFragmentAnimation.setAnimation(getActivity(),root,true);

        initViews();

        //不管是哪种页面，都有发送验证码的页面，所以先注册其点击事件
        //注册 发送验证码 点击事件
        sendCodeButtonOnclick();

        //PHONELOGIN_OR_FORGETPASSWORD的值决定是 手机号登录页面 还是 忘记密码页面
        Bundle bundle = getArguments();
        int PHONELOGIN_OR_FORGETPASSWORD = bundle.getInt("show");

        if(PHONELOGIN_OR_FORGETPASSWORD == LoginAndRegisterConstants.LOGIN_BY_PHONE){
            //是手机号登录页面，注册其点击事件即可
            //首先进行页面设置
            LoginAndRegisterConstants.CURRENT_PAGE = LoginAndRegisterConstants.CURRENT_PAGE_IS_LOGINBYPHONECODE;
        }else {
            //是忘记密码页面

            //首先进行页面设置
            LoginAndRegisterConstants.CURRENT_PAGE = LoginAndRegisterConstants.CURRENT_PAGE_IS_FORGETPASSWORD;

            phoneloginOrforgetpassword.setText("忘记密码");
            loginOrverification.setText("验证");

            //注册 忘记密码页面中 验证 的点击事件
            verificationButtonOnclick();
        }
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
                RandomCode.createAlertAndCode(getContext(),getActivity(),firstEdit.getText().toString(),getResources().getColor(R.color.black),LoginAndRegisterConstants.CURRENT_PAGE_IS_LOGINBYPHONECODE);
            }
        });
    }

    //注册 验证 的点击事件（忘记密码页面）
    public void verificationButtonOnclick(){
        //注册”验证“点击事件
        loginOrverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephone = firstEdit.getText().toString();
                if (telephone.equals("")){
                    Toast.makeText(getContext(),"手机号不能为空，请输入手机号码",Toast.LENGTH_LONG).show();
                }else {
                    String codeByUser = secondEdit.getText().toString();
                    if (codeByUser.equals(LoginAndRegisterConstants.USER_SENDED_CODE)) {
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

                        firstEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        secondEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                        //注册完成的点击事件
                        //setButtonOnclick();
                    } else {
                        Toast.makeText(getContext(), "验证码不正确，请重新输入", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}