package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge1


class FunctionCallBridge1Impl(
    val onCall:(params1: Any)->Unit
):FunctionCallBridge1 {
    override fun call(params1: Any) {
        onCall(params1)
    }
}