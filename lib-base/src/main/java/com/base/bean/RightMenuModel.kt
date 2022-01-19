package com.base.bean

import com.base.recyclerview.IBaseCustomViewModel
import com.google.gson.annotations.SerializedName

/**
 * Created by chenxiong
 * date 1/8/22
 */
data class RightMenuModel(
    val groupName: String,
    val foodName: String,
    val tags: List<String>,
    val saleCount: Int,
    val praiseDegree: Int,
    val price: Float
): IBaseCustomViewModel
