package com.cool.jerry.bridge.impl

import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2
import com.cool.jerry.bridge.FunctionCallBridge3
import com.cool.jerry.bridge.FunctionCallBridge4


class FunctionCallBridge4Impl(
    val onCall:(params1: Any,params2:Any, params3: Any, params4: Any)->Unit
): FunctionCallBridge4 {
    override fun call(params1: Any, params2: Any, params3: Any, params4: Any) {
        onCall(params1,params2,params3,params4)
    }
}