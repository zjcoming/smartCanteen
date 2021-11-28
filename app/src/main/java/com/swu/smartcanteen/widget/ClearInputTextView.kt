package com.swu.smartcanteen.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.graphics.contains
import com.base.util.UIUtils
import com.common.util.LogUtil
import com.google.android.material.textfield.TextInputEditText
import com.swu.smartcanteen.R

/**
 * Created by chenxiong
 * date 11/27/21
 */
/*
 * 自定义输入框实现 文本末尾画×以及绘制验证码发送框
 */
@SuppressLint("UseCompatLoadingForDrawables")
class ClearInputTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    style: Int = 0
) : TextInputEditText(context, attr, style), TextWatcher {
    private val normalVerifyTextColor = Color.parseColor("#4D4D4D")
    private val sentVerifyTextColor = Color.parseColor("#334D4D4D")
    private var deleteIcon: Drawable
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
    private var isVerifyMode = false
    private var verifyEnabled = true

    private val mDeleteIconSize = 50
    private val verifyCodeWidth = UIUtils.dp2px(context,100)
    private val seconds = 60 * 1000L

    private var verifyText = "获取验证码"


    //验证码框
    private lateinit var mVerifyRect: Rect

    //发送验证码的回调
    private var sendVerifyCallBack:(()->Boolean)? = null

    //扩展文本监听
    private var extraTextWatcher: TextWatcher? = null

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
            verifyTextPaint.color = normalVerifyTextColor
            invalidate()
        }

    }
    init {
        deleteIcon = context.getDrawable(R.drawable.delete)!!
        deleteIcon.setBounds(0, 0, mDeleteIconSize, mDeleteIconSize)
        parseAttrs(attr)
        addTextChangedListener(this)
    }

    private fun parseAttrs(attr: AttributeSet?) {
        if (attr == null) return
        val typedArray = context.obtainStyledAttributes(attr,R.styleable.ClearInputTextView)
        isVerifyMode = typedArray.getBoolean(R.styleable.ClearInputTextView_verifyMode, false)
        typedArray.recycle()
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

        if (isVerifyMode) {
            canvas?.drawRoundRect(
                mVerifyRect.left.toFloat(), mVerifyRect.top.toFloat(),
                mVerifyRect.right.toFloat(), mVerifyRect.bottom.toFloat(),
                3f,3f,verifyCodeFramePaint)
            val fontMetrics = verifyTextPaint.fontMetrics
            val space = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
            canvas?.drawText(verifyText, ((mVerifyRect.left+mVerifyRect.right)/2).toFloat(),
                (mVerifyRect.bottom+mVerifyRect.top)/2+space,verifyTextPaint)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        val point = Point(event.x.toInt(), event.y.toInt())
        if (!isVerifyMode) {
            val rect = Rect(measuredWidth-mDeleteIconSize, 0, measuredWidth, measuredHeight)
            if (rect.contains(point)) {
                setText("")
            }
        } else {
            if (mVerifyRect.contains(point) && verifyEnabled) {
                //位于点击区域，发送验证码
                sendVerifyCode()
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private fun sendVerifyCode() {
        //合法性校验流程
        val isMobile = sendVerifyCallBack?.let {
            it.invoke()
        }
        if (isMobile == true) {
            countDownTimer.cancel()
            countDownTimer.start()
        }
    }

    private fun getPhoneNumber() = SpannableStringBuilder(editableText).apply {
                delete(3,4)
                delete(7,8)
        }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        hasFocus = focused
        invalidate()
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        extraTextWatcher?.beforeTextChanged(s,start,count, after)
        if (isVerifyMode) return
        if (TextUtils.isEmpty(text)) {
            setCompoundDrawables(null,null,null,null)
        } else {
            setCompoundDrawables(null,null,deleteIcon,null)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        extraTextWatcher?.afterTextChanged(s)
        if (isVerifyMode) return
        postInvalidate()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        extraTextWatcher?.onTextChanged(text,start,lengthBefore,lengthAfter)
        if (isVerifyMode) return
        if (hasFocus) {
            if (TextUtils.isEmpty(text)) {
                setCompoundDrawables(null,null,null,null)
            } else {
                setCompoundDrawables(null,null,deleteIcon,null)
            }
        }
    }

    fun setExtraTextWatcher(textWatcher: TextWatcher) {
        extraTextWatcher = textWatcher
    }

    fun setVerifyCallBack(callBack:()->Boolean) {
        sendVerifyCallBack = callBack
    }

}