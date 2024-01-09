package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge0

class FunctionCallBridge0Impl(
    val onCall:()->Unit
):FunctionCallBridge0 {
    override fun call() {
        onCall()
    }
}