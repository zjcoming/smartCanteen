package com.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.base.util.inflateBindingWithGeneric

/**
 * Created by chenxiong
 * date 11/26/21
 */
abstract class BaseFragment<VIEW: ViewBinding>: Fragment() {
    private var _binding: VIEW? = null
    val binding:VIEW get() = _binding!!
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

    abstract fun initViews()

    protected open fun initData() {}

    abstract fun initListener()


}