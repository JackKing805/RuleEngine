package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2


class FunctionCallBridge2Impl(
    val onCall:(params1: Any,params2:Any)->Unit
):FunctionCallBridge2 {
    override fun call(params1: Any, params2: Any) {
        onCall(params1,params2)
    }

}