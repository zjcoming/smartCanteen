package com.base.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.base.util.bindViewWithGeneric

/**
 * Created by chenxiong
 * date 11/26/21
 */
abstract class BaseCustomView<VIEW : ViewBinding, DATA : IBaseCustomViewModel> @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null, style: Int = 0
) : FrameLayout(context, attr, style), ICustomView<DATA> {
    protected lateinit var binding: VIEW
    private lateinit var mData: DATA

    init {
        init()
    }

    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(getLayoutId(),this,false)
        binding = bindViewWithGeneric(view)
        addView(binding.root)
    }
    abstract fun getLayoutId(): Int


    override fun setData(data: DATA) {
        mData = data
        setDataToView(data)
    }

    protected abstract fun setDataToView(data: DATA)

}