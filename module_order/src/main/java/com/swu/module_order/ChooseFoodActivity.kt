package com.swu.module_order

import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseActivity
import com.common.util.FragmentUtil
import com.swu.module_order.databinding.ActivityChooseFoodBinding
import com.example.module_person.fragment.MessageFragment

class ChooseFoodActivity : BaseActivity<ActivityChooseFoodBinding>() {

    override fun initData() {
        //依赖注入
        ARouter.getInstance().inject(this)
        FragmentUtil.getInstance().startFragment(
            this,
//            ChooseFoodFragment(this).apply {
//                object : BaseFragment.FragmentJumpListener {
//                    override fun jumpToFragment(
//                        fragment: Fragment,
//                        anim: Int
//                    ) {
//                        FragmentUtil.getInstance().startFragment(
//                            requireActivity(), fragment, R.id.container,
//                            anim
//                        )
//                    }
//                }
//            },
                com.example.module_person.fragment.MessageFragment(),
            R.id.container
        )
    }

    override fun initListener() {

    }


}