package com.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.base.util.bindViewWithGeneric
import com.swu.lib_base.R

/**
 * Created by chenxiong
 * date 11/26/21
 */
abstract class BaseDialog<VIEW : ViewBinding>(context: Context, style: Int = R.style.BaseDialog) :
    Dialog(context, style) {
    lateinit var binding: VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(getLayoutId(), null, false)
        binding = bindViewWithGeneric(view)
        setContentView(view)

        initData()
        initListener()
    }

    abstract fun getLayoutId(): Int

    abstract fun initData()

    abstract fun initListener()

}