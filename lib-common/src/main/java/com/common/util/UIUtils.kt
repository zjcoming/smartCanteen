package com.common.util

import android.content.Context
import android.widget.Toast

/**
 * Created by chenxiong
 * date 11/27/21
 */
/*
 * 和UI相关的一些工具
 */
object UIUtils {

    fun showToast(context: Context, content: String) {
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
    }
}