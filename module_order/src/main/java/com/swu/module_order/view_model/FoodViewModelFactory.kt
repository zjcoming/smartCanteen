package com.swu.module_order.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by chenxiong
 * date 1/28/22
 */
class FoodViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodPageViewModel::class.java)) {
            return FoodPageViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}