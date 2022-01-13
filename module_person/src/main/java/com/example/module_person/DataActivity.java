package com.example.module_person;


import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.base.BaseActivity;
import com.base.BaseFragment;
import com.common.util.FragmentUtil;
import com.example.module_person.data.DataFragment;
import com.example.module_person.widget.TopBarFragment;
import com.swu.module_person.R;
import com.swu.module_person.databinding.ActivityDataBinding;



public class DataActivity extends BaseActivity<ActivityDataBinding> {

    private DataFragment dataFragment;
    private FragmentActivity fa = this;

    @Override
    public void initData() {
//
        dataFragment = new DataFragment();
    }

    @Override
    public void initListener() {
        dataFragment.setJumpFragmentCallBack1(new BaseFragment.FragmentJumpListener() {
            @Override
            public void jumpToFragment(@NonNull Fragment fragment, int anim) {
                FragmentUtil.getInstance().startFragment(dataFragment.requireActivity(), fragment, R.id.top_container);
            }
        });

        FragmentUtil.getInstance().startFragment(this,dataFragment,binding.mContainer.getId());

    }

    //状态栏透明
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}