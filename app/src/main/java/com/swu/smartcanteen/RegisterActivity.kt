package com.swu.smartcanteen

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.base.BaseActivity
import com.base.util.BaseUtil
import com.base.util.UIUtils
import com.swu.smartcanteen.databinding.ActivityRegisterBinding
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.regex.Pattern

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private var shouldAutoSplit = true
    private var hasSentVerifyCode = false
    private var sendVerifyCodeTime = 0L
    private var validVerifyActiveTime = 5 * 60 * 1000
    private lateinit var tipsView: Array<TextView>
    override fun initData() {
            tipsView = arrayOf(binding.verifyTip,binding.snumTip,binding.twiceTip,binding.telTip)
    }

    override fun initListener() {
        binding.tellNum.setExtraTextWatcher(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shouldAutoSplit = count == 1
            }

            override fun afterTextChanged(s: Editable?) {
                if (shouldAutoSplit) {
                    s.toString().length.also { len ->
                        if (len == 3 || len == 8) {
                            s?.append(" ")
                        }
                    }
                }
            }
        })
        binding.verifyCode.setVerifyCallBack {
            val isLegal = if (binding.tellNum.editableText.length != 13) false else isMobilePhone()
            if (!isLegal) {
                showAndDisappear(binding.telTip)
            } else {
                //电话号码合法，可以发送验证码
                hasSentVerifyCode = true
                sendVerifyCodeTime = System.currentTimeMillis()
                BaseUtil.sendMsgCode(getPhoneNum(), this)
            }
            isLegal
        }
        binding.finishBtn.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Boolean> {
                val sNumEmpty = TextUtils.isEmpty(binding.sNum.text)
                val teleNumEmpty = TextUtils.isEmpty(binding.tellNum.text)
                val verifyNumEmpty = TextUtils.isEmpty(binding.verifyCode.text)
                val firstPwdEmpty = TextUtils.isEmpty(binding.firstPwd.text)
                val secondPwdEmpty = TextUtils.isEmpty(binding.secondPwd.text)
                val res = if (sNumEmpty||teleNumEmpty||verifyNumEmpty||firstPwdEmpty||secondPwdEmpty){
                    UIUtils.showToast(this,"请完善信息！")
                    false
                }else {
                    true
                }
                it.onNext(res)
            }).map { upRes->
                var res = false
                if (upRes) {
                    res = checkSNum()
                    if (!res) {
                        showAndDisappear(binding.snumTip)
                    }
                }
                upRes && res
            }.map { upRes->
                var res = false
                if (upRes) {
                    res = binding.firstPwd.text.toString() == binding.secondPwd.text.toString()
                    if (!res) {
                        showAndDisappear(binding.twiceTip)
                    }
                }
                res && upRes
            }.map { upRes->
                var res = false
                if (upRes) {
                    res = hasSentVerifyCode
                    if (!res) {
                        UIUtils.showToast(this,"请发送验证码！")
                    }
                }
                upRes && res
            }.subscribe { res->
                if (!res) return@subscribe
                //最后一步只需校验验证码是否有效
                if (System.currentTimeMillis() - sendVerifyCodeTime > validVerifyActiveTime) {
                    UIUtils.showToast(this, "验证码已过期，请重新发送！")
                }
                BaseUtil.verifyCode(getPhoneNum(), binding.verifyCode.text.toString()) { success ->
                    if (success) {
                        UIUtils.showToast(this, "注册成功！")
                    } else {
                        UIUtils.showToast(this, "注册失败！")
                    }
                }
            }
        }
    }

    private fun showAndDisappear(view: View) {
        view.visibility = View.VISIBLE
        view.postDelayed({
            view.visibility = View.INVISIBLE
        }, 2000)
    }

    private fun checkSNum(): Boolean{
        val sNum = binding.sNum.text.toString()
        val regExp = "[2][2][2][0][12]\\d{10}" //学号必须是以2220 1/2开头
        var p = Pattern.compile(regExp)
        val m = p.matcher(sNum)
        return m.matches()
    }

    private fun getPhoneNum() =
        SpannableStringBuilder(binding.tellNum.editableText).also { builder ->
            builder.delete(3, 4)
            builder.delete(7, 8)
        }.toString()

    private fun isMobilePhone(): Boolean {
        val number = getPhoneNum()
        val regExp = "[1][3456789]\\d{9}"
        var p = Pattern.compile(regExp)
        val m = p.matcher(number)//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return if (TextUtils.isEmpty(number)) {
            false
        } else {
            m.matches()
        }
    }

    private fun restore() {
        tipsView.forEach {
            if (it.visibility == View.VISIBLE) {
                it.visibility = View.INVISIBLE
            }
        }
    }


}
