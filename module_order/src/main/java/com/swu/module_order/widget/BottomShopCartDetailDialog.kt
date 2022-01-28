package com.swu.module_order.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BaseDialog
import com.base.util.UIUtils
import com.base.util.updateLayoutParams
import com.swu.module_order.R
import com.swu.module_order.adapter.BuyingCarAdapter
import com.swu.module_order.databinding.BottomShopCartDetailBinding
import com.swu.module_order.model.BuyingCarBean

/**
 * Created by chenxiong
 * date 1/21/22
 */
class BottomShopCartDetailDialog(context: Context) : BaseDialog<BottomShopCartDetailBinding>(context) {
    private val carData: MutableList<BuyingCarBean> = mutableListOf()

    override fun getLayoutId() = R.layout.bottom_shop_cart_detail

    override fun initData() {
        window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(context, 400))
        }
        binding.rvBottomCartDetail.updateLayoutParams<ViewGroup.LayoutParams> {
            (this as ViewGroup.MarginLayoutParams).setMargins(0,0,0,BottomShopCartLayout.BOTTOM_CART_HEIGHT)
        }

        //模拟数据
        for (i in 1..20) {
            carData.add(BuyingCarBean("水煮肉片$i", "￥18.88", "小份", "微辣", 1))
        }

        binding.rvBottomCartDetail.adapter = BuyingCarAdapter(carData, context)
        binding.rvBottomCartDetail.layoutManager = LinearLayoutManager(context)
    }

    override fun initListener() {

    }

}