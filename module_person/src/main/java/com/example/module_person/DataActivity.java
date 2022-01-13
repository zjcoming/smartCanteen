package com.example.module_person;


import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import com.base.BaseActivity;
import com.common.util.FragmentHelper;
import com.example.module_person.widget.TopBarFragment;
import com.swu.module_person.databinding.ActivityDataBinding;



public class DataActivity extends BaseActivity<ActivityDataBinding> {

    private TopBarFragment mFragment;
    private FragmentActivity fa = this;

    @Override
    public void initData() {
//
        mFragment = new TopBarFragment();
    }

    @Override
    public void initListener() {
//        dataFragment.setJumpFragmentCallBack1(new BaseFragment.FragmentJumpListener() {
//            @Override
//            public void jumpToFragment(@NonNull Fragment fragment, int anim) {
//                FragmentUtil.getInstance().startFragment(dataFragment.requireActivity(), fragment, R.id.top_container);
//            }
//        });
//
//        FragmentUtil.getInstance().startFragment(this,dataFragment,binding.mContainer.getId());

        new FragmentHelper().bind(this).startFragment(mFragment,getBinding().topContainer.getId());

    }

    //状态栏透明
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}