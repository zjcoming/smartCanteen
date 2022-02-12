package com.common.smartcanteen_event_bus

/**
 * Created by chenxiong
 * date 2/12/22
 */
object SmartCanteenEventController {
    private val callbackMap = HashMap<String, SmartCanteenEventCallBack>()

    fun registerCallBack(eventKey: String, callBack: SmartCanteenEventCallBack) {
        if (callbackMap.containsKey(eventKey)) {
            throw RuntimeException("this callback key:$eventKey has been registered, please check...")
        }
        callbackMap[eventKey] = callBack
    }

    fun dispatchEvent(eventKey: String, param: Map<*,*>? = null) {
        val callback = callbackMap[eventKey]
            ?: throw RuntimeException("this callback key:$eventKey does not exist...")
        callback.onEventChange(param)
    }

    fun unregisterCallBack(callBack: SmartCanteenEventCallBack) {
        val iterator = callbackMap.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (callBack == callbackMap[entry.key]) {
                iterator.remove()
            }
        }
    }

}