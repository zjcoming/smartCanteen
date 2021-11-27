package com.swu.smartcanteen

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.base.BaseActivity
import com.swu.smartcanteen.databinding.ActivityMainBinding
import com.swu.smartcanteen.fragment.LoginFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initData() {
        //进行判断 如果刚下载app，则展示登录页面
        createFragment(LoginFragment())
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