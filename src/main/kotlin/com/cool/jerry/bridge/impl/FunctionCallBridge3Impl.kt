package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2
import com.cool.jerry.bridge.FunctionCallBridge3


class FunctionCallBridge3Impl(
    val onCall:(params1: Any,params2:Any, params3: Any)->Any
): FunctionCallBridge3 {
    override fun call(params1: Any, params2: Any, params3: Any):Any {
        return onCall(params1,params2,params3)
    }

}