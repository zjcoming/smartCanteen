package com.common.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.swu.lib_common.R

/**
 * Created by chenxiong
 * date 2/7/22
 */
class LoadingCallback : Callback() {
    override fun onCreateView() =  R.layout.layout_loading

    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

}