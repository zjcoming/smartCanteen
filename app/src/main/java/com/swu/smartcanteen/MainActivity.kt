package com.swu.smartcanteen

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.BaseActivity
import com.common.constants.LoginAndRegisterConstants
import com.common.util.RetrofitUtil
import com.swu.smartcanteen.databinding.ActivityMainBinding
import com.swu.smartcanteen.fragment.LoginByPhoneFragment
import com.swu.smartcanteen.fragment.LoginFragment
import com.swu.smartcanteen.fragment.RegisterFragment
import com.common.constants.RouteConstants
@Route(path = RouteConstants.Module_app.PAGER_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initData() {
        //创建好每一个fragment
        var fragments = arrayOfNulls<Fragment>(12)
        fragments[LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT] = (LoginFragment())
        fragments[LoginAndRegisterConstants.REGISTER_FRAGMENT] = (RegisterFragment())
        fragments[LoginAndRegisterConstants.LOGINBYPHONE_OR_FORGETPASSWORD] = (LoginByPhoneFragment())
        LoginAndRegisterConstants.fragments = fragments;

        //进行判断 如果刚下载app，则展示登录页面
        createFragment(LoginFragment())

        //创建retrofit
        RetrofitUtil.createRetrofit(LoginAndRegisterConstants.BASE_URL)
    }


    override fun initListener() {

    }

    //创建Fragment
    fun createFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.loginAndRegisterFrag, fragment!!)
        fragmentTransaction.commit()
    }

    //更改Fragment
    fun changeFragment(fragment: Fragment?) {
        //得到管理类
        val fragmentManager = supportFragmentManager
        //开始事务
        val fragmentTransaction = fragmentManager.beginTransaction()
        //事务创建
        fragmentTransaction.replace(R.id.loginAndRegisterFrag, fragment!!)
        fragmentTransaction.addToBackStack(null)
        //事务提交
        fragmentTransaction.commit()
    }
}