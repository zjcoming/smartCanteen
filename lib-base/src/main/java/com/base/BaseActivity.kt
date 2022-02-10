package com.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.swu.lib_base.R

/**
 * Created by chenxiong
 * date 11/26/21
 */
abstract class BaseActivity<VIEW : ViewBinding>: AppCompatActivity() {
    lateinit var binding: VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
    }

    abstract fun initData()

    abstract fun initListener()

    //跳转并可设置传参
    inline fun <reified T : BaseActivity<ViewBinding>> startActivityAndFinish(parseParamsCall: Intent.() -> Unit = {}) {
        Intent(this, T::class.java).let { intent ->
            intent.parseParamsCall()
            startActivity(intent)
        }
        finish()
    }

    protected fun openFragment(fragment: Fragment) {
        FragmentUtil.getInstance().startFragment(this, fragment, R.id.container)
    }
}