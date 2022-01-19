package com.example.module_person;


import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;

import com.base.BaseActivity;
import com.common.util.FragmentUtil;
import com.example.module_person.dataFragment.AddAddressFragment;
import com.example.module_person.dataFragment.AddressListFragment;
import com.example.module_person.dataFragment.PersonDataFragment;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.example.module_person.widget.TopBarFragment;
import com.swu.module_person.databinding.ActivityDataBinding;



public class DataActivity extends BaseActivity<ActivityDataBinding> {

    private TopBarFragment dataTop;
    private TopBarFragment setTop;
    private TopBarFragment addTop;
    private TopBarFragment fixTop;
    private AddressListFragment addressListFragment;
    private PersonDataFragment personDataFragment;
    private AddAddressFragment addAddressFragment;

    private PersonDataViewModel personDataViewModel;
    @Override
    public void initData() {

        personDataViewModel = new ViewModelProvider(this).get(PersonDataViewModel.class);
        addAddressFragment = personDataViewModel.addAddressFragment;
        personDataFragment = personDataViewModel.personDataFragment;
        addressListFragment = personDataViewModel.addressListFragment;

        FragmentUtil.getInstance().startFragment(DataActivity.this,personDataFragment,binding.contentContainer.getId());
    }

    @Override
    public void initListener() {


    }

    //状态栏透明
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}