package com.cool.jerry.rt_engine.utils

import java.lang.reflect.Method

object RuleUtils {

    private fun findMethodType(refType: String, methodName: String): Method? {
        return Class.forName(refType).declaredMethods.find {
            it.name == methodName
        }
    }

    fun String.isRefName(): Boolean {
        return this.startsWith("@")
    }

    fun String.getRefName(): String? {
        return if (isRefName()) {
            this.substring(1)
        } else {
            null
        }
    }


}