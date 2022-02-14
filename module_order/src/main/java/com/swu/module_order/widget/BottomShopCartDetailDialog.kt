package com.swu.module_order.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BaseDialog
import com.base.UIUtils
import com.base.updateLayoutParams
import com.common.bean.FoodItemBean
import com.swu.module_order.R
import com.swu.module_order.adapter.BuyingCarAdapter
import com.swu.module_order.databinding.BottomShopCartDetailBinding
import com.swu.module_order.model.BuyingCarBean

/**
 * Created by chenxiong
 * date 1/21/22
 */
class BottomShopCartDetailDialog(context: Context) : BaseDialog<BottomShopCartDetailBinding>(context) {
    val carData: MutableList<FoodItemBean> = mutableListOf()

    override fun getLayoutId() = R.layout.bottom_shop_cart_detail

    override fun initData() {
        window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(context, 400))
        }
        binding.rvBottomCartDetail.updateLayoutParams<ViewGroup.LayoutParams> {
            (this as ViewGroup.MarginLayoutParams).setMargins(
                0,
                0,
                0,
                BottomShopCartLayout.BOTTOM_CART_HEIGHT
            )
        }

        for (i in 1..10) {
            carData.add(FoodItemBean(i, "第i个", "", "大份", "微辣", i, i.toFloat()))
        }

        //模拟数据
//        for (i in 1..20) {
//            carData.add(BuyingCarBean("水煮肉片$i", "￥18.88", "小份", "微辣", 1))
//        }

        binding.rvBottomCartDetail.adapter = BuyingCarAdapter(carData, context)
        binding.rvBottomCartDetail.layoutManager = LinearLayoutManager(context)
    }

    override fun initListener() {

    }

}