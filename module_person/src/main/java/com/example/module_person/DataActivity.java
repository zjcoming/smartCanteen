package com.example.module_person;


import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.base.BaseActivity;
import com.base.BaseFragment;
import com.common.util.FragmentUtil;
import com.example.module_person.data.DataFragment;
import com.swu.module_person.databinding.ActivityDataBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class DataActivity extends BaseActivity<ActivityDataBinding> {

    private DataFragment mFragment;

    @Override
    public void initData() {
//
    }

    @Override
    public void initListener() {
        mFragment = new DataFragment();
        mFragment.setJumpFragmentCallBack1(new Function2<Fragment, Integer, Unit>() {
            @Override
            public Unit invoke(Fragment fragment, Integer integer) {
                FragmentUtil.getInstance().startFragment(DataActivity.this, fragment, binding.mContainer.getId(), integer);
                return null;
            }
        });
        FragmentUtil.getInstance().startFragment(this,mFragment,binding.mContainer.getId());

    }

    //状态栏透明
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}