package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2


class FunctionCallBridge2Impl(
    val onCall:(params1: Any,params2:Any)->Any
):FunctionCallBridge2 {
    override fun call(params1: Any, params2: Any):Any {
        return onCall(params1,params2)
    }

}