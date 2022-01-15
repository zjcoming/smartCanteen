package com.common.util;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.swu.lib_common.R;

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
    private FragmentUtil() {

    }

    private static FragmentUtil instance;

    public static FragmentUtil getInstance() {
        if (instance == null) {
            synchronized (FragmentUtil.class) {
                if (instance == null) {
                    instance = new FragmentUtil();
                }
            }
        }
        return instance;
    }
    public void startFragment(FragmentActivity hostActivity, Fragment fragment, int containerId) {
        if (hostActivity != null && containerId != 0) {
            FragmentManager fragmentManager = hostActivity.getSupportFragmentManager();
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
    /**
     * @author zhangJun
     * @param hostActivity 寄主activity
     * @param fromFragment 从那个Fragment切换
     * @param toFragment 切换到哪一个Fragment
     * @param containerId 占位控件id
     */
    public void switchFragment(FragmentActivity hostActivity, Fragment fromFragment, Fragment toFragment, int containerId){
        FragmentManager fragmentManager = hostActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        Fragment targetFragment = fragmentManager.findFragmentByTag(toFragment.getClass().getCanonicalName());
        transaction.hide(fromFragment);//隐藏上个Fragment
        if(!toFragment.isAdded()) {
            transaction.add(containerId, toFragment);
        }else if (!toFragment.isVisible()) {
            transaction.show(toFragment);
        }
        transaction.commitNowAllowingStateLoss();
    }

    /**
     * 带动画
     * @param hostActivity
     * @param fromFragment
     * @param toFragment
     * @param containerId
     * @param inAnim
     */
    public void switchFragment(FragmentActivity hostActivity, Fragment fromFragment, Fragment toFragment, int containerId,@AnimatorRes @AnimRes int inAnim){
        FragmentTransaction transaction =hostActivity.getSupportFragmentManager().beginTransaction();
        transaction.hide(fromFragment);//隐藏上个Fragment
        if(!toFragment.isAdded()) {
            transaction.add(containerId, toFragment);
        }
        transaction.setCustomAnimations(inAnim, 0);
        transaction.show(toFragment).commitAllowingStateLoss();
    }


    //带出现动画的方式
    public void startFragment(FragmentActivity hostActivity, Fragment fragment, int containerId, @AnimatorRes @AnimRes int inAnim) {
        if (hostActivity != null && containerId != 0) {
            FragmentManager fragmentManager = hostActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment targetFragment = fragmentManager.findFragmentByTag(fragment.getClass().getCanonicalName());
            if (targetFragment != null && targetFragment.isAdded()) {
                if (targetFragment.isHidden()) {
                    transaction.show(targetFragment);
                }
            } else {
                transaction.setCustomAnimations(inAnim, 0);
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
    //带关闭动画的方式
    public void closeFragment(Fragment fragment, @AnimatorRes @AnimRes int outAnim) {
        if (fragment.getActivity() != null) {
            FragmentActivity activity = fragment.getActivity();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment targetFragment = fragmentManager.findFragmentByTag(fragment.getClass().getCanonicalName());
            if (targetFragment != null && targetFragment.isAdded()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(0, outAnim);
                transaction.remove(fragment).commitNowAllowingStateLoss();
            }
        }
    }

    public <T extends Fragment> Fragment findFragment(FragmentActivity hostActivity, Class<T> fragmentClass) {
        FragmentManager fragmentManager = hostActivity.getSupportFragmentManager();

        Fragment targetFragment = fragmentManager.findFragmentByTag(fragmentClass.getCanonicalName());
        return targetFragment;
    }

    //扩展先关闭，后续有需求再打开
//    public class AnimationBean {
//         private int foreInRes;
//         private int backOutRes;
//         private int backInRes;
//         private int foreOutRes;
//
//        public AnimationBean(
//                @AnimatorRes @AnimRes int foreInRes,
//                @AnimatorRes @AnimRes int backOutRes,
//                @AnimatorRes @AnimRes int backInRes,
//                @AnimatorRes @AnimRes int foreOutRes) {
//            this.foreInRes = foreInRes;
//            this.backOutRes = backOutRes;
//            this.backInRes = backInRes;
//            this.foreOutRes = foreOutRes;
//        }
//    }
}
