package com.cool.jerry.model

import com.cool.jerry.bridge.*
import java.lang.reflect.Method

data class InjectMethod(
    val name: String,
    val method: Method,
    val ins: Any? = null
) {
    fun invokeMethod(vararg params: Any?): Any? {
        return method.invoke(ins, *params)
    }

    val parameterCount = method.parameterCount

    fun isSameMethod(nodes: List<ParseResult.ValueResult<*>>): Boolean {
        if (parameterCount == 0) {
            if (nodes.isEmpty()) {
                return true
            }
        }

        if (nodes.size != parameterCount) {
            return false
        }

        for ((index, node) in nodes.withIndex()) {
            val methodParameter = method.parameters[index].type
            if (methodParameter == Any::class.java){
                continue
            }

            when (node) {
                is ParseResult.ValueResult.AnyValueResult -> {
                    val nodeValue = node.value
                    if (nodeValue is ParseResult.Define.FunctionDefine){
                        return when(val size = nodeValue.functionStatement.parameters.parameters.size){
                            0->  methodParameter== FunctionCallBridge0::class.java
                            1->  methodParameter== FunctionCallBridge1::class.java
                            2->  methodParameter== FunctionCallBridge2::class.java
                            3->  methodParameter== FunctionCallBridge3::class.java
                            4->  methodParameter== FunctionCallBridge4::class.java
                            else->throw RuntimeException("not support parameter size:$size for lambda bridge")
                        }
                    }

                    if (methodParameter == Any::class.java || node.value::class.java.isAssignableFrom(methodParameter)) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.FloatValueResult -> {
                    if (node.isDouble()) {
                        if (
                            methodParameter == Double::class.java
                        ) {
                            continue
                        }
                    } else {
                        if (
                            methodParameter == Float::class.java
                        ) {
                            continue
                        }
                    }
                    return false
                }

                is ParseResult.ValueResult.IntValueResult -> {
                    if (node.isLong()) {
                        if (
                            methodParameter == Long::class.java
                        ) {
                            continue
                        }
                    } else {
                        if (
                            methodParameter == Int::class.java ||
                            methodParameter == Long::class.java
                        ) {
                            continue
                        }
                    }
                    return false
                }

                is ParseResult.ValueResult.StringValueResult -> {
                    if (
                        methodParameter == String::class.java
                    ) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.BooleanValueResult -> {
                    if (methodParameter == Boolean::class.java) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.ArrayValueResult -> {
                    if (methodParameter == Array::class.java || methodParameter == List::class.java) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.RangeValueResult -> {
                    if (methodParameter == LongRange::class.java) {
                        continue
                    }
                    return false
                }
            }
        }

        return true
    }
}