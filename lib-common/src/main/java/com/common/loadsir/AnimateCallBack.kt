package com.common.loadsir

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.kingja.loadsir.callback.Callback
import com.swu.lib_common.R

/**
 * Created by chenxiong
 * date 2/7/22
 */
class AnimateCallBack : Callback() {
    private lateinit var context: Context
    private lateinit var animateView: View

    override fun onCreateView() = R.layout.layout_animate

    override fun onAttach(context: Context, view: View?) {
        this.context = context
        animateView = view!!.findViewById(R.id.view_animate)
        RotateAnimation(
            0F, 359F, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            repeatCount = Integer.MAX_VALUE
            fillAfter = true
            interpolator = LinearInterpolator()
        }.also { animateView.startAnimation(it) }
        Toast.makeText(context.applicationContext, "start animation", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        animateView.clearAnimation()
        Toast.makeText(context.applicationContext, "stop animation", Toast.LENGTH_SHORT).show()
    }
}