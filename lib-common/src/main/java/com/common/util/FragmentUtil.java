package com.common.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @ClassName FragmentUtil
 * @Author zhangJun
 * @Date 2022/1/11 1:22 下午
 * @Description 使用说明
 * 在activity中初始化 FragmentUtil
 * 如下：FragmentUtil mFragmentUtil = new FragmentUtil(R.id.btm_container).bind(mActivity);
 *
 */
public class FragmentUtil {
    // fragmentactivity 继承自activity，用来解决android3.0 之前没有fragment的api 用法和activity一样不用纠结

    //当前需要操作的fragment所依赖的activity对象
    private FragmentActivity mHostActivity;
    //占位用到的控件id
    private int mContainerId;

    public FragmentUtil(int id) {
        mContainerId = id;
    }

    public FragmentUtil() {

    }

    public FragmentUtil bind(FragmentActivity activity) {
        mHostActivity = activity;
        return this;
    }

    public void startFragment(Fragment fragment) {
        if (mHostActivity != null && mContainerId != 0) {
            FragmentManager fragmentManager = mHostActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment targetFragment = fragmentManager.findFragmentByTag(fragment.getClass().getCanonicalName());
            if (targetFragment != null && targetFragment.isAdded()) {
                if (targetFragment.isHidden()) {
                    transaction.show(targetFragment);
                }
            } else {
                transaction.add(mContainerId, fragment, fragment.getClass().getCanonicalName());
            }
            transaction.commitNowAllowingStateLoss();
        }
    }

    public void startFragment(Fragment fragment, int containerId) {
        if (mHostActivity != null && containerId != 0) {
            FragmentManager fragmentManager = mHostActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment targetFragment = fragmentManager.findFragmentByTag(fragment.getClass().getCanonicalName());
            if (targetFragment != null && targetFragment.isAdded()) {
                if (targetFragment.isHidden()) {
                    transaction.show(targetFragment);
                }
            } else {
                transaction.add(containerId, fragment, fragment.getClass().getCanonicalName());
            }

            transaction.commitNowAllowingStateLoss();
        }
    }

    public void closeFragment(Fragment fragment) {
        if (fragment.getActivity() != null) {
            FragmentActivity activity = fragment.getActivity();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment targetFragment = fragmentManager.findFragmentByTag(fragment.getClass().getCanonicalName());
            if (targetFragment != null && targetFragment.isAdded()) {
                fragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
            }
        }
    }

    public <T extends Fragment> Fragment findFragment(Class<T> fragmentClass) {
        FragmentManager fragmentManager = mHostActivity.getSupportFragmentManager();

        Fragment targetFragment = fragmentManager.findFragmentByTag(fragmentClass.getCanonicalName());
        return targetFragment;
    }
}
