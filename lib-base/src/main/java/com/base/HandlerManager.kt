package com.base

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

/**
 * Created by chenxiong
 * date 1/20/22
 */
object HandlerManager {
    private val uiHandler = Handler(Looper.getMainLooper())

    private val handlerThread = HandlerThread("Time-Consume-Thread").also { it.start() }

    private val subHandler = Handler(handlerThread.looper)

    fun isUIThread(): Boolean = (Looper.getMainLooper() == Looper.myLooper())

    //for java
    fun doInUIThread(task: Runnable) {
        uiHandler.post(task)
    }

    fun doInUIThread(task: Runnable, delayTime: Long) {
        uiHandler.postDelayed(task, delayTime)
    }

    fun doInSubThread(task: Runnable) {
        subHandler.post(task)
    }

    fun doInSubThread(task: Runnable, delayTime: Long) {
        subHandler.postDelayed(task, delayTime)
    }
    // for kt
    fun doInUIThread(task: ()->Unit) {
        uiHandler.post(task)
    }

    fun doInUIThread(delayTime: Long, task: ()->Unit) {
        uiHandler.postDelayed(task, delayTime)
    }

    fun doInSubThread(task: ()->Unit) {
        subHandler.post(task)
    }

    fun doInSubThread(delayTime: Long, task: ()->Unit) {
        subHandler.postDelayed(task, delayTime)
    }


}