package com.base.util

import android.content.Context
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener

/**
 * Created by chenxiong
 * date 11/28/21
 */
object BaseUtil {
     fun sendMsgCode(phoneNum: String, context: Context) {
        BmobSMS.requestSMSCode(phoneNum,"cx",object : QueryListener<Int>(){
            override fun done(p0: Int?, p1: BmobException?) {
                if (p1 == null) {
                    UIUtils.showToast(context,"短信发送成功~")
                } else {
                    UIUtils.showToast(context,"短信发送失败！")
                }
            }
        })
    }

    fun verifyCode(phoneNum: String, code: String, callBack:((Boolean)->Unit)){
        BmobSMS.verifySmsCode(phoneNum,code,object :UpdateListener(){
            override fun done(p0: BmobException?) {
                callBack.invoke(p0 == null)
            }
        })
    }
}