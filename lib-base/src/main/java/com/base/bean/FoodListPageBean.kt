package com.base.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by chenxiong
 * date 1/16/22
 */
data class FoodListPageBean(
    @SerializedName("recommend_img_list")
    val recommendImgList: List<String>,
    @SerializedName("food_list_bean")
    val foodListBeans: List<FoodListBean>
)
