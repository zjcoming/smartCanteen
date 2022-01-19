package com.swu.module_order.util

import com.base.bean.LeftMenuModel
import com.base.bean.RightMenuModel

/**
 * Created by chenxiong
 * date 1/8/22
 */
object MockDataUtil {

    fun getLeftMenuData():List<LeftMenuModel> {
        val leftMenuBeans = mutableListOf<LeftMenuModel>()
        repeat(20){
            leftMenuBeans.add(LeftMenuModel("推荐${it+1}",false))
        }
        return leftMenuBeans
    }

    fun getRightMenuData():List<RightMenuModel> {
        val rightMenuBeans = mutableListOf<RightMenuModel>()
        for (i in 0 until 20) {
            repeat(4){
                rightMenuBeans.add(
                    RightMenuModel(
                    "推荐${i+1}",
                    "推荐${i+1}",
                    listOf("甜食","香甜","好吃"),
                    1000,
                    85,
                    30.21f
                )
                )
            }
        }
        return rightMenuBeans
    }

}