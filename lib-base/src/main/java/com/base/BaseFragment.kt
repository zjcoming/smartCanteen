package com.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.base.util.inflateBindingWithGeneric

/**
 * Created by chenxiong
 * date 11/26/21
 */
abstract class BaseFragment<VIEW : ViewBinding>: Fragment() {
    private var _binding: VIEW? = null
    val binding:VIEW get() = _binding!!
    private var jumpListener: FragmentJumpListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(layoutInflater, container, false)
        _binding?.root?.isClickable = true
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //将Fragment大小设置为屏幕大小减去底部导航栏大小
        setRealSize()
        initViews()
        initListener()
    }

    protected open fun setRealSize(){

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun jumpToFragment(fragment: Fragment, @AnimatorRes @AnimRes inAnim: Int) {
        jumpListener?.jumpToFragment(fragment, inAnim)
    }

    @JvmName("setJumpFragmentCallBack1")
    fun setJumpFragmentCallBack(jumpListener: FragmentJumpListener) {
        this.jumpListener = jumpListener
    }

    abstract fun initViews()

    protected open fun initData() {}

    abstract fun initListener()

    interface FragmentJumpListener {
        fun jumpToFragment(fragment: Fragment, @AnimatorRes @AnimRes anim: Int)
    }

}