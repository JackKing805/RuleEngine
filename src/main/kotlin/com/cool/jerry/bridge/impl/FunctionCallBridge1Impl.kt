package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge1


class FunctionCallBridge1Impl(
    val onCall:(params1: Any)->Any
):FunctionCallBridge1 {
    override fun call(params1: Any):Any {
        return onCall(params1)
    }
}