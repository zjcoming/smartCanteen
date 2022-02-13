package com.example.module_person.dataFragment;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.base.BaseFragment;
import com.base.FragmentUtil;
import com.common.selfview.MyTitleBar;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentFixAddressBinding;

/**
 * @ClassName FixAddressFragment
 * @Author zhangJun
 * @Date 2022/2/13 5:08 下午
 * @Description
 */
public class FixAddressFragment extends BaseFragment<FragmentFixAddressBinding> {

    private PersonDataViewModel personDataViewModel;
    private EditText user_name;
    private EditText user_phone;
    private EditText user_address;
    private net.igenius.customcheckbox.CustomCheckBox checkBox;
    @Override
    public void initViews() {
        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
        user_name = getBinding().etAddressUserName;
        user_phone = getBinding().etAddressUserPhone;
        user_address = getBinding().etAddressUserAddress;
        checkBox = getBinding().addressCheckBox;
        registerAllObserveEvent();
    }

    @Override
    public void initListener() {
        onBackClick();
    }
    private void onBackClick() {
        getBinding().btBack.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
                FragmentUtil.getInstance().switchFragment(requireActivity(),personDataViewModel.fixAddressFragment,personDataViewModel.addressListFragment, R.id.container, com.swu.lib_common.R.anim.page_from_left_to_right_out);
            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
    private void registerAllObserveEvent(){
        //自动写入姓名
        personDataViewModel.getUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                user_name.setText(s);
            }
        });
        //自动写入电话
        personDataViewModel.getUserPhone().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                user_phone.setText(s);
            }
        });
        //自动写入地址
        personDataViewModel.getUserAddress().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                user_address.setText(s);
            }
        });
    }
}
