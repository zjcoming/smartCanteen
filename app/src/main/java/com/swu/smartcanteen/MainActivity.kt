package com.swu.smartcanteen

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseActivity
import com.common.constants.LoginAndRegisterConstants
import com.common.constants.RouteConstants
import com.common.util.RetrofitUtil
import com.swu.smartcanteen.databinding.ActivityMainBinding
import com.swu.smartcanteen.fragment.LoginByPhoneFragment
import com.swu.smartcanteen.fragment.LoginFragment
import com.swu.smartcanteen.fragment.RegisterFragment
import java.lang.reflect.Method

/**
 * 入口Activity
 */
@Route(path = RouteConstants.Module_app.PAGER_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var decorView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //依赖注入
        ARouter.getInstance().inject(this)
        //获取顶层视图
        decorView = getWindow().getDecorView();
    }
    override fun initData() {
        //创建好每一个fragment
        var fragments = arrayOfNulls<Fragment>(12)
        fragments[LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT] = (LoginFragment())
        fragments[LoginAndRegisterConstants.REGISTER_FRAGMENT] = (RegisterFragment())
        fragments[LoginAndRegisterConstants.LOGIN_BY_PHONE] = (LoginByPhoneFragment())
        fragments[LoginAndRegisterConstants.FORGET_PASSWORD] = (LoginByPhoneFragment())

        LoginAndRegisterConstants.fragments = fragments;

        //进行判断 如果刚下载app，则展示登录页面
        createFragment(LoginFragment())

        //创建retrofit
        RetrofitUtil.createRetrofit(LoginAndRegisterConstants.BASE_URL)
    }

    override fun onStart() {
        init()
        super.onStart()
    }
    private fun init() {
        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return
        } else {
            // 获取属性
            decorView!!.systemUiVisibility = flag
        }
    }

    /**
     * 判断是否存在虚拟按键
     * @return
     */
    fun checkDeviceHasNavigationBar(): Boolean {
        var hasNavigationBar = false
        val rs: Resources = resources
        val id: Int = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m: Method = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }
        return hasNavigationBar
    }

    fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
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
    fun changeFragment(fragmentNum: Int?) {
        LoginAndRegisterConstants.CURRENT_PAGE_INT = fragmentNum!!
        var fragment: Fragment = LoginByPhoneFragment()
        if(fragmentNum == LoginAndRegisterConstants.LOGIN_AND_REGISTER_FRAGMENT || fragmentNum == LoginAndRegisterConstants.REGISTER_FRAGMENT){
            //如果是登录注册页面，则可以用缓存的
            fragment = LoginAndRegisterConstants.fragments[fragmentNum]
        }else if (fragmentNum == LoginAndRegisterConstants.LOGIN_BY_PHONE || fragmentNum == LoginAndRegisterConstants.FORGET_PASSWORD){
            //如果是“手机号登录”页面或者是“忘记密码“页面，则需要重新创建。
            fragment = LoginByPhoneFragment()//LoginAndRegisterConstants.fragments[fragmentNum]
        }else{
            return
        }
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