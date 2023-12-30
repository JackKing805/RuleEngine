package com.cool.jerry.model

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

    fun isSameMethod(nodes:List<ParseResult.ValueResult<*>>):Boolean{
        if (method.parameterCount==0){
            if (nodes.isEmpty()){
                return true
            }
        }

        if (nodes.size!=method.parameterCount){
            return false
        }

        for ((index, node) in nodes.withIndex()) {
            val mp = method.parameters[index].type

            when(node){
                is ParseResult.ValueResult.AnyValueResult -> {
                    if (mp == Any::class.java){
                        return true
                    }
                    if (node.value::class.java.isAssignableFrom(mp)){
                        return true
                    }
                }
                is ParseResult.ValueResult.FloatValueResult -> {
                    if (node.isDouble()){
                        if (
                            mp==Double::class.java
                        ){
                            return true
                        }
                    }else{
                        if (
                            mp==Float::class.java
                        ){
                            return true
                        }
                    }
                }
                is ParseResult.ValueResult.IntValueResult -> {
                    if (node.isLong()){
                        if (
                            mp==Long::class.java
                        ){
                            return true
                        }
                    }else{
                        if (
                            mp==Int::class.java ||
                            mp == Long::class.java
                        ){
                            return true
                        }
                    }
                }
                is ParseResult.ValueResult.StringValueResult -> {
                    if (
                        mp==String::class.java
                    ){
                        return true
                    }
                }

                is ParseResult.ValueResult.BooleanValueResult -> {
                    if (mp==Boolean::class.java){
                        return true
                    }
                }

                is ParseResult.ValueResult.ArrayValueResult -> {
                    if (mp==Array::class.java || mp == List::class.java){
                        return true
                    }
                }
            }
        }

        return false
    }
}