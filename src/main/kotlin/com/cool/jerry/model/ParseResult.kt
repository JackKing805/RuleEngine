package com.cool.jerry.model

import com.cool.jerry.v3.R3Node

sealed class ParseResult {
    sealed class ValueResult<T>(open val value: T) : ParseResult() {
        data class IntValueResult(override val value: Long) : ValueResult<Long>(value) {
            fun isLong() = this.value.toString().replace("-", "").length > Int.MAX_VALUE.toString().length
        }

        data class StringValueResult(override val value: String) : ValueResult<String>(value)
        data class FloatValueResult(override val value: Double) : ValueResult<Double>(value) {
            fun isDouble(): Boolean {
                val toString = this.value.toString().replace("-", "")
                if (toString.contains(".")) {
                    val maxSS = 7
                    val suf = toString.substringAfter(".")
                    if (suf.length > maxSS) {
                        return true
                    }
                }
                return false
            }
        }

        data class BooleanValueResult(override val value: Boolean) : ValueResult<Boolean>(value)
        data class AnyValueResult(override val value: Any) : ValueResult<Any>(value)
        data class ArrayValueResult(override val value: Array<ValueResult<*>>) :
            ValueResult<Array<ValueResult<*>>>(value) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ArrayValueResult

                return value.contentEquals(other.value)
            }

            override fun hashCode(): Int {
                return value.contentHashCode()
            }
        }
    }

    data object NoneResult : ParseResult()

    //新定义的变量

    sealed class OperateResult: ParseResult() {
        data object Break:OperateResult()
        data object Continue:OperateResult()
        data class Return(val value:ValueResult<*>?):OperateResult()
    }

    sealed class Define: ParseResult() {
        data class ClassDefine(val classStatement: R3Node.Statement.ClassStatement,val variableExpression: List<R3Node.Expression.VariableExpression>,val functionDefine: List<FunctionDefine>,val constructorDefine: List<ConstructorDefine>):
            Define()
        data class FunctionDefine(val functionStatement: R3Node.Statement.FunctionStatement): Define()
        data class ConstructorDefine(val functionStatement: R3Node.Statement.ConstructorFunctionStatement): Define()

        //只有使用def才能用这个参数返回
        data class Variable(val name: String, val value: ValueResult<*>,val isConst:Boolean) : Define()
    }
}

fun ParseResult.toIntValueResultElseThrow():ParseResult.ValueResult.IntValueResult{
    return when (this) {
        is ParseResult.ValueResult.IntValueResult -> this
        else ->  throw RuntimeException("$this not a IntValueResult")
    }
}

fun ParseResult.toValueResultElseThrow(exceptionInfo:String?=null): ParseResult.ValueResult<*> {
    return when (this) {
        ParseResult.OperateResult.Break -> throw RuntimeException(exceptionInfo?:"$this not a valueResult")
        ParseResult.OperateResult.Continue -> throw RuntimeException(exceptionInfo?:"$this not a valueResult")
        is ParseResult.Define -> {
            when(this){
                is ParseResult.Define.ClassDefine,
                is ParseResult.Define.ConstructorDefine,
                is ParseResult.Define.FunctionDefine -> throw RuntimeException(exceptionInfo?:"$this not a valueResult")
                is ParseResult.Define.Variable -> this.value
            }
        }
        is ParseResult.NoneResult -> throw RuntimeException(exceptionInfo?:"$this not a valueResult")
        is ParseResult.ValueResult.AnyValueResult -> this
        is ParseResult.ValueResult.FloatValueResult -> this
        is ParseResult.ValueResult.IntValueResult -> this
        is ParseResult.ValueResult.StringValueResult -> this
        is ParseResult.ValueResult.BooleanValueResult -> this
        is ParseResult.ValueResult.ArrayValueResult -> this
        is ParseResult.OperateResult.Return -> this.value?:throw RuntimeException(exceptionInfo?:"$this not a valueResult")
    }
}

fun Any?.toValueResultElseNone(): ParseResult {
    return if (this is Unit || this == null) {
        ParseResult.NoneResult
    } else {
        this.toValueResult()
    }
}

fun Any.toValueResult(): ParseResult.ValueResult<*> {
    return when (this) {
        is ParseResult.ValueResult<*> -> {
            this
        }

        is ParseResult.Define->{
            if (this is ParseResult.Define.Variable){
                return this.value
            }
            throw RuntimeException("$this not a valueResult")
        }

        is ParseResult.OperateResult->{
            when(this){
                ParseResult.OperateResult.Break,
                ParseResult.OperateResult.Continue -> throw RuntimeException("operate can't convert to valueResult")
                is ParseResult.OperateResult.Return -> this.value?:throw RuntimeException("$this not a valueResult")
            }
        }

        is Int -> {
            ParseResult.ValueResult.IntValueResult(this.toLong())
        }

        is Long -> {
            ParseResult.ValueResult.IntValueResult(this)
        }

        is String -> {
            ParseResult.ValueResult.StringValueResult(this)
        }

        is Float -> {
            ParseResult.ValueResult.FloatValueResult(this.toDouble())
        }

        is Double -> {
            ParseResult.ValueResult.FloatValueResult(this)
        }

        is Boolean -> {
            ParseResult.ValueResult.BooleanValueResult(this)
        }

        is Array<*> -> {
            ParseResult.ValueResult.ArrayValueResult(this.map {
                it!!.toValueResult()
            }.toTypedArray())
        }

        is List<*> -> {
            ParseResult.ValueResult.ArrayValueResult(this.map {
                it!!.toValueResult()
            }.toTypedArray())
        }

        else -> {
            ParseResult.ValueResult.AnyValueResult(this)
        }
    }
}


fun <T> Any.toTargetTypeInstance(type: Class<T>): T {
    if (this is ParseResult.ValueResult<*>) {
        throw RuntimeException("please use ParseResult.ValueResult.value to convert to Other type")
    }

    if (this is R3Node) {
        throw RuntimeException("please don't use R3Node convert to Other type")
    }

    return when (type) {
        Long::class.java -> {
            try {
                this as Long
            } catch (e: ClassCastException) {
                this.toString().toLong()
            }
        }

        Int::class.java -> {
            try {
                this as Int
            } catch (e: ClassCastException) {
                this.toString().toInt()
            }
        }

        String::class.java -> {
            this as String
        }

        Boolean::class.java -> {
            this as Boolean
        }

        Double::class.java -> {
            try {
                this as Double
            } catch (e: ClassCastException) {
                this.toString().toDouble()
            }
        }

        Float::class.java -> {
            try {
                this as Float
            } catch (e: ClassCastException) {
                this.toString().toFloat()
            }
        }

        Short::class.java -> {
            try {
                this as Short
            } catch (e: ClassCastException) {
                this.toString().toShort()
            }
        }

        Byte::class.java -> {
            this as Byte
        }

        ByteArray::class.java -> {
            this as ByteArray
        }

        Array::class.java -> {
            this as Array<*>
        }

        List::class.java -> {
            this as List<*>
        }

        else -> this
    } as T
}