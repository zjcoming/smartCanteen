package com.base.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.base.ApplicationContext

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
    fun showToast(content: String) {
        Toast.makeText(ApplicationContext.getContext(),content,Toast.LENGTH_SHORT).show()
    }

    fun getLayoutInflater(context: Context) = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun dp2px(context: Context, dp: Int) = (context.resources.displayMetrics.density * dp + 0.5f).toInt()

    fun hideKeyBoard(editText: EditText) {
        val inputMethodManager=ApplicationContext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    fun showKeyBoard(editText: EditText) {
        val inputMethodManager=ApplicationContext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, 0)
    }

    fun getInputMethodManager() = ApplicationContext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

}

inline fun <reified T: ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) {
    val params = layoutParams as T
    block(params)
    layoutParams = params
}