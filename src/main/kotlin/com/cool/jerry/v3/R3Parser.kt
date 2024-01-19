package com.cool.jerry.v3

import com.cool.jerry.bridge.*
import com.cool.jerry.bridge.impl.*
import com.cool.jerry.exception.*
import com.cool.jerry.extern.Embed
import com.cool.jerry.model.*
import java.util.UUID
import kotlin.concurrent.thread
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor
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


    data class GrammarParser(
        val scopeParams: MutableList<Param>,
        val classes: MutableList<ParseResult.Define.ClassDefine>,
        val functions: MutableList<ParseResult.Define.FunctionDefine>,
    )

    fun parse(r3Node: R3Node): GrammarParser {
        val scopeParams = mutableListOf<Param>()
        val classes = mutableListOf<ParseResult.Define.ClassDefine>()
        val functions = mutableListOf<ParseResult.Define.FunctionDefine>()
        parse(r3Node, scopeParams, classes, functions)
        return GrammarParser(
            scopeParams,
            classes,
            functions
        )
    }

    fun parse(
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
                        if (scopeParams.find { it.paramName() == result.name } != null) {
                            throw RuntimeException("variable ${result.name} is already define")
                        }
                        scopeParams.add(Param(result.name, result))
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
                            if (classes.find { it.classStatement.className.text == result.classStatement.className.text } != null) {
                                throw RuntimeException("class ${result.classStatement.className.text} is already define")
                            }
                            classes.add(result)
                        }
                    }

                    is ParseResult.Define.FunctionDefine -> {
                        if (node.parent() == null) {
                            if (functions.find {
                                    it.functionStatement.functionName.text == result.functionStatement.functionName.text &&
                                            it.functionStatement.parameters.parameters.size == result.functionStatement.parameters.parameters.size
                                } != null) {
                                throw RuntimeException("function ${result.functionStatement.functionName.text} is already define")
                            }
                            functions.add(result)
                        }
                    }

                    else -> {}
                }
                result
            }
        }
    }

    private fun parseDefine(
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

    private fun parseBitOperate(
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
                    (left.value shl right.value.toTargetTypeInstance(
                        Int::class.java,
                        scopeParams,
                        classes,
                        functions
                    )).toValueResult()
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
                    (left.value shr right.value.toTargetTypeInstance(
                        Int::class.java,
                        scopeParams,
                        classes,
                        functions
                    )).toValueResult()
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

    private fun parseCompareOperate(
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

    private fun parseMathAssignOperate(
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math assign operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math assign operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult,
                            is ParseResult.ValueResult.ArrayValueResult,
                            is ParseResult.ValueResult.BooleanValueResult -> throw RuntimeException("*= left and right must be same ${left.value.javaClass} type,only support int and float ,string")

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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math assign operate left or right can't be rangeDefine")
                        }
                    }

                    else -> {
                        throw RuntimeException("math assign operate left or right can't be rangeDefine")
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
            reAssignLeftValue((node.left as R3Node.Expression.Define.Identifier), it.toValueResult(), scopeParams)
        }
        return ParseResult.NoneResult
    }

    private fun parseMathOperate(
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.StringValueResult -> {
                        when (right) {
                            is ParseResult.ValueResult.AnyValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.FloatValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.IntValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.StringValueResult -> "${left.value}${right.value}"
                            is ParseResult.ValueResult.BooleanValueResult -> "${left.value}${right.value}"

                            is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                "String + Array",
                                node.source
                            )

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array +", node.source)
                    is ParseResult.ValueResult.MapValueResult,
                    is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array /", node.source)
                    is ParseResult.ValueResult.MapValueResult,
                    is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> {
                        throw OpNotSupport("Array %", node.source)
                    }

                    is ParseResult.ValueResult.MapValueResult,
                    is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> {
                        throw OpNotSupport("Array *", node.source)
                    }

                    is ParseResult.ValueResult.MapValueResult,
                    is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

                            is ParseResult.ValueResult.MapValueResult,
                            is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
                        }
                    }

                    is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array -", node.source)
                    is ParseResult.ValueResult.MapValueResult,
                    is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("math operate left or right can't be rangeDefine")
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

    private fun parseNumberAutoOperate(
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
                reAssignLeftValue(node.who, (whoValue.value - 1).toValueResult(), scopeParams)
            }

            is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoIncreaseOperateExpression -> {
                reAssignLeftValue(node.who, (whoValue.value + 1).toValueResult(), scopeParams)
            }
        }
        return ParseResult.NoneResult
    }

    private fun parseOperate(
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

    private fun parseType(
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

    private fun parseExpression(
        node: R3Node.Expression,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): ParseResult {
        return when (node) {
            is R3Node.Expression.Define -> parseDefine(node, scopeParams, classes, functions)
            is R3Node.Expression.ArrayAccessExpression -> {
                var array = parse(node.array, scopeParams, classes, functions)
                if (array is ParseResult.Define.Variable){
                    if (array.value !is ParseResult.ValueResult.ArrayValueResult && array.value !is ParseResult.ValueResult.MapValueResult && array.value !is ParseResult.ValueResult.AnyValueResult) {
                        throw RuntimeException("${node.source} not a array or map define")
                    }
                    array = array.value
                }else{
                    if (array !is ParseResult.ValueResult.ArrayValueResult && array !is ParseResult.ValueResult.MapValueResult && array !is ParseResult.ValueResult.AnyValueResult) {
                        throw RuntimeException("${node.source} not a array or map define")
                    }
                }
                if (array is ParseResult.ValueResult.ArrayValueResult) {
                    val index =
                        parse(node.index, scopeParams, classes, functions).toValueResult().toIntValueResultElseThrow()
                    array.value[index.value.toInt()].toValueResult()
                } else if (array is ParseResult.ValueResult.MapValueResult) {
                    val key = parse(node.index, scopeParams, classes, functions)
                    val realKey = (scopeParams.find {
                        if (key is ParseResult.Define.Variable) {
                            it.paramName() == key.name
                        } else {
                            false
                        }
                    } ?: key).toValueResult().value
                    var result: ParseResult = ParseResult.NoneResult
                    for (entry in array.value.entries) {
                        if (entry.key.value == realKey) {
                            result = entry.value
                            break
                        }
                    }
                    result
                } else {
                    val toValueResult = array.toValueResult()
                    when (val value = toValueResult.value) {
                        is Array<*> -> {
                            val index = parse(node.index, scopeParams, classes, functions).toValueResult()
                                .toIntValueResultElseThrow()
                            value[index.value.toInt()]?.toValueResult() ?: ParseResult.NoneResult
                        }

                        is List<*> -> {
                            val index = parse(node.index, scopeParams, classes, functions).toValueResult()
                                .toIntValueResultElseThrow()
                            value[index.value.toInt()]?.toValueResult() ?: ParseResult.NoneResult
                        }

                        is Map<*, *> -> {
                            val key = parse(node.index, scopeParams, classes, functions)
                            val realKey = (scopeParams.find {
                                if (key is ParseResult.Define.Variable) {
                                    it.paramName() == key.name
                                } else {
                                    false
                                }
                            } ?: key).toValueResult().value
                            value[realKey]?.toValueResult() ?: ParseResult.NoneResult
                            var result: ParseResult = ParseResult.NoneResult
                            for (entry in value.entries) {
                                if (entry.key == realKey) {
                                    result = entry.value?.toValueResult() ?: ParseResult.NoneResult
                                    break
                                }
                            }
                            result
                        }

                        else -> {
                            throw RuntimeException("$value can't get properties ${node.index}")
                        }
                    }
                }
            }

            is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
                val value = parse(node.right, scopeParams, classes, functions)
                when (node.left) {
                    is R3Node.Expression.Define.Identifier -> {
                        reAssignLeftValue(node.left, value.toValueResultElseThrow(), scopeParams)
                    }

                    is R3Node.Expression.ObjectPropertiesExpression -> {
                        val objectHolder = parse(node.left.objectExpression, scopeParams, classes, functions)
                        if (objectHolder is ParseResult.ValueResult.AnyValueResult && objectHolder.value is ClassEnvironmentDefine) {
                            reAssignLeftValue(
                                node.left.propertiesName,
                                value.toValueResultElseThrow(),
                                (objectHolder.value as ClassEnvironmentDefine).scopeParams
                            )
                        } else if(objectHolder is ParseResult.ValueResult.AnyValueResult){
                            objectHolder.value::class.java.declaredFields.find {
                                it.name == node.left.propertiesName.text
                            }?.let {
                                it.isAccessible = true
                                when (value) {
                                    is ParseResult.ValueResult<*> -> {
                                        it.set(objectHolder.value, value.value)
                                    }
                                    is ParseResult.Define.Variable -> {
                                        it.set(objectHolder.value, value.value.value)
                                    }
                                    else -> {
                                        throw RuntimeException("$value's type not equals to properties's type")
                                    }
                                }
                            }
                        } else {
//                            objectHolder::class.java.declaredFields.find {
//                                it.name == node.left.propertiesName.text
//                            }?.let {
//                                it.isAccessible = true
//                                it.set(objectHolder, value.value!!)
//                            }
                            throw RuntimeException("$objectHolder not support modifier properties's value")
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
                    val booleanValue = if (condition is ParseResult.ValueResult.BooleanValueResult) {
                        condition.value
                    } else if (condition is ParseResult.Define.Variable) {
                        val value = condition.value
                        if (value is ParseResult.ValueResult.BooleanValueResult) {
                            value.value
                        } else {
                            throw RuntimeException("if condition must be boolean value")
                        }
                    } else {
                        throw RuntimeException("if condition must be boolean value")
                    }

                    val expressionList = if (booleanValue) {
                        node.thenBody
                    } else {
                        node.elseBody
                    }.toMutableList()
                return transformScope(scopeParams) { scopeParams->
                    expressionList.execute(scopeParams, classes, functions, true, true, true, false)
                }
            }

            is R3Node.Expression.LoopExpression -> {
                transformScope(scopeParams) { scopeParams->
                    var result: ParseResult = ParseResult.NoneResult
                    if (node.condition == null) {
                        while (true) {
                            val loopBody = node.loopBody.toMutableList()
                            result = loopBody.execute(scopeParams, classes, functions, true, true, true, false)
                            if (result is ParseResult.OperateResult.Break) {
                                result = ParseResult.NoneResult
                                break
                            } else if (result is ParseResult.OperateResult.Continue) {
                                continue
                            } else if (result is ParseResult.OperateResult.Return) {
                                break
                            }
                        }
                    } else {
                        val condition = parse(node.condition, scopeParams, classes, functions).toValueResultElseThrow()
                        if (condition is ParseResult.ValueResult.ArrayValueResult) {
                            for (valueResult in condition.value) {
                                if (node.conditionProxy != null) {
                                    val conditionProxy = Param(node.conditionProxy.text, valueResult)
                                    conditionProxy.replaceScopeParams(scopeParams, true)
                                }
                                val loopBody = node.loopBody.toMutableList()
                                result = loopBody.execute(scopeParams, classes, functions, true, true, true, false)
                                if (result is ParseResult.OperateResult.Break) {
                                    break
                                } else if (result is ParseResult.OperateResult.Continue) {
                                    continue
                                } else if (result is ParseResult.OperateResult.Return) {
                                    break
                                }
                            }
                        } else if (condition is ParseResult.ValueResult.RangeValueResult) {
                            for (valueResult in condition.value) {
                                if (node.conditionProxy != null) {
                                    val conditionProxy = Param(node.conditionProxy.text, valueResult.toValueResult())
                                    conditionProxy.replaceScopeParams(scopeParams, true)
                                }
                                val loopBody = node.loopBody.toMutableList()
                                result = loopBody.execute(scopeParams, classes, functions, true, true, true, false)
                                if (result is ParseResult.OperateResult.Break) {
                                    result = ParseResult.NoneResult
                                    break
                                } else if (result is ParseResult.OperateResult.Continue) {
                                    result = ParseResult.NoneResult
                                    continue
                                } else if (result is ParseResult.OperateResult.Return) {
                                    break
                                }
                            }
                        } else if (condition is ParseResult.ValueResult.BooleanValueResult) {
                            if (node.conditionProxy != null) {
                                throw RuntimeException("loop bool,don;t need conditionProxy:${node.source}")
                            }
                            while ((parse(node.condition, scopeParams, classes, functions).toValueResultElseThrow() as ParseResult.ValueResult.BooleanValueResult).value) {
                                val loopBody = node.loopBody.toMutableList()
                                result = loopBody.execute(scopeParams, classes, functions, true, true, true, false)
                                if (result is ParseResult.OperateResult.Break) {
                                    result = ParseResult.NoneResult
                                    break
                                } else if (result is ParseResult.OperateResult.Continue) {
                                    result = ParseResult.NoneResult
                                    continue
                                } else if (result is ParseResult.OperateResult.Return) {
                                    break
                                }
                            }
                        } else {
                            throw RuntimeException("loop only support array and range:${node.source}")
                        }
                    }
                    result
                }
            }

            is R3Node.Expression.MethodCallExpression -> {
                transformScope(scopeParams) {scopeParams->
                    val nodeMethodName = node.methodName
                    if (nodeMethodName is R3Node.Expression.Define.Identifier.ID){
                        val method = nodeMethodName.text

                        //internal function
                        val functionStatement = functions.find { it.functionStatement.functionName.text == method }
                        val lambdaExpression = scopeParams.filter {
                            val re = it.parseResult
                            (re is ParseResult.Define.Variable &&
                                    re.value is ParseResult.ValueResult.AnyValueResult &&
                                    re.value.value is ParseResult.Define.FunctionDefine &&
                                    (re.value.value as ParseResult.Define.FunctionDefine).functionStatement.parameters.parameters.size == node.arguments.size)

                                    ||

                                    (re is ParseResult.ValueResult.AnyValueResult &&
                                            re.value is ParseResult.Define.FunctionDefine &&
                                            (re.value as ParseResult.Define.FunctionDefine).functionStatement.parameters.parameters.size == node.arguments.size)
                        }.find {
                            method == it.paramName()
                        }
                        if (functionStatement != null) {
                            functionStatement.functionStatement.execute(
                                node.arguments,
                                scopeParams,
                                classes,
                                functions,
                                true
                            )
                        } else if (lambdaExpression != null) {
                            (lambdaExpression.parseResult.toValueResult().value as ParseResult.Define.FunctionDefine).functionStatement.execute(
                                node.arguments,
                                scopeParams,
                                classes,
                                functions,
                                true
                            )
                        } else {
                            //判断是否是调用了类的创建方法
                            val findClassCreate = classes.find {
                                val isClassInitConstructor = it.classStatement.className.text == method &&
                                        it.classStatement.parameters.parameters.size == node.arguments.size

                                var isOtherClassConstructor = false

                                for (constructorDefine in it.constructorDefine) {
                                    if (constructorDefine.functionStatement.functionStatement.parameters.parameters.size == node.arguments.size && it.classStatement.className.text == method) {
                                        isOtherClassConstructor = true
                                        break
                                    }
                                }

                                isClassInitConstructor || isOtherClassConstructor
                            }

                            if (findClassCreate != null) {
                                //调用类的创建,两种方式中，创建变量的顺序不一致，所以要写两次
                                findClassCreate.constructorDefine.find {
                                    it.functionStatement.functionStatement.parameters.parameters.size == node.arguments.size
                                }?.let {
                                    //创建类变量
                                    findClassCreate.variableExpression.map {
                                        parse(it, scopeParams, classes, functions)
                                    }.forEach {
                                        if (it is ParseResult.Define.Variable) {
                                            if (scopeParams.find { g -> g.paramName() == it.name } != null) {
                                                throw RuntimeException("variable ${it.name} is already define")
                                            }
                                            Param(it.name, it).replaceScopeParams(scopeParams, true)
                                        }
                                    }

                                    //调用匹配的构造方法
                                    it.functionStatement.functionStatement.execute(
                                        node.arguments,
                                        scopeParams,
                                        classes,
                                        functions,
                                        false
                                    )
                                } ?: run {
                                    //如果没有默认构造方法
                                    node.arguments.convertArguments(
                                        findClassCreate.classStatement.parameters.parameters.map { it.text },
                                        scopeParams,
                                        classes,
                                        functions
                                    ).replaceScopeParams(scopeParams, false)


                                    //创建类变量
                                    findClassCreate.variableExpression.map {
                                        parse(it, scopeParams, classes, functions)
                                    }.forEach {
                                        if (it is ParseResult.Define.Variable) {
                                            if (scopeParams.find { g -> g.paramName() == it.name } != null) {
                                                throw RuntimeException("variable ${it.name} is already define")
                                            }
                                            Param(it.name, it).replaceScopeParams(scopeParams, true)
                                        }
                                    }
                                }

                                //这里调用构造函数应该将该类创建并返回
                                ParseResult.ValueResult.AnyValueResult(
                                    ClassEnvironmentDefine(
                                        findClassCreate,
                                        mutableListOf(*scopeParams.toTypedArray()),
                                    )
                                )
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

                                val injectMethod = getEnvironmentMethod(method, arguments.map { it.second })
                                val parameters = injectMethod.method.parameters
                                val argumentParams = arguments.map {
                                    it.second.value
                                }.mapIndexed { index, any ->
                                    any?.toTargetTypeInstance(parameters[index].type, scopeParams, classes, functions)
                                }
                                injectMethod.invokeMethod(*argumentParams.toTypedArray()).toValueResultElseNone()
                            }
                        }
                    }else{
                        val nodeMethodParser = parse(nodeMethodName,scopeParams,classes, functions)
                        if (nodeMethodParser is ParseResult.Define.FunctionDefine){
                            nodeMethodParser.functionStatement.execute(
                                node.arguments,
                                scopeParams,
                                classes,
                                functions,
                                true
                            )
                        }else{
                            throw RuntimeException("only support lambdaExpression ")
                        }
                    }
                }
            }

            is R3Node.Expression.ObjectMethodCallExpression -> {
                transformScope(scopeParams) {scopeParams->
                    val objectDefine =
                        parse(node.objectExpression, scopeParams, classes, functions).toValueResultElseThrow()
                    val methodDefine = node.methodName.text
                    val arguments = node.arguments.map {
                        parse(it, scopeParams, classes, functions).toValueResult()
                    }.mapIndexed { index, valueResult ->
                        node.arguments[index] to valueResult
                    }
                    val value = objectDefine.value!!

                    if (value is ClassEnvironmentDefine) {
                        val functionStatement = value.classDefine.functionDefine.find {
                            it.functionStatement.functionName.text == methodDefine && it.functionStatement.parameters.parameters.size == arguments.size
                        } ?: throw RuntimeException("$objectDefine don't define $methodDefine function")
                        functionStatement.functionStatement.execute(
                            node.arguments, value.scopeParams, mutableListOf(),
                            value.classDefine.functionDefine.toMutableList(),
                            true
                        )
                    } else {
                        val clazz = value::class.java
                        val allMethod = (clazz.declaredMethods + clazz.methods).distinct()

                        val method = allMethod.find {
                            it.name == methodDefine && it.parameterCount == arguments.size
                        } ?: throw RefNotDefineMethodException(clazz.name, methodDefine)
                        val parameters = method.parameters
                        val argumentsConverter = arguments.mapIndexed { index, it ->
                            val value = if (it.first is R3Node.Expression.Define.Identifier.ID) {
                                scopeParams.find { a -> (it.first as R3Node.Expression.Define.Identifier.ID).text == a.paramName() }!!.parseResult
                            } else {
                                it.second
                            }
                            Param(parameters[index].name, value)
                        }.mapIndexed { index, it ->
                            it.parseResult.toValueResultElseThrow().value?.toTargetTypeInstance(
                                parameters[index].type,
                                scopeParams,
                                classes,
                                functions
                            )
                        }
                        method.invoke(value, *argumentsConverter.toTypedArray()).toValueResultElseNone()
                    }
                }
            }

            is R3Node.Expression.ObjectPropertiesExpression -> {
                transformScope(scopeParams) {scopeParams->
                    val objectDefine =
                        parse(node.objectExpression, scopeParams, classes, functions).toValueResultElseThrow()
                    val value = objectDefine.value!!
                    val propertiesName = node.propertiesName.text
                    if (value is ClassEnvironmentDefine) {
                        value.scopeParams.find {
                            it.paramName() == propertiesName
                        }?.parseResult
                            ?: throw RuntimeException("${node.objectExpression.source} not define properties:$propertiesName")
                    } else {
                        val clazz = value::class.java
                        val allFields = (clazz.fields + clazz.declaredFields).distinct()
                        (
                                allFields.find {
                                    it.name == propertiesName
                                }?.let {
                                    it.isAccessible = true
                                    it
                                }?.get(value) ?: throw RefNotDefinePropertiesException(
                                    clazz.name,
                                    propertiesName
                                )
                                ).toValueResult()
                    }
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

                ParseResult.ValueResult.RangeValueResult(LongRange(start = start.value, end.value))
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
                val parent = node.variableValue.parent()
                val defineScope = when (parent) {
                    is R3Node.Statement.ClassStatement -> ParseResult.Define.Variable.DefineScope.CLASS
//                    is R3Node.Expression.IfExpression,
//                    is R3Node.Expression.LoopExpression,
//                    is R3Node.Expression.CatchErrorExpression,
                    is R3Node.Statement.FunctionStatement -> ParseResult.Define.Variable.DefineScope.FUN
                    null -> {
                        ParseResult.Define.Variable.DefineScope.GLOBAL
                    }

                    else -> {
                        ParseResult.Define.Variable.DefineScope.INNER
                    }
                }

                val name = node.variableName.text
                val isConst = node.isConst
//                if (scopeParams.find {
//                        if (it.parseResult is ParseResult.Define.Variable) {
//                            it.paramName() == name &&
//                            (it.parseResult as ParseResult.Define.Variable).defineScope == defineScope
//                        } else {
//                            false
//                        }
//                    } != null) {
//                    throw RuntimeException("$name is already define before parent scope")
//                }
                ParseResult.Define.Variable(name, parse, isConst, defineScope)
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

                    is ParseResult.ValueResult.RangeValueResult -> throw RuntimeException("signed Expr must be a Number:${node.source}")
                    is ParseResult.ValueResult.MapValueResult -> throw RuntimeException("signed Expr must be a Number:${node.source}")
                }
            }

            is R3Node.Expression.LambdaExpression -> {
                val functionName =
                    "LambdaExpression_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + "_Define"
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

            is R3Node.Expression.CatchErrorExpression -> {
                var result: ParseResult = ParseResult.NoneResult

                result = try {
                    transformScope(scopeParams) { scopeParams->
                        node.watchBody.toMutableList().execute(scopeParams, classes, functions, true, true, true, false)
                    }
                } catch (e: Exception) {
                    transformScope(scopeParams) { scopeParams ->
                        Param(node.errorName.text, (e.cause ?: e).toValueResult()).replaceScopeParams(scopeParams, true)
                        node.errorBody.toMutableList().execute(scopeParams, classes, functions, true, true, true, false)
                    }
               }
                result
            }

            is R3Node.Expression.MapExpression -> {
                val map = mutableMapOf<ParseResult.ValueResult<*>, ParseResult.ValueResult<*>>()
                for (entry in node.mapExpression) {
                    val key = parse(entry.key, scopeParams, classes, functions).toValueResultElseThrow()
                    val parse = parse(entry.value, scopeParams, classes, functions).toValueResultElseThrow()
                    map[key] = parse

                }
                ParseResult.ValueResult.MapValueResult(
                    map
                )
            }

            is R3Node.Expression.ReserveExpression -> {
                val result = parse(node.expression, scopeParams, classes, functions).toValueResultElseThrow()
                if (result !is ParseResult.ValueResult.BooleanValueResult) {
                    throw RuntimeException("reserve only use at bool")
                }
                return ParseResult.ValueResult.BooleanValueResult(
                    !result.value
                )
            }

            is R3Node.Expression.AsyncExpression -> {
                val asyncBody = node.asyncBody.toMutableList()
                if(asyncBody.isNotEmpty()){
                    var result:ParseResult = ParseResult.NoneResult
                    val needWait = asyncBody.haveReturnExpression()
                    var isComplete = false
                    val copyScopeParams = ProxyMutableList(scopeParams){index,old,new->
                        val oldParseResult = old.parseResult
                        val newParseResult = new.parseResult.toValueResult()
                        if (oldParseResult::class.java!=newParseResult::class.java){
                            throw RuntimeException("${old.paramName()}'s type not equals ${new.paramName()}'s type")
                        }
                        when (oldParseResult) {
                            is ParseResult.Define.Variable -> {
                                when (oldParseResult.value) {
                                    is ParseResult.ValueResult.AnyValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.AnyValueResult).copy()
                                    is ParseResult.ValueResult.ArrayValueResult ->oldParseResult.value = (newParseResult as ParseResult.ValueResult.ArrayValueResult).copy()

                                    is ParseResult.ValueResult.BooleanValueResult -> {
                                        oldParseResult.value = (newParseResult as ParseResult.ValueResult.BooleanValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.FloatValueResult -> {
                                        oldParseResult.value = (newParseResult as ParseResult.ValueResult.FloatValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.IntValueResult -> {
                                        oldParseResult.value = (newParseResult as ParseResult.ValueResult.IntValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.StringValueResult -> {
                                        oldParseResult.value = (newParseResult as ParseResult.ValueResult.StringValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.MapValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.MapValueResult).copy()

                                    is ParseResult.ValueResult.RangeValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.RangeValueResult).copy()
                                }
                            }

                            is ParseResult.ValueResult.AnyValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.AnyValueResult).copy().value
                            is ParseResult.ValueResult.ArrayValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.ArrayValueResult).copy().value

                            is ParseResult.ValueResult.BooleanValueResult -> {
                                oldParseResult.value = (newParseResult as ParseResult.ValueResult.BooleanValueResult).copy().value
                            }

                            is ParseResult.ValueResult.FloatValueResult -> {
                                oldParseResult.value = (newParseResult as ParseResult.ValueResult.FloatValueResult).copy().value
                            }

                            is ParseResult.ValueResult.IntValueResult -> {
                                oldParseResult.value = (newParseResult as ParseResult.ValueResult.IntValueResult).copy().value
                            }

                            is ParseResult.ValueResult.StringValueResult -> {
                                oldParseResult.value = (newParseResult as ParseResult.ValueResult.StringValueResult).copy().value
                            }

                            is ParseResult.ValueResult.RangeValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.RangeValueResult).copy().value
                            is ParseResult.ValueResult.MapValueResult -> oldParseResult.value = (newParseResult as ParseResult.ValueResult.MapValueResult).copy().value

                            else -> throw RuntimeException("$oldParseResult not support modifier")
                        }
                    }
                    thread {
                        result = transformScope(copyScopeParams){ scopeParams->
                            var execute = asyncBody.execute(scopeParams,classes,functions,true,true,true,false)
                            if (execute is ParseResult.OperateResult.Return) {
                                execute = execute.value?:ParseResult.NoneResult
                            }
                            execute
                        }
                        isComplete = true
                    }
                    if(needWait){
                        while (!isComplete){
                            Embed.sleep(50)
                        }
                        result
                    }else{
                        ParseResult.NoneResult
                    }
                }else{
                    ParseResult.NoneResult
                }
            }
        }
    }

    private fun parseProgram(
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

    private fun parseStatement(
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

                constructor.groupBy {
                    it.functionStatement.functionStatement.parameters.parameters.size
                }.takeIf { it.size > 1 }?.let {
                    throw RuntimeException("${node.className.text}'s constructor is Repetition")
                }

                function.groupBy {
                    it.functionStatement.functionName
                }.let { it ->
                    it.forEach { (_, u) ->
                        u.groupBy { g ->
                            g.functionStatement.parameters.parameters.size
                        }.takeIf { it.size > 1 }?.let { c ->
                            throw RuntimeException("${node.className.text}'s function [${c[0]!![0].functionStatement.source}] is Repetition")
                        }
                    }
                }

                variableExpression.groupBy {
                    it.variableName.text
                }.let {
                    for (entry in it) {
                        if (entry.value.size>1){
                            throw RuntimeException("${node.className.text}'s variable [${entry.value[0].variableName.text}] is Repetition")
                        }
                    }
                }


                ParseResult.Define.ClassDefine(
                    node,
                    variableExpression,
                    function,
                    constructor
                )
            }

            is R3Node.Statement.FunctionStatement -> {
                val find =
                    injectMethods.find { it.name == node.functionName.text && it.method.parameterCount == node.parameters.parameters.size }
                if (find != null) {
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


    private fun List<R3Node.Expression>.convertArguments(
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

    private fun List<Any>.convertArgumentsByBridge(
        autalParametersNames: List<String>,
        scopeParams: MutableList<Param>
    ): MutableList<Param> {
        val arguments = this.map {
            it.toValueResult()
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
    private fun R3Node.Statement.FunctionStatement.execute(
        arguments: List<R3Node.Expression>,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>,
        REPLACE: Boolean
    ): ParseResult {
        return transformScope(scopeParams) { scopeParams->
            if (parameters.parameters.size != arguments.size) {
                throw RuntimeException("params size not equals")
            }
            arguments.convertArguments(parameters.parameters.map { it.text }, scopeParams, classes, functions)
                .replaceScopeParams(scopeParams, REPLACE)
            val functionBody = functionBody.toMutableList()
            var result = functionBody.execute(scopeParams, classes, functions, true, false, true, true)
            if (result is ParseResult.OperateResult.Break) {
                throw RuntimeException("not support break")
            } else if (result is ParseResult.OperateResult.Continue) {
                throw RuntimeException("not support continue")
            } else if (result is ParseResult.OperateResult.Return) {
                result = result.value?:ParseResult.NoneResult
            }
            result
        }
    }


    /**
     * @param arguments 传入的实际参数值
     * @param scopeParams 当前上下文的环境变量
     */
    private fun R3Node.Statement.FunctionStatement.executeByBridge(
        arguments: List<Any>,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>,
        REPLACE: Boolean
    ): ParseResult {
        return transformScope(scopeParams) { scopeParams->
            if (parameters.parameters.size != arguments.size) {
                throw RuntimeException("params size not equals")
            }
            arguments.convertArgumentsByBridge(parameters.parameters.map { it.text }, scopeParams)
                .replaceScopeParams(scopeParams, REPLACE)
            val functionBody = functionBody.toMutableList()
            var result = functionBody.execute(scopeParams, classes, functions, true, false, true, false)
            if (result is ParseResult.OperateResult.Break) {
                throw RuntimeException("not support break")
            } else if (result is ParseResult.OperateResult.Continue) {
                throw RuntimeException("not support continue")
            } else if (result is ParseResult.OperateResult.Return) {
                result = result.value?:ParseResult.NoneResult
            }
            result
        }
    }


    private fun MutableList<R3Node.Expression>.execute(
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>,
        needBreak: Boolean = false,
        needContinue: Boolean = false,
        needReturn: Boolean = false,
        removeFuncDefine: Boolean//移除scopeParams 中的Fuc Variable
    ): ParseResult {
        var result: ParseResult = ParseResult.NoneResult

        val listIterator = scopeParams.listIterator()
        while (listIterator.hasNext()) {
            //todo 如果前面语句中有线程，那么这里会崩溃
            val next = listIterator.next()
            val parseResult = next.parseResult
            if (parseResult is ParseResult.Define.Variable && next.paramName() == parseResult.name) {
                if (parseResult.defineScope != ParseResult.Define.Variable.DefineScope.CLASS && parseResult.defineScope != ParseResult.Define.Variable.DefineScope.GLOBAL) {
                    if (parseResult.defineScope == ParseResult.Define.Variable.DefineScope.FUN) {
                        if (removeFuncDefine) {
                            listIterator.remove()
                        }
                    } else {
                        listIterator.remove()
                    }
                }
            }
        }

        for ((index, expression) in this.withIndex()) {
            val expressionResult = parse(expression, scopeParams, classes, functions)

            if (expressionResult is ParseResult.Define.Variable) {
                if (scopeParams.find {
                        if (it.parseResult is ParseResult.Define.Variable) {
                            it.paramName() == expressionResult.name
                        } else {
                            false
                        }
                    } != null) {
                    throw RuntimeException("${expressionResult.name} is already define before parent scope")
                }
                Param(expressionResult.name, expressionResult).replaceScopeParams(scopeParams, true)
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
                            result = ParseResult.OperateResult.Return(
                                expressionResult.value
                            )
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



    /**
     * 如果有相同就替换，没有就添加
     *
     * @param REPLACE true:表示复制这个值，但HASH地址不同，否则，不改变值，只改变值的内容，hash地址一样
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
     * 重新对环境中的变量进行赋值
     */
    private fun reAssignLeftValue(
        id: R3Node.Expression.Define.Identifier,
        value: ParseResult.ValueResult<*>,
        scopeParams: MutableList<Param>
    ) {
        when (id) {
            is R3Node.Expression.Define.Identifier.ID -> {
                scopeParams.find { id.text == it.paramName() }?.let { p ->
                    if (p.parseResult is ParseResult.Define.Variable && (p.parseResult as ParseResult.Define.Variable).isConst) {
                        throw RuntimeException("${p.paramName()} is const, can't modifier")
                    }

                    scopeParams.indexOf(p).takeIf { index -> index != -1 }?.let { index ->
                        if (!isSameClass(
                                value.value!!::class.java,
                                p.parseResult.toValueResultElseThrow().value!!::class.java
                            )
                        ) {
                            throw RuntimeException("you want assign a issues value type to anther value type: sourceType:${p.parseResult.toValueResult().value!!::class.java},newValueType:${value.value!!::class.java}")
                        }
                        val indexValue = scopeParams[index].parseResult
                        when (indexValue) {
                            is ParseResult.Define.Variable -> {
                                when (indexValue.value) {
                                    is ParseResult.ValueResult.AnyValueResult -> indexValue.value = (value as ParseResult.ValueResult.AnyValueResult).copy()
                                    is ParseResult.ValueResult.ArrayValueResult -> indexValue.value = (value as ParseResult.ValueResult.ArrayValueResult).copy()

                                    is ParseResult.ValueResult.BooleanValueResult -> {
                                        indexValue.value = (value as ParseResult.ValueResult.BooleanValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.FloatValueResult -> {
                                        indexValue.value = (value as ParseResult.ValueResult.FloatValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.IntValueResult -> {
                                        indexValue.value = (value as ParseResult.ValueResult.IntValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.StringValueResult -> {
                                        indexValue.value = (value as ParseResult.ValueResult.StringValueResult).copy()
                                    }

                                    is ParseResult.ValueResult.MapValueResult -> indexValue.value = (value as ParseResult.ValueResult.MapValueResult).copy()

                                    is ParseResult.ValueResult.RangeValueResult -> indexValue.value = (value as ParseResult.ValueResult.RangeValueResult).copy()

                                }
                            }

                            is ParseResult.ValueResult.AnyValueResult -> scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.AnyValueResult).copy())
                            is ParseResult.ValueResult.ArrayValueResult ->  scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.ArrayValueResult).copy())

                            is ParseResult.ValueResult.BooleanValueResult -> {
                                scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.BooleanValueResult).copy())
                            }

                            is ParseResult.ValueResult.FloatValueResult -> {
                                scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.FloatValueResult).copy())
                            }

                            is ParseResult.ValueResult.IntValueResult -> {
                                scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.IntValueResult).copy())
                            }

                            is ParseResult.ValueResult.StringValueResult -> {
                                scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.StringValueResult).copy())
                            }

                            is ParseResult.ValueResult.RangeValueResult -> scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.RangeValueResult).copy())
                            is ParseResult.ValueResult.MapValueResult ->  scopeParams[index] = scopeParams[index].copy(parseResult = (value as ParseResult.ValueResult.MapValueResult).copy())

                            else -> throw RuntimeException("$indexValue not support modifier")
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

    private fun isSameClass(clazz1: Class<*>, clazz2: Class<*>): Boolean {
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


    private fun <T> Any.toTargetTypeInstance(
        type: Class<T>,
        scopeParams: MutableList<Param>,
        classes: MutableList<ParseResult.Define.ClassDefine>,
        functions: MutableList<ParseResult.Define.FunctionDefine>
    ): T {
        if (this is ParseResult.ValueResult<*>) {
            throw RuntimeException("please use ParseResult.ValueResult.value to convert to Other type")
        }

        if (this is R3Node) {
            throw RuntimeException("please don't use R3Node convert to Other type")
        }

        return when (type) {
            FunctionCallBridge0::class.java -> {
                FunctionCallBridge0Impl {
                    val functionDefine = this as ParseResult.Define.FunctionDefine
                    transformScope(scopeParams) {
                        functionDefine.functionStatement.executeByBridge(
                            emptyList(),
                            scopeParams,
                            classes,
                            functions,
                            true
                        )
                    }.let {
                        when (it) {
                            is ParseResult.ValueResult<*> -> it.value!!
                            is ParseResult.OperateResult.Return -> it.value?.value ?: Unit
                            else -> Unit
                        }
                    }
                }
            }

            FunctionCallBridge1::class.java -> {
                FunctionCallBridge1Impl { it ->
                    val functionDefine = this as ParseResult.Define.FunctionDefine
                    transformScope(scopeParams) {
                        functionDefine.functionStatement.executeByBridge(
                            listOf(it),
                            scopeParams,
                            classes,
                            functions,
                            true
                        )
                    }.let {
                        when (it) {
                            is ParseResult.ValueResult<*> -> it.value!!
                            is ParseResult.OperateResult.Return -> it.value?.value ?: Unit
                            else -> Unit
                        }
                    }
                }
            }

            FunctionCallBridge2::class.java -> {
                FunctionCallBridge2Impl { p1, p2 ->
                    val functionDefine = this as ParseResult.Define.FunctionDefine
                    transformScope(scopeParams) {
                        functionDefine.functionStatement.executeByBridge(
                            listOf(p1, p2),
                            scopeParams,
                            classes,
                            functions,
                            true
                        )
                    }.let {
                        when (it) {
                            is ParseResult.ValueResult<*> -> it.value!!
                            is ParseResult.OperateResult.Return -> it.value?.value ?: Unit
                            else -> Unit
                        }
                    }
                }
            }

            FunctionCallBridge3::class.java -> {
                FunctionCallBridge3Impl { p1, p2, p3 ->
                    val functionDefine = this as ParseResult.Define.FunctionDefine
                    transformScope(scopeParams) {
                        functionDefine.functionStatement.executeByBridge(
                            listOf(p1, p2, p3),
                            scopeParams,
                            classes,
                            functions,
                            true
                        )
                    }.let {
                        when (it) {
                            is ParseResult.ValueResult<*> -> it.value!!
                            is ParseResult.OperateResult.Return -> it.value?.value ?: Unit
                            else -> Unit
                        }
                    }
                }
            }

            FunctionCallBridge4::class.java -> {
                FunctionCallBridge4Impl { p1, p2, p3, p4 ->
                    val functionDefine = this as ParseResult.Define.FunctionDefine
                    transformScope(scopeParams) {
                        functionDefine.functionStatement.executeByBridge(
                            listOf(p1, p2, p3, p4),
                            scopeParams,
                            classes,
                            functions,
                            true
                        )
                    }.let {
                        when (it) {
                            is ParseResult.ValueResult<*> -> it.value!!
                            is ParseResult.OperateResult.Return -> it.value?.value ?: Unit
                            else -> Unit
                        }
                    }
                }
            }

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

            else -> {
                if (this is ParseResult.Define.FunctionDefine) {
                    this.functionStatement.execute(emptyList(), scopeParams, classes, functions, true)
                        .toValueResult()
                        .value
                        ?.toTargetTypeInstance(type, scopeParams, classes, functions)
                } else {
                    this
                }
            }
        } as T
    }

    private fun transformScope(scopeParams: MutableList<Param>, scope: (scopeParams:MutableList<Param>) -> ParseResult): ParseResult {
        val organic = mutableListOf(*scopeParams.toTypedArray())
        val organicNames = scopeParams.map { it.paramName() }
//        val proxyMutableList = ProxyMutableList(scopeParams)
        val result = scope(scopeParams)
//        val listIterator = scopeParams.listIterator()
//        while (listIterator.hasNext()){
//            val nnn = listIterator.next()
//            if (organic.find { nnn.paramName() == it.paramName() }==null){
//                listIterator.remove()
//            }
//        }
//        if (scopeParams.isNotEmpty()){
//            scopeParams.clear()
//        }
//        if (organic.isNotEmpty()){
//            scopeParams.addAll(organic)
//        }
        val listIterator = scopeParams.listIterator()
        while (listIterator.hasNext()){//把原来列表没有的数据从这个列表里面删除
            val next = listIterator.next()
            if (!organicNames.contains(next.paramName())){
                listIterator.remove()
            }
        }
        for (organicName in organicNames) {//
            if (scopeParams.find { it.paramName()==organicName }==null){
                organic.find { it.paramName()==organicName }?.let {
                    scopeParams.add(it)
                }
            }
        }
        return result
    }
}