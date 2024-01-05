package com.cool.jerry.v3

import com.cool.jerry.exception.*
import com.cool.jerry.model.*
import java.util.UUID
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor
import kotlin.math.atan
import kotlin.text.StringBuilder

class R3Parser {
    //注入的环境变量
    private val environments = mutableMapOf<String, Any>()
    private val injectMethods = mutableListOf<InjectMethod>()

    fun reset() {
        environments.clear()
        injectMethods.clear()
    }

    fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    fun setEnvironment(map: Map<String, Any>) {
        environments.putAll(map)
    }

    private fun getEnvironment(key: String): Any {
        return environments[key] ?: throw RuntimeException("@${key} not define in environment")
    }

    fun setEnvironmentMethod(list: List<InjectMethod>) {
        injectMethods.addAll(list)
    }


    fun setEnvironmentMethod(method: InjectMethod) {
        injectMethods.add(method)
    }

    private fun getEnvironmentMethod(key: String, nodes: List<ParseResult.ValueResult<*>>): InjectMethod {
        return injectMethods.find {
            it.name == key && it.isSameMethod(nodes)
        } ?: throw NotDefineMethodException(key)
    }

    suspend fun parse(
        node: R3Node,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Expression -> {
                val result = parseExpression(node, scopeParams, classes, functions)
                if (result is ParseResult.Define.Variable) {
                    if (node.parent() == null) {
                        scopeParams.add(Param(result.name, result.value))
                    }
                }
                result
            }

            is R3Node.Program -> {
                parseProgram(node, scopeParams, classes, functions)
            }

            is R3Node.Statement -> {
                val result = parseStatement(node, scopeParams, classes, functions)
                when (result) {
                    is ParseResult.Define.ClassDefine -> {
                        if (node.parent() == null) {
                            classes.add(result)
                        }
                    }

                    is ParseResult.Define.FunctionDefine -> {
                        if (node.parent() == null) {
                            functions.add(result)
                        }
                    }

                    else -> {}
                }
                result
            }
        }
    }

    private suspend fun parseDefine(
        node: R3Node.Expression.Define,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Expression.Define.Identifier.ID -> scopeParams.find { it.paramName() == node.text }?.parseResult
                ?: throw RuntimeException("${node.text} not define before")

            is R3Node.Expression.Define.Identifier.ID_REF -> getEnvironment(node.text).toValueResult()
            is R3Node.Expression.Define.Params -> {
                throw RuntimeException("params should not be parse,it just a symbol")
            }
        }
    }

    private suspend fun parseBitOperate(
        node: R3Node.Expression.OperateExpression.BitOperateExpression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        val left = parse(node.left, scopeParams, classes, functions).toValueResultElseThrow()
        val right = parse(node.right, scopeParams, classes, functions).toValueResultElseThrow()
        return when (node) {
            is R3Node.Expression.OperateExpression.BitOperateExpression.BitAndOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value and right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.AnyValueResult && left.value is Byte && right is ParseResult.ValueResult.AnyValueResult && right.value is Byte) {
                    (left.value as Byte and right.value as Byte).toValueResult()
                } else {
                    throw RuntimeException("& operate only support int,long boolean")
                }
            }

            is R3Node.Expression.OperateExpression.BitOperateExpression.BitLeftOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    if (right.isLong()) {
                        throw RuntimeException("left move number must be int")
                    }
                    (left.value shl right.value.toTargetTypeInstance(Int::class.java)).toValueResult()
                } else {
                    throw RuntimeException("<< operate only support int,long")
                }
            }

            is R3Node.Expression.OperateExpression.BitOperateExpression.BitOrOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value or right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value or right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.AnyValueResult && left.value is Byte && right is ParseResult.ValueResult.AnyValueResult && right.value is Byte) {
                    (left.value as Byte or right.value as Byte).toValueResult()
                } else {
                    throw RuntimeException("or operate only support int,long,boolean")
                }
            }

            is R3Node.Expression.OperateExpression.BitOperateExpression.BitRightOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    if (right.isLong()) {
                        throw RuntimeException("right move number must be int")
                    }
                    (left.value shr right.value.toTargetTypeInstance(Int::class.java)).toValueResult()
                } else {
                    throw RuntimeException(">> operate only support int,long")
                }
            }

            is R3Node.Expression.OperateExpression.BitOperateExpression.BitXorOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value xor right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value xor right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.AnyValueResult && left.value is Byte && right is ParseResult.ValueResult.AnyValueResult && right.value is Byte) {
                    (left.value as Byte xor right.value as Byte).toValueResult()
                } else {
                    throw RuntimeException(">> operate only support int,long")
                }
            }
        }
    }

    private suspend fun parseCompareOperate(
        node: R3Node.Expression.OperateExpression.CompareOperateExpression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        val left = parse(node.left, scopeParams, classes, functions).toValueResultElseThrow()
        val right = parse(node.right, scopeParams, classes, functions).toValueResultElseThrow()
        return when (node) {
            is R3Node.Expression.OperateExpression.CompareOperateExpression.AndCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value != 0L && right.value != 0L).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value && right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value != 0.0 && right.value != 0.0).toValueResult()
                } else {
                    throw RuntimeException("&& operate only support int,boolean,(int != 0 is true)")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.EqualCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value == right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value == right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    (left.value == right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.AnyValueResult && right is ParseResult.ValueResult.AnyValueResult) {
                    (left.value == right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.ArrayValueResult && right is ParseResult.ValueResult.ArrayValueResult) {
                    (left.value.contentEquals(right.value)).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value == right.value).toValueResult()
                } else {
                    throw RuntimeException("== left and right must be same ${left.value!!.javaClass} type")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.LessCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value < right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value < right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    (left.value < right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value < right.value).toValueResult()
                } else {
                    throw RuntimeException("< left and right must be same ${left.value!!.javaClass} type,only int float boolean and string type support")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.LessEqualCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value <= right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value <= right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    (left.value <= right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value <= right.value).toValueResult()
                } else {
                    throw RuntimeException("<= left and right must be same ${left.value!!.javaClass} type,only int float boolean and string type support")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.MoreCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value > right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value > right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    (left.value > right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value > right.value).toValueResult()
                } else {
                    throw RuntimeException("> left and right must be same ${left.value!!.javaClass} type,only int float boolean and string type support")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.MoreEqualCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value >= right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value >= right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    (left.value >= right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value >= right.value).toValueResult()
                } else {
                    throw RuntimeException(">= left and right must be same ${left.value!!.javaClass} type,only int float boolean and string type support")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.NotEqualCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value != right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value != right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    (left.value != right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.AnyValueResult && right is ParseResult.ValueResult.AnyValueResult) {
                    (left.value != right.value).toValueResult()
                } else if (left is ParseResult.ValueResult.ArrayValueResult && right is ParseResult.ValueResult.ArrayValueResult) {
                    (!left.value.contentEquals(right.value)).toValueResult()
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    (left.value != right.value).toValueResult()
                } else {
                    throw RuntimeException("!= left and right must be same ${left.value!!.javaClass} type")
                }
            }

            is R3Node.Expression.OperateExpression.CompareOperateExpression.OrCompareOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    (left.value != 0L || right.value != 0L).toValueResult()
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    (left.value || right.value).toValueResult()
                } else {
                    throw RuntimeException("|| left and right must be same ${left.value!!.javaClass} type,(int != 0 is true),only support boolean and int")
                }
            }
        }
    }

    private suspend fun parseMathAssignOperate(
        node: R3Node.Expression.OperateExpression.MathAssignOperateExpression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        if (node.left !is R3Node.Expression.Define.Identifier) {
            throw RuntimeException("math assign operate left must be variable")
        }

        val left = parse(node.left, scopeParams, classes, functions).toValueResultElseThrow()
        val right = parse(node.right, scopeParams, classes, functions).toValueResultElseThrow()

        when (node) {
            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.AddAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value + right.value
                } else if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    right.value + left.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    right.value + left.value
                } else {
                    throw RuntimeException("+= left and right must be same ${left.value!!.javaClass} type,only support int float and string")
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.AndAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value and right.value
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    left.value and right.value
                } else {
                    throw RuntimeException("&= left and right must be same ${left.value!!.javaClass} type,only support int and boolean")
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.DivAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value / right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value / right.value
                } else if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value / right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value / right.value
                } else {
                    throw RuntimeException("/= left and right must be same ${left.value!!.javaClass} type,only support int and float")
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.ModAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value % right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value % right.value
                } else if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value % right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value % right.value
                } else {
                    throw RuntimeException("%= left and right must be same ${left.value!!.javaClass} type,only support int and float")
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.MulAssignOperateExpression -> {
                when (left) {
                    is ParseResult.ValueResult.AnyValueResult,
                    is ParseResult.ValueResult.ArrayValueResult,
                    is ParseResult.ValueResult.BooleanValueResult -> throw RuntimeException("*= left and right must be same ${left.value!!.javaClass} type,only support int and float ,string")

                    is ParseResult.ValueResult.FloatValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult,
                            is ParseResult.ValueResult.ArrayValueResult,
                            is ParseResult.ValueResult.BooleanValueResult -> throw RuntimeException("*= left and right must be same ${left.value!!.javaClass} type,only support int and float ,string")

                            is ParseResult.ValueResult.FloatValueResult -> {
                                left.value * right.value
                            }

                            is ParseResult.ValueResult.IntValueResult -> {
                                left.value * right.value
                            }

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "float * String",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult,
                            is ParseResult.ValueResult.ArrayValueResult,
                            is ParseResult.ValueResult.BooleanValueResult -> throw RuntimeException("*= left and right must be same ${left.value!!.javaClass} type,only support int and float ,string")

                            is ParseResult.ValueResult.FloatValueResult -> {
                                left.value * right.value
                            }

                            is ParseResult.ValueResult.IntValueResult -> {
                                left.value * right.value
                            }

                            is ParseResult.ValueResult.StringValueResult -> {
                                val str = StringBuilder("")
                                for (i in 0..<left.value) {
                                    str.append(right.value)
                                }
                                str.toString()
                            }
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult,
                            is ParseResult.ValueResult.ArrayValueResult,
                            is ParseResult.ValueResult.BooleanValueResult -> throw RuntimeException("*= left and right must be same ${left.value!!.javaClass} type,only support int and float ,string")

                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "String * float",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> {
                                val str = StringBuilder("")
                                for (i in 0..<right.value) {
                                    str.append(left.value)
                                }
                                str.toString()
                            }

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "String * String",
                                node.source
                            )
                        }
                    }
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.OrAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value or right.value
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    left.value or right.value
                } else {
                    throw RuntimeException("|= left and right must be same ${left.value!!.javaClass} type,only support int and boolean")
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.SubAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value - right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value - right.value
                } else if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value - right.value
                } else if (left is ParseResult.ValueResult.FloatValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value - right.value
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.StringValueResult) {
                    left.value.replace(right.value, "")
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value.replace(right.value.toString(), "")
                } else if (left is ParseResult.ValueResult.StringValueResult && right is ParseResult.ValueResult.FloatValueResult) {
                    left.value.replace(right.value.toString(), "")
                } else {
                    throw RuntimeException("+= left and right must be same ${left.value!!.javaClass} type,only support int float and string")
                }
            }

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression.XorAssignOperateExpression -> {
                if (left is ParseResult.ValueResult.IntValueResult && right is ParseResult.ValueResult.IntValueResult) {
                    left.value xor right.value
                } else if (left is ParseResult.ValueResult.BooleanValueResult && right is ParseResult.ValueResult.BooleanValueResult) {
                    left.value xor right.value
                } else {
                    throw RuntimeException("|= left and right must be same ${left.value!!.javaClass} type,only support int and boolean")
                }
            }
        }.let {
            reAssignLeftValue((node.left as R3Node.Expression.Define.Identifier), it, scopeParams)
        }
        return ParseResult.NoneResult
    }

    private suspend fun parseMathOperate(
        node: R3Node.Expression.OperateExpression.MathOperateExpression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        val left = parse(node.left, scopeParams, classes, functions).toValueResultElseThrow()
        val right = parse(node.right, scopeParams, classes, functions).toValueResultElseThrow()
        return when (node) {
            is R3Node.Expression.OperateExpression.MathOperateExpression.AddOperateExpression -> {
                when (left) {
                    is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                    is ParseResult.ValueResult.FloatValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Float + Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Float + Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value + right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value + right.value
                            is ParseResult.ValueResult.StringValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Float + Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Int + Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Int + Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value + right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value + right.value
                            is ParseResult.ValueResult.StringValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Int + Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.FloatValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.IntValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.StringValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "String + Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "String + Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.BooleanValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> left.value && right.value
                            is ParseResult.ValueResult.FloatValueResult,
                            is ParseResult.ValueResult.IntValueResult,
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Boolean + Any",
                                node.source
                            )

                            is ParseResult.ValueResult.StringValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Boolean + Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array +", node.source)
                }

            }

            is R3Node.Expression.OperateExpression.MathOperateExpression.DivOperateExpression -> {
                when (left) {
                    is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                    is ParseResult.ValueResult.FloatValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Float / Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value / right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value / right.value
                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Float / String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Float / Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Float / Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                            is ParseResult.ValueResult.FloatValueResult -> left.value / right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value / right.value
                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "String / String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Int / Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Int / Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "String / Float",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport(
                                "String / Int",
                                node.source
                            )

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "String / String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "String / Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "String / Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.BooleanValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Boolean / Any",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Boolean / Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "Boolean / Float",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport(
                                "Boolean / Int",
                                node.source
                            )

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Boolean / String",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Boolean / Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array /", node.source)
                }
            }

            is R3Node.Expression.OperateExpression.MathOperateExpression.ModOperateExpression -> {
                when (left) {
                    is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                    is ParseResult.ValueResult.FloatValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Float % Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value % right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value % right.value
                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Float % String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Float % Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Float % Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                            is ParseResult.ValueResult.FloatValueResult -> left.value % right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value % right.value
                            is ParseResult.ValueResult.StringValueResult -> {
                                if (left.value <= 0) {
                                    throw OpNotSupport("o or -Int % String", node.source)
                                }

                                val string = StringBuilder()
                                for (l in (0..<left.value)) {
                                    string.append(right.value)
                                }
                                string.toString()
                            }

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Int % Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Int % Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "Float % String",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> {
                                throw OpNotSupport("String % Int", node.source)
                            }

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "String % String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "String % Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "String % Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.BooleanValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Boolean % Any",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Boolean % Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "Boolean % Float",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport(
                                "Boolean % Int",
                                node.source
                            )

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Boolean % String",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Boolean % Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> {
                        throw OpNotSupport("Array %", node.source)
                    }
                }
            }

            is R3Node.Expression.OperateExpression.MathOperateExpression.MulOperateExpression -> {
                when (left) {
                    is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                    is ParseResult.ValueResult.FloatValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Float * Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value * right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value * right.value
                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Float * String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Float * Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Float * Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                            is ParseResult.ValueResult.FloatValueResult -> left.value * right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value * right.value
                            is ParseResult.ValueResult.StringValueResult -> {
                                if (left.value <= 0) {
                                    throw OpNotSupport("o or -Int * String", node.source)
                                }

                                val string = StringBuilder()
                                for (l in (0..<left.value)) {
                                    string.append(right.value)
                                }
                                string.toString()
                            }

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Int * Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Int * Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "Float * String",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> {
                                if (right.value <= 0) {
                                    throw OpNotSupport("o or -Int * String", node.source)
                                }

                                val string = StringBuilder()
                                for (l in (0..<right.value)) {
                                    string.append(left.value)
                                }
                                string.toString()
                            }

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "String * String",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "String * Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "String * Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.BooleanValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Boolean * Any",
                                node.source
                            )

                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Boolean * Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "Boolean * Float",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport(
                                "Boolean * Int",
                                node.source
                            )

                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Boolean * String",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Boolean * Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> {
                        throw OpNotSupport("Array *", node.source)
                    }
                }
            }

            is R3Node.Expression.OperateExpression.MathOperateExpression.SubOperateExpression -> {
                when (left) {
                    is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                    is ParseResult.ValueResult.BooleanValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> left.value && right.value
                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Boolean - Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                "Boolean - Float",
                                node.source
                            )

                            is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport(
                                "Boolean - Int",
                                node.source
                            )

                            is ParseResult.ValueResult.StringValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Boolean - Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.FloatValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Float - Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Float - Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value - right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value - right.value
                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Float - String",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Float - Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "Int - Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "Int - Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> left.value - right.value
                            is ParseResult.ValueResult.IntValueResult -> left.value - right.value
                            is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport(
                                "Int - String",
                                node.source
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "Int - Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                "String - Boolean",
                                node.source
                            )

                            is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                "String - Any",
                                node.source
                            )

                            is ParseResult.ValueResult.FloatValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.IntValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.StringValueResult -> left.value.replace(
                                right.value,
                                ""
                            )

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "String - Array",
                                node.source
                            )
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array -", node.source)
                }
            }
        }.let { result ->
            when (result) {
                is Int,
                is Long -> {
                    ParseResult.ValueResult.IntValueResult(result as Long)
                }

                is String -> {
                    ParseResult.ValueResult.StringValueResult(result)
                }

                is Float,
                is Double -> {
                    ParseResult.ValueResult.FloatValueResult(result as Double)
                }

                else -> {
                    throw IllStmtException("error Math operation:${node.source}")
                }
            }
        }
    }

    private suspend fun parseNumberAutoOperate(
        node: R3Node.Expression.OperateExpression.NumberAutoOperateExpression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        val whoValue = parse(node.who, scopeParams, classes, functions).toValueResultElseThrow()
        if (whoValue !is ParseResult.ValueResult.IntValueResult) {
            throw RuntimeException("auto Operate only support int define")
        }
        when (node) {
            is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoDecreaseOperateExpression -> {
                reAssignLeftValue(node.who, whoValue.value - 1, scopeParams)
            }

            is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoIncreaseOperateExpression -> {
                reAssignLeftValue(node.who, whoValue.value + 1, scopeParams)
            }
        }
        return ParseResult.NoneResult
    }

    private suspend fun parseOperate(
        node: R3Node.Expression.OperateExpression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Expression.OperateExpression.BitOperateExpression -> parseBitOperate(
                node,
                scopeParams,
                classes,
                functions
            )

            is R3Node.Expression.OperateExpression.CompareOperateExpression -> parseCompareOperate(
                node,
                scopeParams, classes,
                functions
            )

            is R3Node.Expression.OperateExpression.MathAssignOperateExpression -> parseMathAssignOperate(
                node,
                scopeParams, classes, functions
            )

            is R3Node.Expression.OperateExpression.MathOperateExpression -> parseMathOperate(
                node,
                scopeParams, classes,
                functions
            )

            is R3Node.Expression.OperateExpression.NumberAutoOperateExpression -> parseNumberAutoOperate(
                node,
                scopeParams, classes, functions
            )
        }
    }

    private suspend fun parseType(
        node: R3Node.Expression.TypeExpression<*>,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Expression.TypeExpression.ArrayExpression -> {
                node.value.map {
                    parse(it, scopeParams, classes, functions)
                }.toValueResult()
            }

            is R3Node.Expression.TypeExpression.BooleanTypeExpression -> node.value.toValueResult()
            is R3Node.Expression.TypeExpression.FloatNumberTypeExpression -> node.value.toValueResult()
            is R3Node.Expression.TypeExpression.NumberTypeExpression -> node.value.toValueResult()
            is R3Node.Expression.TypeExpression.StringTypeExpression -> node.value.toValueResult()
        }
    }

    private suspend fun parseExpression(
        node: R3Node.Expression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Expression.Define -> parseDefine(node, scopeParams, classes, functions)
            is R3Node.Expression.ArrayAccessExpression -> {
                val array = parse(node.array, scopeParams, classes, functions)
                if (array !is ParseResult.ValueResult.ArrayValueResult) {
                    throw RuntimeException("${node.source} not a array define")
                }
                val index = parse(node.index, scopeParams, classes, functions) as ParseResult.ValueResult.IntValueResult
                array.value[index.value.toInt()]
            }

            is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
                val value = parse(node.right, scopeParams, classes, functions).toValueResultElseThrow()
                when (node.left) {
                    is R3Node.Expression.Define.Identifier -> {
                        reAssignLeftValue(node.left, value.value!!, scopeParams)
                    }

                    is R3Node.Expression.ObjectPropertiesExpression -> {
                        val objectHolder = parse(node.left.objectExpression, scopeParams, classes, functions)
                        if (objectHolder is ParseResult.ValueResult.AnyValueResult && objectHolder.value is ClassEnvironmentDefine){
                            reAssignLeftValue(node.left.propertiesName,value.value!!,(objectHolder.value as ClassEnvironmentDefine).scopeParams)
                        }else{
                            objectHolder::class.java.declaredFields.find {
                                it.name == node.left.propertiesName.text
                            }?.let {
                                it.isAccessible = true
                                it.set(objectHolder, value.value!!)
                            }
                        }
                    }

                    else -> {
                        throw RuntimeException("assign operate only support object.properties and id or id_ref")
                    }
                }
                ParseResult.NoneResult
            }

            is R3Node.Expression.BreakExpression -> {
                ParseResult.OperateResult.Break
            }

            is R3Node.Expression.ContinueExpression -> {
                ParseResult.OperateResult.Continue
            }

            is R3Node.Expression.IfExpression -> {
                val condition = parse(node.condition, scopeParams, classes, functions)
                if (condition !is ParseResult.ValueResult.BooleanValueResult) {
                    throw RuntimeException("if condition must be boolean value")
                }
                val expressionList = if (condition.value) {
                    node.thenBody
                } else {
                    node.elseBody
                }.toMutableList()
                val scopeParamsCopy = mutableListOf(*scopeParams.toTypedArray())

                return expressionList.execute(scopeParamsCopy, classes, functions, true, true, true)
//                for ((index, expression) in expressionList.withIndex()) {
//                    val expressionResult = parse(expression, scopeParamsCopy, classes, functions)
//                    val modifierResult = expressionResult.modifierScopeParams(scopeParamsCopy)
//                    if (modifierResult != null) {
//                        expressionList.modifierAfterIndexIdNameUntilNextSameOldName(
//                            index,
//                            expressionResult,
//                            modifierResult
//                        )
//                    }
//
//                    if (expressionResult is ParseResult.OperateResult) {
//                        when (expressionResult) {
//                            ParseResult.OperateResult.Break -> {
//                                break
//                            }
//
//                            ParseResult.OperateResult.Continue -> {
//                                continue
//                            }
//
//                            is ParseResult.OperateResult.Return -> {
//                                return expressionResult.value ?: ParseResult.NoneResult
//                            }
//                        }
//                    }
//                }
//                ParseResult.NoneResult
            }

            is R3Node.Expression.LoopExpression -> {
                val condition = parse(node.condition, scopeParams, classes, functions).toValueResultElseThrow()
                if (condition !is ParseResult.ValueResult.ArrayValueResult) {
                    throw RuntimeException("loop only support array:${node.source}")
                }
//                if (scopeParams.find { node.conditionProxy.text == it.paramName() } != null) {
//                    throw RuntimeException("${node.conditionProxy.text} is already define at before:${node.source}")
//                }

                val scopeParamsCopy = mutableListOf(*scopeParams.toTypedArray())
                var result: ParseResult = ParseResult.NoneResult
                for (valueResult in condition.value) {
                    val conditionProxy = Param(node.conditionProxy.text, valueResult)
                    conditionProxy.replaceScopeParams(scopeParamsCopy, true)
                    val loopBody = node.loopBody.toMutableList()
                    result = loopBody.execute(scopeParamsCopy, classes, functions, true, true, true)
                    if (result is ParseResult.OperateResult.Break) {
                        break
                    } else if (result is ParseResult.OperateResult.Continue) {
                        continue
                    } else if (result is ParseResult.OperateResult.Return) {
                        break
                    }
//                    for ((index, expression) in loopBody.withIndex()) {
//                        val expressionResult = parse(expression, scopeParamsCopy, classes, functions)
//                        val modifierResult = expressionResult.modifierScopeParams(scopeParamsCopy)
//                        if (modifierResult != null) {
//                            loopBody.modifierAfterIndexIdNameUntilNextSameOldName(
//                                index,
//                                expressionResult,
//                                modifierResult
//                            )
//                        }
//                        if (expressionResult is ParseResult.OperateResult) {
//                            when (expressionResult) {
//                                ParseResult.OperateResult.Break -> {
//                                    break
//                                }
//
//                                ParseResult.OperateResult.Continue -> {
//                                    continue
//                                }
//
//                                is ParseResult.OperateResult.Return -> {
//                                    return expressionResult.value ?: ParseResult.NoneResult
//                                }
//                            }
//                        }
//                    }
                }
                result
            }

            is R3Node.Expression.MethodCallExpression -> {
                val method = node.methodName.text

                val scopeParamsCopy = mutableListOf(*scopeParams.toTypedArray())

                //internal function
                val functionStatement = functions.find { it.functionStatement.functionName.text == method }
                val lambdaExpression = scopeParamsCopy.filter {
                    val re = it.parseResult
                    re is ParseResult.Define.Variable &&
                            re.value is ParseResult.ValueResult.AnyValueResult &&
                            re.value.value is ParseResult.Define.FunctionDefine &&
                            (re.value.value as ParseResult.Define.FunctionDefine).functionStatement.parameters.parameters.size == node.arguments.size
                }.find {
                    method == it.paramName()
                }?:scopeParamsCopy.filter {
                    val re = it.parseResult
                            re is ParseResult.ValueResult.AnyValueResult &&
                            re.value is ParseResult.Define.FunctionDefine &&
                                    (re.value as ParseResult.Define.FunctionDefine).functionStatement.parameters.parameters.size == node.arguments.size
                }.find {
                    method==it.paramName()
                }
                if (functionStatement != null) {
                    return functionStatement.functionStatement.execute(
                        node.arguments,
                        scopeParamsCopy,
                        classes,
                        functions,
                        true
                    )
                } else if (lambdaExpression!=null){
                    return (lambdaExpression.parseResult.toValueResult().value as ParseResult.Define.FunctionDefine).functionStatement.execute(
                        node.arguments,
                        scopeParamsCopy,
                        classes,
                        functions,
                        true
                    )
                }else {
                    //判断是否是调用了类的创建方法
                    val findClassCreate = classes.find {
                        val isClassInitConstructor = it.classStatement.className.text == method &&
                                it.classStatement.parameters.parameters.size == node.arguments.size

                        var isOtherClassConstructor = false

                        for (constructorDefine in it.constructorDefine) {
                            if (constructorDefine.functionStatement.functionStatement.parameters.parameters.size == node.arguments.size) {
                                isOtherClassConstructor = true
                                break
                            }
                        }

                        isClassInitConstructor || isOtherClassConstructor
                    }

                    if (findClassCreate != null) {
                        //创建类变量
                        findClassCreate.variableExpression.map {
                            parse(it, scopeParamsCopy, classes, functions)
                        }.forEach {
                            if (it is ParseResult.Define.Variable) {
                                Param(it.name, it).replaceScopeParams(scopeParamsCopy, true)
                            }
                        }

                        //调用类的创建
                        findClassCreate.constructorDefine.find {
                            it.functionStatement.functionStatement.parameters.parameters.size == findClassCreate.classStatement.parameters.parameters.size &&
                                    it.functionStatement.functionStatement.parameters.parameters.zip(findClassCreate.classStatement.parameters.parameters)
                                        .all { (element1, element2) -> element1.text == element2.text }
                        }?.let {
                            //调用匹配的构造方法
                            it.functionStatement.functionStatement.execute(
                                node.arguments,
                                scopeParamsCopy,
                                classes,
                                functions,
                                false
                            )
                        } ?: run {
                            //如果没有默认构造方法
//                            val argumentsConvert = arguments.mapIndexed { index, parseResult ->
//                                Param(findClassCreate.classStatement.parameters.parameters[index].text, parseResult.second)
//                            }.toMutableList()
//                            val argumentsConvert = arguments.mapIndexed { index, it ->
//                                val value = if (it.first is R3Node.Expression.Define.Identifier.ID){
//                                    scopeParams.find {a-> (it.first as R3Node.Expression.Define.Identifier.ID).text==a.paramName() }!!.parseResult
//                                }else{
//                                    it.second
//                                }
//                                Param(findClassCreate.classStatement.parameters.parameters[index].text, value)
//                            }.toMutableList()
                            node.arguments.convertArguments(
                                findClassCreate.classStatement.parameters.parameters.map { it.text },
                                scopeParamsCopy,
                                classes,
                                functions
                            ).replaceScopeParams(scopeParamsCopy, false)

                        }

                        //这里调用构造函数应该将该类创建并返回
                        ParseResult.ValueResult.AnyValueResult(
                            ClassEnvironmentDefine(
                                findClassCreate,
                                scopeParamsCopy,
                            )
                        )
//                            .let {
//                            ParseResult.Define.Variable(
//                                method,
//                                it.toValueResult(),
//                                false
//                            )
//                        }
                    } else {
                        //调用系统函数

                        val arguments = node.arguments.map {
                            parse(
                                it,
                                scopeParams,
                                classes,
                                functions
                            ).toValueResultElseThrow("$method params execute result must be a value:${it.source}")
                        }.mapIndexed { index, valueResult ->
                            node.arguments[index] to valueResult
                        }

//                        val argumentsConvert = node.arguments.convertArguments(
//
//                        )

                        val injectMethod = getEnvironmentMethod(method, arguments.map { it.second })
                        val parameters = injectMethod.method.parameters
                        val argumentParams = arguments.map {
                            it.second.value
                        }.mapIndexed { index, any ->
                            any?.toTargetTypeInstance(parameters[index].type)
                        }
                        injectMethod.invokeMethod(*argumentParams.toTypedArray()).toValueResultElseNone()
                    }
                }
            }

            is R3Node.Expression.ObjectMethodCallExpression -> {
                val objectDefine =
                    parse(node.objectExpression, scopeParams, classes, functions).toValueResultElseThrow()
                val methodDefine = node.methodName.text
                val scopeParamsCopy = mutableListOf(*scopeParams.toTypedArray())
                val arguments = node.arguments.map {
                    parse(it, scopeParamsCopy, classes, functions).toValueResult()
                }.mapIndexed { index, valueResult ->
                    node.arguments[index] to valueResult
                }
                val value = objectDefine.value!!

                if (value is ClassEnvironmentDefine) {
                    val functionStatement = value.classDefine.functionDefine.find {
                        it.functionStatement.functionName.text == methodDefine && it.functionStatement.parameters.parameters.size == arguments.size
                    } ?: throw RuntimeException("$objectDefine don't define $methodDefine function")
                    return functionStatement.functionStatement.execute(
                        node.arguments, value.scopeParams, mutableListOf(),
                        mutableListOf(),
                        true
                    )
                } else {
                    val method = value::class.java.declaredMethods.find {
                        it.name == methodDefine && it.parameterCount == arguments.size
                    } ?: throw RefNotDefineMethodException(value.toString(), methodDefine)
                    val parameters = method.parameters
                    val argumentsConverter = arguments.mapIndexed { index, it ->
                        it.toTargetTypeInstance(parameters[index].type)
                    }
                    return method.invoke(value, *argumentsConverter.toTypedArray()).toValueResultElseNone()
                }
            }

            is R3Node.Expression.ObjectPropertiesExpression -> {
                val objectDefine =
                    parse(node.objectExpression, scopeParams, classes, functions).toValueResultElseThrow()
                val value = objectDefine.value!!
                val propertiesName = node.propertiesName.text
                if (value is ClassEnvironmentDefine) {
                    value.scopeParams.find {
                        it.paramName() == propertiesName
                    }?.parseResult?:throw RuntimeException("${node.objectExpression.source} not define properties:$propertiesName")
                }else{
                    (
                            value::class.java.declaredFields.find {
                                it.name == propertiesName
                            }?.let {
                                it.isAccessible = true
                                it
                            }?.get(value) ?: throw RefNotDefinePropertiesException(
                                value::class.java.name,
                                propertiesName
                            )
                            ).toValueResult()
                }
            }

            is R3Node.Expression.OperateExpression -> parseOperate(node, scopeParams, classes, functions)
            is R3Node.Expression.RangeExpression -> {
                val start = parse(node.start, scopeParams, classes, functions).toValueResultElseThrow()
                    .toIntValueResultElseThrow()
                val end = parse(node.end, scopeParams, classes, functions).toValueResultElseThrow()
                    .toIntValueResultElseThrow()

                if (start.value > end.value) {
                    throw RuntimeException("error range define,range start more than end:${node.source}")
                }

                val list = mutableListOf<Long>()
                for (i in start.value..end.value) {
                    list.add(i)
                }
                ParseResult.ValueResult.ArrayValueResult(
                    list.map {
                        it.toValueResult()
                    }.toTypedArray()
                )
            }

            is R3Node.Expression.ReturnExpression -> {
                ParseResult.OperateResult.Return(
                    node.expression?.let {
                        parse(it, scopeParams, classes, functions).toValueResult()
                    }
                )
            }

            is R3Node.Expression.TypeExpression<*> -> parseType(node, scopeParams, classes, functions)
            is R3Node.Expression.VariableExpression -> {
                val parse = parse(node.variableValue, scopeParams, classes, functions).toValueResultElseThrow()
                ParseResult.Define.Variable(node.variableName.text, parse, node.isConst)
            }

            is R3Node.Expression.SignedExpression -> {
                val innerExpression = parse(
                    node.innerExpression,
                    scopeParams,
                    classes,
                    functions
                ).toValueResultElseThrow("signed Expr must be a Number:${node.source}")
                when (innerExpression) {
                    is ParseResult.ValueResult.IntValueResult -> (-innerExpression.value).toValueResult()
                    is ParseResult.ValueResult.FloatValueResult -> (-innerExpression.value).toValueResult()
                    is ParseResult.ValueResult.ArrayValueResult,
                    is ParseResult.ValueResult.BooleanValueResult,
                    is ParseResult.ValueResult.AnyValueResult,
                    is ParseResult.ValueResult.StringValueResult -> throw RuntimeException("signed Expr must be a Number:${node.source}")
                }
            }

            is R3Node.Expression.LambdaExpression -> {
//                val scopeParamsCopy = mutableListOf(*scopeParams.toTypedArray())
//
//                val arguments =  mutableListOf<Param>()
//                for (parameter in node.parameters.parameters) {
//                    val findParams = scopeParamsCopy.find { it.paramName()==parameter.text }?:throw NullPointerException("${parameter.text} not define before")
//                    if(findParams.parseResult !is ParseResult.ValueResult<*>){
//                        throw RuntimeException("${parameter.text} not a variable")
//                    }
//                    arguments.add(findParams)
//                }
//
//                arguments.replaceScopeParams(scopeParamsCopy,true)
//
//                node.functionBody.toMutableList().execute(
//                    scopeParamsCopy,
//                    classes,
//                    functions,
//                    true,
//                    true,
//                    true
//                )

                val functionName = "LambdaExpression_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + "_Define"
                ParseResult.Define.FunctionDefine(
                    R3Node.Statement.FunctionStatement(
                        node.source,
                        R3Node.Expression.Define.Identifier.ID(
                            functionName,
                            functionName
                        ).apply {
                                setParent(node.parent())
                        },
                        node.parameters,
                        node.functionBody
                    ).apply {
                        setParent(node.parent())
                    }
                )
            }
        }
    }

    private suspend fun parseProgram(
        node: R3Node.Program,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        var result: ParseResult = ParseResult.NoneResult
        for (statement in node.statements) {
            result = parse(statement, scopeParams, classes, functions)
        }
        return result
    }

    private suspend fun parseStatement(
        node: R3Node.Statement,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Statement.ClassStatement -> {
                val classBody = node.classBody
                val constructor = classBody.filterIsInstance<R3Node.Statement.ConstructorFunctionStatement>()
                    .map { parse(it, scopeParams, classes, functions) as ParseResult.Define.ConstructorDefine }
                val function = classBody.filterIsInstance<R3Node.Statement.FunctionStatement>().map {
                    parse(it, scopeParams, classes, functions) as ParseResult.Define.FunctionDefine
                }
                val variableExpression = classBody.filterIsInstance<R3Node.Expression.VariableExpression>()

                ParseResult.Define.ClassDefine(
                    node,
                    variableExpression,
                    function,
                    constructor
                )
            }

            is R3Node.Statement.FunctionStatement -> {
                val find = injectMethods.find { it.name == node.functionName.text && it.method.parameterCount == node.parameters.parameters.size }
                if (find!=null){
                    throw RuntimeException("${node.source} is override injectMethod,please modifier name or add sub parameters size")
                }
                ParseResult.Define.FunctionDefine(node)
            }

            is R3Node.Statement.ConstructorFunctionStatement -> {
                val parent = node.parent()
                if (parent == null || parent !is R3Node.Statement.ClassStatement) {
                    throw RuntimeException("${node.source} must define in class scope")
                }
                ParseResult.Define.ConstructorDefine(node)
            }
        }
    }


    private suspend fun List<R3Node.Expression>.convertArguments(
        autalParametersNames: List<String>,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): MutableList<Param> {
        val arguments = this.map {
            parse(
                it,
                scopeParams,
                classes,
                functions
            ).toValueResultElseThrow("${it.source} params execute result must be a value:${it.source}")
        }.mapIndexed { index, valueResult ->
            this[index] to valueResult
        }
        val argumentsConvert = arguments.mapIndexed { index, it ->
            val value = if (it.first is R3Node.Expression.Define.Identifier.ID) {
                scopeParams.find { a -> (it.first as R3Node.Expression.Define.Identifier.ID).text == a.paramName() }!!.parseResult
            } else {
                it.second
            }
            Param(autalParametersNames[index], value)
        }.toMutableList()
        return argumentsConvert
    }


    /**
     * @param arguments 传入的实际参数值
     * @param scopeParams 当前上下文的环境变量
     */
    private suspend fun R3Node.Statement.FunctionStatement.execute(
        arguments: List<R3Node.Expression>,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>,
        REPLACE: Boolean
    ): ParseResult {
        if (parameters.parameters.size != arguments.size) {
            throw RuntimeException("params size not equals")
        }
//        val argumentsConvert = arguments.mapIndexed { index, parseResult ->
//            Param(parameters.parameters[index].text, parseResult)
//        }.toMutableList()

//        val argumentsConvert = arguments.mapIndexed { index, it ->
//                val name = if (it.first is R3Node.Expression.Define.Identifier.ID){
//                    (it.first as R3Node.Expression.Define.Identifier.ID).text
//                }else{
//                    parameters.parameters[index].text
//                }
//            Param(name, it.second)
//        }.toMutableList()

//        asdas
        arguments.convertArguments(parameters.parameters.map { it.text }, scopeParams, classes, functions)
            .replaceScopeParams(scopeParams, REPLACE)

//        argumentsConvert.replaceScopeParams(scopeParams, REPLACE)

        val functionBody = functionBody.toMutableList()
//        for ((index, statement) in functionBody.withIndex()) {
//            val statementResult = parse(statement, scopeParams, classes, functions)
//            if (statementResult is ParseResult.Define.Variable) {
//                if (argumentsConvert.find { it.paramName() == statementResult.name } != null) {
//                    throw RuntimeException("${statementResult.name} is already define before at params")
//                }
//
//                if (scopeParams.find { it.paramName() == statementResult.name } != null) {
//                    throw RuntimeException("${statementResult.name} is already define before parent scope")
//                }
//            }
//
//            val modifierResult = statementResult.modifierScopeParams(scopeParams)
//            if (modifierResult != null) {
//                functionBody.modifierAfterIndexIdNameUntilNextSameOldName(
//                    index,
//                    statementResult,
//                    modifierResult
//                )
//            }
//            if (statementResult is ParseResult.OperateResult) {
//                when (statementResult) {
//                    ParseResult.OperateResult.Break -> {
//                        break
//                    }
//
//                    ParseResult.OperateResult.Continue -> {
//                        throw RuntimeException("continue can't use at function scope")
//                    }
//
//                    is ParseResult.OperateResult.Return -> {
//                        return statementResult.value ?: ParseResult.NoneResult
//                    }
//                }
//            }
//        }
//        return ParseResult.NoneResult
//
        return functionBody.execute(scopeParams, classes, functions, true, false, true)
    }

    private suspend fun MutableList<R3Node.Expression>.execute(
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>,
        needBreak: Boolean = false,
        needContinue: Boolean = false,
        needReturn: Boolean = false
    ): ParseResult {
        var result: ParseResult = ParseResult.NoneResult
        for ((index, expression) in this.withIndex()) {
            val expressionResult = parse(expression, scopeParams, classes, functions)

            if (expressionResult is ParseResult.Define.Variable) {
                if (scopeParams.find { it.paramName() == expressionResult.name } != null) {
                    throw RuntimeException("${expressionResult.name} is already define before parent scope")
                }
            }

            val modifierResult = expressionResult.modifierScopeParams(scopeParams)
            if (modifierResult != null) {
                this.modifierAfterIndexIdNameUntilNextSameOldName(
                    index,
                    expressionResult,
                    modifierResult
                )
            }
            if (expressionResult is ParseResult.OperateResult) {
                when (expressionResult) {
                    ParseResult.OperateResult.Break -> {
                        if (needBreak) {
                            result = ParseResult.OperateResult.Break
                            break
                        } else {
                            throw RuntimeException("break can't use at hare:${expression.source}")
                        }
                    }

                    ParseResult.OperateResult.Continue -> {
                        if (needContinue) {
                            result = ParseResult.OperateResult.Continue
                            break
                        } else {
                            throw RuntimeException("continue can't use at hare:${expression.source}")
                        }
                    }

                    is ParseResult.OperateResult.Return -> {
                        if (needReturn) {
                            result = expressionResult.value ?: ParseResult.NoneResult
                            break
                        } else {
                            throw RuntimeException("return can't use at hare:${expression.source}")
                        }
                    }
                }
            }
        }
        return result
    }

    private fun MutableList<R3Node.Expression>.modifierAfterIndexIdNameUntilNextSameOldName(
        startIndex: Int,
        oldName: ParseResult,
        newName: String
    ) {
        if (oldName !is ParseResult.Define.Variable) {
            return
        }
        val oname = oldName.name
        if (startIndex == size - 1) {
            return
        }
        for (i in startIndex..<size) {
            val item = get(i)
            if (i != startIndex) {
                if (item is R3Node.Expression.VariableExpression) {
                    if (item.variableName.text == oname) {
                        break
                    }
                }
            }

            if (item.haveIdEquals(oname)) {
                set(i, item.modifierOldIdToNewIdName2(oname, newName) as R3Node.Expression)
            }
        }
    }


    /**
     * 如果有相同就替换，没有就添加
     */
    private fun MutableList<Param>.replaceScopeParams(scopeParams: MutableList<Param>, REPLACE: Boolean) {
        for (param in this) {
            param.replaceScopeParams(scopeParams, REPLACE)
        }
    }

    /**
     * 如果有相同就替换，没有就添加
     *
     * @param REPLACE true:表示复制这个值，但HASH地址不同，否则，不改变值，只改变值的内容，hash地址一样
     */
    private fun Param.replaceScopeParams(scopeParams: MutableList<Param>, REPLACE: Boolean) {
        scopeParams.find { it.paramName() == this.paramName() }?.let { p ->
            scopeParams.indexOf(p).takeIf { index -> index != -1 }?.let { index ->
                if (REPLACE) {
                    scopeParams[index] = this
                } else {
                    val v = scopeParams[index]
                    v.setNewName(this.getName())
                    v.setNewModifierName(this.getModifierName())
                    v.parseResult = this.parseResult
                    scopeParams[index] = v
                }
            } ?: scopeParams.add(this)
        } ?: scopeParams.add(this)
    }

    /**
     * 如果修改成功则返回修改之后的变量名
     */
    private fun ParseResult.modifierScopeParams(scopeParams: MutableList<Param>): String? {
        return if (this is ParseResult.Define.Variable) {
            var name = this.name
            scopeParams.find { s -> s.paramName() == this.name }?.let { p ->
//                scopeParams.indexOf(p).takeIf { index -> index != -1 }?.let { index ->
//                    scopeParams[index] = scopeParams[index].copy(parseResult = this)
//                } ?: scopeParams.add(Param(this.name, this))
                name = UUID.randomUUID().toString() + "_" + name
                scopeParams.add(Param(this.name, this.copy(name), name))
            } ?: scopeParams.add(Param(this.name, this))
            name
        } else {
            null
        }
    }

    /**
     * 重新对环境中的变量进行赋值
     */
    private fun reAssignLeftValue(
        id: R3Node.Expression.Define.Identifier,
        value: Any,
        scopeParams: MutableList<Param>
    ) {
        when (id) {
            is R3Node.Expression.Define.Identifier.ID -> {
                scopeParams.find { id.text == it.paramName() }?.let { p ->
                    if (p.parseResult is ParseResult.Define.Variable && (p.parseResult as ParseResult.Define.Variable).isConst){
                        throw RuntimeException("${p.paramName()} is const, can't modifier")
                    }

                    scopeParams.indexOf(p).takeIf { index -> index != -1 }?.let { index ->
                        if (!isSameClass(value::class.java,p.parseResult.toValueResultElseThrow().value!!::class.java)) {
//                        if (!value.javaClass.isAssignableFrom(p.parseResult.toValueResultElseThrow().value!!.javaClass)) {
                            throw RuntimeException("setLeft,but value type is change:${id.source}")
                        }
                        val indexValue = scopeParams[index].parseResult.toValueResultElseThrow()
                        when (indexValue) {
                            is ParseResult.ValueResult.AnyValueResult -> indexValue.value = value
                            is ParseResult.ValueResult.ArrayValueResult -> indexValue.value =
                                value as Array<ParseResult.ValueResult<*>>

                            is ParseResult.ValueResult.BooleanValueResult -> indexValue.value = value as Boolean
                            is ParseResult.ValueResult.FloatValueResult -> indexValue.value = value as Double
                            is ParseResult.ValueResult.IntValueResult -> indexValue.value = value as Long
                            is ParseResult.ValueResult.StringValueResult -> indexValue.value = value as String
                        }

//                        scopeParams[index].parseResult = value.toValueResult()
//                        scopeParams[index] = p.copy(parseResult = value.toValueResult())
                    }
                } ?: throw RuntimeException("${id.text} not define before")
            }

            is R3Node.Expression.Define.Identifier.ID_REF -> {
                environments[id.text]?.let { p ->
                    if (value.javaClass != p::class.java) {
                        throw RuntimeException("setLeft,but value type is change")
                    }
                    environments[id.text] = value
                } ?: throw RuntimeException("${id.text} not define before")
            }
        }
    }

    private fun isSameClass(clazz1:Class<*>,clazz2: Class<*>): Boolean {
        return when (clazz1) {
            Int::class.java,
            Long::class.java -> {
                clazz2 == Int::class.java ||
                        clazz2 == Long::class.java
            }

            Float::class.java,
            Double::class.java -> {
                clazz2 == Float::class.java ||
                        clazz2 == Double::class.java
            }

            else -> {
                clazz1.isAssignableFrom(clazz2)
            }
        }
    }
}