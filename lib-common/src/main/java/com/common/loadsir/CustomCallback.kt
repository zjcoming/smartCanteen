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
class CustomCallback : Callback() {

    override fun onCreateView() = R.layout.layout_custom

    override fun onReloadEvent(context: Context, view: View): Boolean {
        Toast.makeText(context.applicationContext, "Hello buddy, how r u! :p", Toast.LENGTH_SHORT)
            .show()
        view.findViewById<View>(R.id.iv_gift).setOnClickListener {
            Toast.makeText(context.applicationContext, "It's your gift! :p", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}