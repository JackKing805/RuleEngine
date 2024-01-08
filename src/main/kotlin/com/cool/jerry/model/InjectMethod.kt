package com.cool.jerry.model

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
            val mp = method.parameters[index].type
            if (mp == Any::class.java){
                continue
            }
            when (node) {
                is ParseResult.ValueResult.AnyValueResult -> {
                    if (mp == Any::class.java || node.value::class.java.isAssignableFrom(mp)) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.FloatValueResult -> {
                    if (node.isDouble()) {
                        if (
                            mp == Double::class.java
                        ) {
                            continue
                        }
                    } else {
                        if (
                            mp == Float::class.java
                        ) {
                            continue
                        }
                    }
                    return false
                }

                is ParseResult.ValueResult.IntValueResult -> {
                    if (node.isLong()) {
                        if (
                            mp == Long::class.java
                        ) {
                            continue
                        }
                    } else {
                        if (
                            mp == Int::class.java ||
                            mp == Long::class.java
                        ) {
                            continue
                        }
                    }
                    return false
                }

                is ParseResult.ValueResult.StringValueResult -> {
                    if (
                        mp == String::class.java
                    ) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.BooleanValueResult -> {
                    if (mp == Boolean::class.java) {
                        continue
                    }
                    return false
                }

                is ParseResult.ValueResult.ArrayValueResult -> {
                    if (mp == Array::class.java || mp == List::class.java) {
                        continue
                    }
                    return false
                }
            }
        }

        return true
    }
}