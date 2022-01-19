package com.base.bean

import com.base.recyclerview.IBaseCustomViewModel
import com.google.gson.annotations.SerializedName

/**
 * Created by chenxiong
 * date 1/8/22
 */
data class RightMenuBean(
    @SerializedName("food_little_img_url")
    val littleImgUrl: String,
    @SerializedName("food_name")
    val foodName: String,
    @SerializedName("food_tags")
    val tags: List<String>,
    @SerializedName("sale_count")
    val saleCount: Int,
    @SerializedName("praise_degree")
    val praiseDegree: Int,
    @SerializedName("food_price")
    val price: Float,
    @SerializedName("food_total_count")
    val foodCount: Int
)

