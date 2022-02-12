package com.common.loadsir

import android.content.Context
import android.view.View
import android.widget.Toast
import com.kingja.loadsir.callback.Callback
import com.swu.lib_common.R

/**
 * Created by chenxiong
 * date 2/7/22
 */
class TimeoutCallback : Callback() {
    override fun onCreateView() = R.layout.layout_timeout

    override fun onReloadEvent(context: Context, view: View?): Boolean {
        Toast.makeText(context.applicationContext, "Connecting to the network again!", Toast.LENGTH_SHORT).show()
        return false
    }

}