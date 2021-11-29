package com.swu.smartcanteen.widget

import android.content.Context
import android.graphics.*
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import com.base.util.UIUtils
import com.google.android.material.textfield.TextInputEditText

/**
 * Created by chenxiong
 * date 11/28/21
 */
/*
 * 自定义验证码输入框
 */
class VerifyCodeInputTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    style: Int = 0
) : TextInputEditText(context, attr, style) {
    private val normalVerifyTextColor = Color.parseColor("#4D4D4D")
    private val sentVerifyTextColor = Color.parseColor("#334D4D4D")

    private val normalLinePaint = Paint().apply {
        color = Color.parseColor("#D8D8D8")
        this.style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private val focusedLinePaint = Paint().apply {
        color = Color.parseColor("#71C9CE")
        this.style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private val verifyCodeFramePaint = Paint().apply {
        color = Color.BLACK
        this.style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val verifyTextPaint = Paint().apply {
        color = normalVerifyTextColor
        this.style = Paint.Style.FILL_AND_STROKE
        textSize = UIUtils.dp2px(context,18).toFloat()
        textAlign = Paint.Align.CENTER
    }

    private var hasFocus = false
    private var verifyEnabled = true
    private var couldSendMsg = true

    private val verifyCodeWidth = UIUtils.dp2px(context,100)
    private val seconds = 60 * 1000L

    private var verifyText = "获取验证码"

    //验证码框
    private lateinit var mVerifyRect: Rect

    //发送验证码的回调
    private var sendVerifyCallBack:(()->Boolean)? = null

    //倒计时timer
    private val countDownTimer = object : CountDownTimer(seconds,1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (verifyEnabled) {
                verifyTextPaint.color = sentVerifyTextColor
                verifyEnabled = false
            }
            val hintCount = (millisUntilFinished / 1000).toInt()
            verifyText = "重新发送 $hintCount"
            invalidate()
        }

        override fun onFinish() {
            verifyText = "获取验证码"
            verifyEnabled = true
            couldSendMsg = true
            verifyTextPaint.color = normalVerifyTextColor
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mVerifyRect = Rect(measuredWidth-verifyCodeWidth, top+5, measuredWidth-5, top+measuredHeight-12)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (hasFocus) {
            canvas?.drawLine(0f,height-5f,width.toFloat(),height-5f,focusedLinePaint)
        } else {
            canvas?.drawLine(0f,height-5f,width.toFloat(),height-5f,normalLinePaint)
        }
        canvas?.drawRoundRect(
            mVerifyRect.left.toFloat(), mVerifyRect.top.toFloat(),
            mVerifyRect.right.toFloat(), mVerifyRect.bottom.toFloat(),
            3f,3f,verifyCodeFramePaint)
        val fontMetrics = verifyTextPaint.fontMetrics
        val space = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
        canvas?.drawText(verifyText, ((mVerifyRect.left+mVerifyRect.right)/2).toFloat(),
            (mVerifyRect.bottom+mVerifyRect.top)/2+space,verifyTextPaint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        val point = Point(event.x.toInt(), event.y.toInt())
        if (mVerifyRect.contains(point.x,point.y) && verifyEnabled) {
            //位于点击区域，发送验证码
            sendVerifyCode()
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        hasFocus = focused
        invalidate()
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    private fun sendVerifyCode() {
        if (couldSendMsg) {
            couldSendMsg = false
        } else {
            return
        }
        //合法性校验流程
        val isMobile = sendVerifyCallBack?.let {
            it.invoke()
        }
        if (isMobile == true) {
            countDownTimer.cancel()
            countDownTimer.start()
        } else {
            couldSendMsg = true
        }
    }

    fun setVerifyCallBack(callBack:()->Boolean) {
        sendVerifyCallBack = callBack
    }
}