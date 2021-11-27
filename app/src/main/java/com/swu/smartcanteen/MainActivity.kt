package com.swu.smartcanteen

import android.os.Bundle
import com.base.BaseActivity
import com.swu.smartcanteen.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initListener() {

    }
}