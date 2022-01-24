package com.swu.module_order.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.base.ApplicationContext
import com.base.util.UIUtils
import com.base.util.updateLayoutParams
import com.common.util.ScreenSizeUtil
import com.swu.module_order.databinding.BuyCarBottomLayoutBinding
import java.lang.ref.WeakReference

/**
 * Created by chenxiong
 * date 1/21/22
 */
class BottomShopCartLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    style: Int = 0
) : LinearLayout(context, attr, style) {
    private lateinit var binding: BuyCarBottomLayoutBinding
    private var mActivityRef: WeakReference<Activity>? = null
    init {
        initView()
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initListener()
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            BottomShopCartDetailDialog(context).show()
        }
    }

    private fun initView() {
        binding = BuyCarBottomLayoutBinding.inflate(UIUtils.getLayoutInflater(context), this, true)
        binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
            height = BOTTOM_CART_HEIGHT
        }
        binding.imageView.updateLayoutParams<ViewGroup.LayoutParams> {
            width = BOTTOM_CART_HEIGHT
            height = BOTTOM_CART_HEIGHT
        }
        binding.tvRawPrice.paint.flags = Paint. STRIKE_THRU_TEXT_FLAG
    }

    fun attach(activity: Activity) {
        if (parent == null) {
            mActivityRef = WeakReference(activity)
            x = 0f
            y = (ScreenSizeUtil.getScreenHeight(context) - ScreenSizeUtil.getNavigationBarHeight(context)).toFloat() - BOTTOM_CART_HEIGHT
            activity.window.decorView.findViewById<ViewGroup>(android.R.id.content).addView(this,layoutParams)
        }
    }

    fun detach() {
        mActivityRef?.get()?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
            ?.removeView(this)
        mActivityRef = null
    }

    @SuppressLint("SetTextI18n")
    fun setRawPrice(price: Float) {
        binding.tvRawPrice.text = "￥$price"
    }

    @SuppressLint("SetTextI18n")
    fun setFinalPrice(price: Float) {
        binding.tvFinalPrice.text = "￥$price"
    }

    companion object {
          val BOTTOM_CART_HEIGHT:Int = (56 / 881.0f * ScreenSizeUtil.getScreenHeight(
              ApplicationContext.getContext()
          )).toInt()
    }

}