package com.common.smartcanteen_event_bus

/**
 * Created by chenxiong
 * date 2/12/22
 */
interface SmartCanteenEventCallBack {
    fun onEventChange(param: Map<*,*> ? = null)
}