package com.swu.module_order.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.swu.module_order.widget.BottomShopCartLayout

/**
 * Created by chenxiong
 * date 1/28/22
 */
class FoodPageViewModel(private val context: Context) : ViewModel() {
    private val bottomShopCartLayout: BottomShopCartLayout by lazy {
        BottomShopCartLayout(context).apply {
            setSettlementCallBack {

            }
        }
    }

    var diningWay: String? = null

    fun getShopCart() = bottomShopCartLayout

}