package com.swu.module_order.util

import com.swu.module_order.model.LeftMenuBean
import com.swu.module_order.model.RightMenuBean

/**
 * Created by chenxiong
 * date 1/8/22
 */
object MockDataUtil {

    fun getLeftMenuData():List<LeftMenuBean> {
        val leftMenuBeans = mutableListOf<LeftMenuBean>()
        repeat(20){
            leftMenuBeans.add(LeftMenuBean("推荐${it+1}",false))
        }
        return leftMenuBeans
    }

    fun getRightMenuData():List<RightMenuBean> {
        val rightMenuBeans = mutableListOf<RightMenuBean>()
        for (i in 0 until 20) {
            repeat(4){
                rightMenuBeans.add(RightMenuBean(
                    "推荐${i+1}",
                    "推荐${i+1}",
                    listOf("甜食","香甜","好吃"),
                    1000,
                    85,
                    30.21f
                ))
            }
        }
        return rightMenuBeans
    }

}