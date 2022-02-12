package com.swu.module_order

import android.util.Log
import com.base.bean.FoodListPageBean
import com.common.handler.RequestHandler
import com.common.requestbase.AppObserver
import com.common.requestbase.ResponseModel
import com.common.smartcanteen_event_bus.SmartCanteenEvent
import com.common.smartcanteen_event_bus.SmartCanteenEventController

/**
 * Created by chenxiong
 * date 2/12/22
 */
object FoodMenuFetchHelper {

    var fetchDataSuccess = false
        private set

    fun fetchMainMenuData() {
        RequestHandler.fetchFoodList(object : AppObserver<ResponseModel<FoodListPageBean>>() {
            override fun onData(o: ResponseModel<FoodListPageBean>) {
                Log.e("cx",o.data.foodListBeans.toString())
                fetchDataSuccess = o.result == "SUCCESS"
                FoodDataManager.sInstance.setFoodListPageData(o.data)
                SmartCanteenEventController.dispatchEvent(SmartCanteenEvent.FETCH_FOOD_DATA)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                fetchDataSuccess = false
                SmartCanteenEventController.dispatchEvent(SmartCanteenEvent.FETCH_FOOD_DATA)
            }
        }, 1)
    }

}