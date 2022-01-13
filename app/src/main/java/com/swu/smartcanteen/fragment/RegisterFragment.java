package com.swu.smartcanteen.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base.BaseFragment;
import com.base.bean.UserBean;
import com.base.util.BaseUtil;
import com.base.util.UIUtils;
import com.common.anim.LoginRegisterFragmentAnimation;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;
import com.common.constants.LoginAndRegisterConstants;
import com.common.handler.RequestHandler;
import com.common.util.BtnCountDownUtil;
import com.common.util.CheckUtil;
import com.common.util.RandomCode;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.R;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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

    @Override
    public void onResume() {
        super.onResume();
        BtnCountDownUtil.continueCountDown(sendCode,System.currentTimeMillis(),"","s后重新发送");
        LoginAndRegisterConstants.CURRENT_PAGE_INT = LoginAndRegisterConstants.REGISTER_FRAGMENT;
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
                    BaseUtil.INSTANCE.verifyCode(telePhone.getText().toString(), Code.getText().toString(), new Function1<Boolean, Unit>() {
                        @Override
                        public Unit invoke(Boolean aBoolean) {
                            if(aBoolean){
                                //说明验证码没有异常，短信是对的
                                //验证码输入正确
                                RequestHandler.register(new AppObserver<ResponseModel<HashMap<String, String>>>() {
                                    @Override
                                    public void onData(@NonNull ResponseModel<HashMap<String, String>> hashMapResponseModel) {
                                        String result = hashMapResponseModel.getResult();
                                        if (result.equals("FAILED")) {
                                            UIUtils.INSTANCE.showToast(getContext(), hashMapResponseModel.getMessage());
                                        } else {
                                            //注册成功
                                            UIUtils.INSTANCE.showToast(getContext(), "注册成功");
                                            //注册成功，短暂停留后进入登录页面
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    changeRegisterFrag(LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT);
                                                }
                                            }, 800);

                                        }
                                    }
                                },new UserBean("",
                                        "",
                                        passWord.getText().toString(),
                                        telePhone.getText().toString(),
                                        "", 0));
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
    //发送验证码按钮 点击事件
    public void sendCodeButtonOnclick(){

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomCode.createAlertAndCode(getContext(),(Button)v,getActivity(),telePhone.getText().toString(),getResources().getColor(R.color.black), LoginAndRegisterConstants.REGISTER_FRAGMENT);
            }
        });
    }
    //更换Fragment
    public void changeRegisterFrag(int fragmentNum){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(fragmentNum);
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

    @Override
    public void initListener() {

    }
}