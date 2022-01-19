package com.swu.smartcanteen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.base.BaseActivity;
import com.common.constants.RouteConstants;
import com.swu.smartcanteen.databinding.ActivityNavigationBinding;
import com.swu.smartcanteen.fragment.HomeFragment;
import com.swu.smartcanteen.fragment.OrderFragment;
import com.swu.smartcanteen.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhangJun
 * data 2021/11/27
 * des:
 */
@Route(path = RouteConstants.Module_app.PAGER_NAVIGATION)
public class NavigationActivity extends BaseActivity<ActivityNavigationBinding> {
    //创建一个list用于保存三个Fragment
    private List<Fragment> fragments;
    UserFragment userFragment;
    OrderFragment orderFragment;
    HomeFragment homeFragment;
    //用于记录上个选择的Fragment
    private int lastFragment;

    //底部导航栏bar
    private BottomNavigationBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        //依赖注入
        ARouter.getInstance().inject(this);
        init();
        initBar();
        pressMenu();
    }
    /**
     * 进行相关fragment初始化 并添加到list中
     */
    private void init() {
        if (fragments == null) {
            fragments = new ArrayList<>();
            orderFragment = new OrderFragment();
            userFragment = new UserFragment();
            homeFragment = new HomeFragment();
            fragments.add(homeFragment);
            fragments.add(userFragment);
            fragments.add(orderFragment);
            lastFragment = 0;
            loadHome(homeFragment);
        }
    }

    /**
     * 初始话底部导航bar
     */
    private void initBar() {
        bar = findViewById(R.id.bottom_navigation_bar);
        bar
                .addItem(new BottomNavigationItem(R.drawable.home_selector, R.string.home))
                .addItem(new BottomNavigationItem(R.drawable.user_selector, R.string.user))
                .addItem(new BottomNavigationItem(R.drawable.order_selector, R.string.order))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    /**
     * 重写底部导航栏按键 触发 效果
     */
    private void pressMenu() {
        bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        if(lastFragment!=0)
                        {
                            switchFragment(lastFragment,0);
                            lastFragment=0;
                        }
                        break;
                    case 1:
                        if(lastFragment!=1)
                        {
                            switchFragment(lastFragment,1);
                            lastFragment=1;
                        }
                        break;
                    default:
                        if(lastFragment!=2)
                        {
                            switchFragment(lastFragment,2);
                            lastFragment=2;
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 切换Fragment
     *
     * @param lastFragment
     * @param index
     */
    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments.get(lastFragment));//隐藏上个Fragment
        if (!fragments.get(index).isAdded()) {
            transaction.add(binding.container.getId(), fragments.get(index));
        }
        transaction.show(fragments.get(index)).commitAllowingStateLoss();
    }

    /**
     * 一进来就默认加载首页
     * @param fragment
     */
    private void loadHome(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(binding.container.getId(),fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}