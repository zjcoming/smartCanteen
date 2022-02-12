package com.swu.module_order

import com.base.bean.FoodListPageBean
import com.base.bean.LeftMenuModel
import com.base.bean.RightMenuBean
import com.base.bean.RightMenuModel

/**
 * Created by chenxiong
 * date 2/12/22
 */
class FoodDataManager private constructor(){

    private var _listPageBean: FoodListPageBean? = null

    val recommendImgList: List<String>
        get() = _listPageBean?.recommendImgList ?: listOf()

    val leftMenuList: List<LeftMenuModel>
        get() = _listPageBean?.foodListBeans?.map {
            LeftMenuModel(it.groupName,false)
        } ?: listOf()

    val rightMenuList: List<RightMenuModel>
        get() = transformToRightMenuModel()

    private fun transformToRightMenuModel(): List<RightMenuModel>{
        _listPageBean ?: return listOf()
        val rightMenuModels = mutableListOf<RightMenuModel>()
        var index = 0
        for (foodListBean in _listPageBean!!.foodListBeans) {
            if (index >= 3 ) break
            for (foodBean in foodListBean.eachGroupList) {
                rightMenuModels.add(RightMenuModel(foodListBean.groupName,foodBean.foodName,foodBean.tags,foodBean.saleCount,foodBean.praiseDegree,foodBean.price))
            }
            index++
        }
        return rightMenuModels
    }

    fun setFoodListPageData(foodListPageBean: FoodListPageBean) {
        this._listPageBean = foodListPageBean
    }
    companion object {
        val sInstance: FoodDataManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            FoodDataManager()
        }
    }

}