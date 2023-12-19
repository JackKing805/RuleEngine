package com.cool.jerry.i

import com.cool.jerry.rt_engine.RtParser
import java.lang.reflect.Method

data class InjectMethod(
    val name:String,
    val method: Method,
    val ins:Any? = null
){
    fun invokeMethod(vararg params:Any?):Any? {
        return if (ins == null) {
            method.invoke(null,*params)
        }else{
            method.invoke(ins,*params)
        }
    }

    fun isSameMethod(nodes:List<RtParser.ParseResult.ValueResult<*>>):Boolean{
        if (nodes.size!=method.parameterCount){
            return false
        }

        for ((index, node) in nodes.withIndex()) {
            val mp = method.parameters[index].type

            when(node){
                is RtParser.ParseResult.ValueResult.AnyValueResult -> {
                    if (node.value::class.java.isAssignableFrom(mp)){
                        return false
                    }
                }
                is RtParser.ParseResult.ValueResult.FloatValueResult -> {
                    if (
                        mp!=Float::class.java &&
                        mp!=Double::class.java
                    ){
                        return false
                    }
                }
                is RtParser.ParseResult.ValueResult.IntValueResult -> {
                    if (
                        mp!=Int::class.java &&
                        mp!=Long::class.java
                    ){
                        return false
                    }
                }
                is RtParser.ParseResult.ValueResult.StringValueResult -> {
                    if (
                        mp!=String::class.java
                    ){
                        return false
                    }
                }

                is RtParser.ParseResult.ValueResult.BooleanValueResult -> {
                    if (mp!=Boolean::class.java){
                        return false
                    }
                }
            }
        }

        return true
    }
}