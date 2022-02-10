package com.base.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by chenxiong
 * date 1/15/22
 */
data class FoodListBean(
    @SerializedName("group_name")
    val groupName: String,
    @SerializedName("each_group_list")
    val eachGroupList: List<RightMenuBean>
)
