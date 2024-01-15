package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge0

class FunctionCallBridge0Impl(
    val onCall:()->Any
):FunctionCallBridge0 {
    override fun call():Any {
        return onCall()
    }
}