package com.base.bean

import com.base.recyclerview.IBaseCustomViewModel

/**
 * Created by chenxiong
 * date 1/8/22
 */
data class LeftMenuModel(
    val title: String,
    var selected: Boolean): IBaseCustomViewModel
