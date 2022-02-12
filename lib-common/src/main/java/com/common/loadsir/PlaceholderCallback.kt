package com.common.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.swu.lib_common.R

/**
 * Created by chenxiong
 * date 2/7/22
 */
class PlaceholderCallback : Callback() {
    override fun onCreateView() = R.layout.layout_placeholder

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

}