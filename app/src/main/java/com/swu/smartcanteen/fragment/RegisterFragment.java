package com.swu.smartcanteen.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.constants.LoginAndRegisterConstants;
import com.common.util.RandomCode;
import com.swu.smartcanteen.MainActivity;
import com.swu.smartcanteen.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 注册的Fragment
 */
public class RegisterFragment extends Fragment {
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        //注册 按钮点击事件
        registerButtonOnclick();

        //发送验证码  按钮点击事件
        sendCodeButtonOnclick();
    }
    public void init(){
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
               //进行注册的相关逻辑
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
    public void changeRegisterFrag(Fragment newFragment){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(newFragment);
    }
}