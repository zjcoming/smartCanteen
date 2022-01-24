package com.base.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by chenxiong
 * date 1/8/22
 */
data class RightMenuBean(
    @SerializedName("id")
    val foodId: Int,
    @SerializedName("iconImage")
    val littleImgUrl: String,
    @SerializedName("detailImage")
    val bigImgUrl: String,
    @SerializedName("recommendImage")
    val recommendImage: String,
    @SerializedName("isRecommend")
    val isRecommend: Int,
    @SerializedName("name")
    val foodName: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("saleCount")
    val saleCount: Int,
    @SerializedName("praiseDegree")
    val praiseDegree: Int,
    @SerializedName("food_price")
    val price: Float,
    @SerializedName("stock")
    val foodCount: Int,
    @SerializedName("floor")
    val floor: Int
)

