package com.cool.jerry.model

import com.cool.jerry.v3.R3Node

sealed class ParseResult {
    sealed class ValueResult<T>(open var value: T) : ParseResult() {
        data class IntValueResult(override var value: Long) : ValueResult<Long>(value) {
            fun isLong() = this.value.toString().replace("-", "").length > Int.MAX_VALUE.toString().length
        }

        data class StringValueResult(override var value: String) : ValueResult<String>(value)
        data class FloatValueResult(override var value: Double) : ValueResult<Double>(value) {
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

        data class BooleanValueResult(override var value: Boolean) : ValueResult<Boolean>(value)
        data class AnyValueResult(override var value: Any) : ValueResult<Any>(value)
        data class ArrayValueResult(override var value: Array<ValueResult<*>>) :
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
        data class RangeValueResult(override var value: LongRange):ValueResult<LongRange>(value)
        data class MapValueResult(override var value: MutableMap<ValueResult<*>,ValueResult<*>>):ValueResult<MutableMap<ValueResult<*>,ValueResult<*>>>(value)
    }

    data object NoneResult : ParseResult()

    sealed class OperateResult : ParseResult() {
        data object Break : OperateResult()
        data object Continue : OperateResult()
        data class Return(val value: ValueResult<*>?) : OperateResult()
    }

    //新定义的变量
    sealed class Define : ParseResult() {
        data class ClassDefine(
            val classStatement: R3Node.Statement.ClassStatement,
            val variableExpression: List<R3Node.Expression.VariableExpression>,
            val functionDefine: List<FunctionDefine>,
            val constructorDefine: List<ConstructorDefine>
        ) :
            Define()

        data class FunctionDefine(val functionStatement: R3Node.Statement.FunctionStatement) : Define()
        data class ConstructorDefine(val functionStatement: R3Node.Statement.ConstructorFunctionStatement) : Define()

        //只有使用def才能用这个参数返回
        data class Variable(val name: String, var value: ValueResult<*>, val isConst: Boolean, val defineScope:DefineScope) : Define(){
            enum class DefineScope{
                GLOBAL,
                CLASS,
                FUN,
                INNER
            }
        }
    }
}

internal fun ParseResult.isLambadaExpression(): Boolean {
    return when (this) {
        is ParseResult.Define.ClassDefine -> false
        is ParseResult.Define.ConstructorDefine -> false
        is ParseResult.Define.FunctionDefine -> {
            this.functionStatement.functionName.text.startsWith("LambdaExpression_")
        }

        is ParseResult.Define.Variable -> this.value.isLambadaExpression()
        ParseResult.NoneResult -> false
        ParseResult.OperateResult.Break -> false
        ParseResult.OperateResult.Continue -> false
        is ParseResult.OperateResult.Return -> false
        is ParseResult.ValueResult.AnyValueResult -> if (this.value is ParseResult.Define.FunctionDefine) {
            (this.value as ParseResult.Define.FunctionDefine).isLambadaExpression()
        } else {
            false
        }

        is ParseResult.ValueResult.ArrayValueResult -> false
        is ParseResult.ValueResult.BooleanValueResult -> false
        is ParseResult.ValueResult.FloatValueResult -> false
        is ParseResult.ValueResult.IntValueResult -> false
        is ParseResult.ValueResult.StringValueResult -> false
        is ParseResult.ValueResult.RangeValueResult -> false
        is ParseResult.ValueResult.MapValueResult -> false
    }
}

internal fun ParseResult.toIntValueResultElseThrow(): ParseResult.ValueResult.IntValueResult {
    return when (this) {
        is ParseResult.ValueResult.IntValueResult -> this
        else -> throw RuntimeException("$this not a IntValueResult")
    }
}

internal fun ParseResult.toValueResultElseThrow(exceptionInfo: String? = null): ParseResult.ValueResult<*> {
    return when (this) {
        ParseResult.OperateResult.Break -> throw RuntimeException(exceptionInfo ?: "$this not a valueResult")
        ParseResult.OperateResult.Continue -> throw RuntimeException(exceptionInfo ?: "$this not a valueResult")
        is ParseResult.Define -> {
            when (this) {
                is ParseResult.Define.ClassDefine,
                is ParseResult.Define.ConstructorDefine,
                is ParseResult.Define.FunctionDefine -> this.toValueResult()

                is ParseResult.Define.Variable -> this.value
            }
        }

        is ParseResult.NoneResult -> throw RuntimeException(exceptionInfo ?: "$this not a valueResult")
        is ParseResult.ValueResult.AnyValueResult -> this
        is ParseResult.ValueResult.FloatValueResult -> this
        is ParseResult.ValueResult.IntValueResult -> this
        is ParseResult.ValueResult.StringValueResult -> this
        is ParseResult.ValueResult.BooleanValueResult -> this
        is ParseResult.ValueResult.ArrayValueResult -> this
        is ParseResult.OperateResult.Return -> this.value ?: throw RuntimeException(
            exceptionInfo ?: "$this not a valueResult"
        )

        is ParseResult.ValueResult.RangeValueResult -> this
        is ParseResult.ValueResult.MapValueResult -> this
    }
}

internal fun Any?.toValueResultElseNone(): ParseResult {
    return if (this is Unit || this == null) {
        ParseResult.NoneResult
    } else {
        this.toValueResult()
    }
}

internal fun Any.toValueResult(): ParseResult.ValueResult<*> {
    return when (this) {
        is ParseResult.ValueResult<*> -> {
            this
        }

        is ParseResult.Define -> {
            if (this is ParseResult.Define.Variable) {
                return this.value
            } else if (this is ParseResult.Define.FunctionDefine) {
                return ParseResult.ValueResult.AnyValueResult(this)
            }
            throw RuntimeException("$this not a valueResult")
        }

        is ParseResult.OperateResult -> {
            when (this) {
                ParseResult.OperateResult.Break,
                ParseResult.OperateResult.Continue -> throw RuntimeException("operate can't convert to valueResult")

                is ParseResult.OperateResult.Return -> this.value ?: throw RuntimeException("$this not a valueResult")
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

//internal fun ParseResult.toParam():Param{
//    return when(this){
//        is ParseResult.Define.ClassDefine -> Param(this.)
//        is ParseResult.Define.ConstructorDefine -> TODO()
//        is ParseResult.Define.FunctionDefine -> TODO()
//        is ParseResult.Define.Variable -> TODO()
//        ParseResult.NoneResult -> TODO()
//        ParseResult.OperateResult.Break -> TODO()
//        ParseResult.OperateResult.Continue -> TODO()
//        is ParseResult.OperateResult.Return -> TODO()
//        is ParseResult.ValueResult.AnyValueResult -> TODO()
//        is ParseResult.ValueResult.ArrayValueResult -> TODO()
//        is ParseResult.ValueResult.BooleanValueResult -> TODO()
//        is ParseResult.ValueResult.FloatValueResult -> TODO()
//        is ParseResult.ValueResult.IntValueResult -> TODO()
//        is ParseResult.ValueResult.MapValueResult -> TODO()
//        is ParseResult.ValueResult.RangeValueResult -> TODO()
//        is ParseResult.ValueResult.StringValueResult -> TODO()
//    }
//}