package com.base.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by chenxiong
 * date 1/15/22
 */
data class FoodDetailBean(
    @SerializedName("food_big_img_url")
    val foodBigImgUrl: String,
    @SerializedName("food_detail_title")
    val foodDetailTitle: String,
    @SerializedName("seller_desc")
    val sellerDesc: String,
    @SerializedName("main_material")
    val mainMaterial: String,
    @SerializedName("food_type")
    val foodType: String,
    @SerializedName("make_way")
    val makeWay: String,
    @SerializedName("single_count")
    val singleCount: Int,
    @SerializedName("flavor")
    val flavor: String
)
