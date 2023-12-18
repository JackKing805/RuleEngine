package com.cool.jerry.i

import java.lang.reflect.Method

data class InjectMethod(
    val ins:Any?,
    val method: Method
){
    fun invokeMethod(vararg params:Any):Any? {
        return if (ins == null) {
            method.invoke(null,*params)
        }else{
            method.invoke(ins,*params)
        }
    }
}