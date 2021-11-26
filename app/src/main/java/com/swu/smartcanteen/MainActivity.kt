package com.swu.smartcanteen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //到这个页面的时候是top_in效果，即将要消失的Activity，使用silent效果
        //overridePendingTransition(R.anim.top_in, R.anim.silent)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}