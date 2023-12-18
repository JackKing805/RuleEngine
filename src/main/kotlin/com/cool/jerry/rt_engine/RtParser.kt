package com.cool.jerry.rt_engine

import com.cool.jerry.rt_engine.define.Node
import com.cool.jerry.rt_engine.exception.*
import java.lang.StringBuilder
import java.lang.reflect.Method
import kotlin.RuntimeException

class RtParser {
    private val environments = mutableMapOf<String, Any>()

    fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    fun setEnvironment(map: Map<String, Any>) {
        environments.putAll(map)
    }


    fun setEnvironmentMethod(key: String, method: Method) {

    }

    fun setEnvironmentMethod(map: Map<String, Method>) {

    }

    private fun getEnvironment(key: String): Any {
        return environments[key] ?: throw NullPointerException("not found value at environment")
    }

    private fun getEnvironmentAsValueResult(key: String): ParseResult.ValueResult<*> {
        return getEnvironment(key).let {
            when (it) {
                is Int,
                is Long -> {
                    ParseResult.ValueResult.IntValueResult(it as Long)
                }

                is String -> {
                    ParseResult.ValueResult.StringValueResult(it)
                }

                is Float,
                is Double -> {
                    ParseResult.ValueResult.FloatValueResult(it as Double)
                }

                else -> {
                    ParseResult.ValueResult.AnyValueResult(it)
                }
            }
        }
    }

    //变量池
    private val variables = mutableMapOf<String, Any>()
    private val functions = mutableMapOf<String, Node.Statement.FunctionStatement>()

    fun parse(node: Node) {
        variables.clear()
        functions.clear()
        parseNode(node, emptyList()).let {
            println(it)
        }
    }

    private fun parseStatement(node: Node.Statement, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Statement.FunctionStatement -> {
                functions[node.functionName.name] = node
                ParseResult.NoneResult()
            }

            is Node.Statement.InvokeStatement -> {
                parseExpression(node.expression, params)
            }

            is Node.Statement.NewVariableAssignmentStatement -> {
                if (variables.containsKey(node.id.name)) {
                    throw DulVarDefineException(node.id.name)
                }
                val result = parseNode(node.value, params).getValueResultElseThrow()
                variables[node.id.name] = result.value as Any
                ParseResult.NoneResult()
            }

            is Node.Statement.VariableAssignmentStatement -> {
                if (variables.containsKey(node.id.name)) {
                    throw DulVarDefineException(node.id.name)
                }
                val result = parseNode(node.value, params)
                variables[node.id.name] = result
                ParseResult.Variable(node.id.name, result.getValueResultElseThrow())
            }

            is Node.Statement.VariablePropertiesAssignmentStatement -> {
                val variableName = node.id.name
                val methodName = node.propertiesIdExpression.name
                val parseNode = parseNode(node.value, params)

                val any = getEnvironment(variableName)
                any::class.java.declaredFields.find {
                    it.name == methodName
                }?.let {
                    it.isAccessible = true
                    parseNode.getValueResultElseThrow().let { value ->
                        it.set(any, value.value)
                    }
                } ?: throw RefNotDefinePropertiesException(variableName, methodName)
                ParseResult.NoneResult()
            }
        }
    }

    private fun parseIdExpression(node: Node.Expression.IdExpression, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression.IdExpression.Id -> {
                params.find {
                    it.name == node.name
                }?.parseResult ?: run {
                    if (!variables.containsKey(node.name)) {
                        throw VarNotDefineException(node.name)
                    }
                    when (val value = variables[node.name]!!) {
                        is Int,
                        is Long -> {
                            ParseResult.ValueResult.IntValueResult(value as Long)
                        }

                        is String -> {
                            ParseResult.ValueResult.StringValueResult(value)
                        }

                        is Float,
                        is Double -> {
                            ParseResult.ValueResult.FloatValueResult(value as Double)
                        }

                        else -> {
                            ParseResult.ValueResult.AnyValueResult(value)
                        }
                    }
                }
            }

            is Node.Expression.IdExpression.IdRef -> {
                if (!environments.containsKey(node.name)) {
                    throw RefNotDefineException(node.name)
                }
                when (val value = environments[node.name]!!) {
                    is Int,
                    is Long -> {
                        ParseResult.ValueResult.IntValueResult(value as Long)
                    }

                    is String -> {
                        ParseResult.ValueResult.StringValueResult(value)
                    }

                    is Float,
                    is Double -> {
                        ParseResult.ValueResult.FloatValueResult(value as Double)
                    }

                    else -> {
                        ParseResult.ValueResult.AnyValueResult(value)
                    }
                }
            }
        }
    }

    private fun parseTypeExpression(node: Node.Expression.TypeExpression): ParseResult {
        return when (node) {
            is Node.Expression.TypeExpression.FloatType -> ParseResult.ValueResult.FloatValueResult(node.value)
            is Node.Expression.TypeExpression.IntType -> ParseResult.ValueResult.IntValueResult(node.value)
            is Node.Expression.TypeExpression.StringType -> ParseResult.ValueResult.StringValueResult(node.value)
        }
    }

    private fun parseExpression(node: Node.Expression, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression.IdExpression -> parseIdExpression(node, params)
            is Node.Expression.TypeExpression -> parseTypeExpression(node)
            is Node.Expression.AddSubExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).getValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).getValueResultElseThrow()
                val result = if (node.op == "+") {
                    //+
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.IntValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }
                    }
                } else {
                    //-
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Float - String")
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Int - String")
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.IntValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.StringValueResult -> leftExpression.value.replace(
                                    rightExpression.value,
                                    ""
                                )
                            }
                        }
                    }
                }
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
                        throw IllStmtException("error ${node.op} operation")
                    }
                }
            }

            is Node.Expression.MulDivExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).getValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).getValueResultElseThrow()
                val result = if (node.op == "*") {
                    //+
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Float * String")
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> {
                                    if (leftExpression.value <= 0) {
                                        throw OpNotSupport("o or -Int * String")
                                    }

                                    val string = StringBuilder()
                                    for (l in (0..<leftExpression.value)) {
                                        string.append(rightExpression.value)
                                    }
                                    string.toString()
                                }
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport("Float * String")
                                is ParseResult.ValueResult.IntValueResult -> {
                                    if (rightExpression.value <= 0) {
                                        throw OpNotSupport("o or -Int * String")
                                    }

                                    val string = StringBuilder()
                                    for (l in (0..<rightExpression.value)) {
                                        string.append(leftExpression.value)
                                    }
                                    string.toString()
                                }

                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("String * String")
                            }
                        }
                    }
                } else {
                    //-
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Float / String")
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("String / String")
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport("String / Float")
                                is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport("String / Int")
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("String / String")
                            }
                        }
                    }
                }
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
                        throw IllStmtException("error ${node.op} operation")
                    }
                }
            }

            is Node.Expression.MethodCallExpression -> {
                val callIdExpression = node.callIdExpression?.name
                val methodIdExpression = node.methodIdExpression.name
                val params = node.params

                if (callIdExpression == null) {
                    //internal function
                    val functionStatement =
                        functions[methodIdExpression] ?: throw NotDefineMethodException(methodIdExpression)
                    if (functionStatement.params.size != params.size) {
                        throw RuntimeException("params size not equals")
                    }
                    val nodeParams = params.map {
                        parseNode(it, emptyList())
                    }.mapIndexed { index, parseResult ->
                        Param(functionStatement.params[index].name, parseResult)
                    }

                    for (statement in functionStatement.statements) {
                        parseNode(statement, nodeParams)
                    }

                    functionStatement.returnExpression?.let { parseNode(it,nodeParams) }?:ParseResult.NoneResult()
                } else {
                    //outside function
                    val any = getEnvironment(callIdExpression)
                    val method = any::class.java.declaredMethods.find {
                        it.name == methodIdExpression
                    }?:throw RefNotDefineMethodException(callIdExpression,methodIdExpression)
                    //TODO
                }
                ParseResult.NoneResult()
            }
        }
    }

    private fun parseProgram(node: Node.Program): ParseResult {
        val all = node.nodes.map {
            parseNode(it, emptyList())
        }
        return all.lastOrNull() ?: ParseResult.NoneResult()
    }

    private fun parseNode(node: Node, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression -> parseExpression(node, params)
            is Node.Program -> parseProgram(node)
            is Node.Statement -> parseStatement(node, params)
        }
    }

    private fun ParseResult.getValueResultElseThrow(): ParseResult.ValueResult<*> {
        return when (this) {
            is ParseResult.NoneResult -> throw RuntimeException("$this not a valueResult")
            is ParseResult.ValueResult.AnyValueResult -> this
            is ParseResult.ValueResult.FloatValueResult -> this
            is ParseResult.ValueResult.IntValueResult -> this
            is ParseResult.ValueResult.StringValueResult -> this
            is ParseResult.Variable -> value.getValueResultElseThrow()
        }
    }

    sealed class ParseResult {
        sealed class ValueResult<T>(open val value: T) : ParseResult() {
            data class IntValueResult(override val value: Long) : ValueResult<Long>(value)
            data class StringValueResult(override val value: String) : ValueResult<String>(value)
            data class FloatValueResult(override val value: Double) : ValueResult<Double>(value)
            data class AnyValueResult(override val value: Any) : ValueResult<Any>(value)
        }

        class NoneResult : ParseResult()


        class Variable(val name: String, val value: ValueResult<*>) : ParseResult()

//        sealed class NameResult(val name: String) : ParseResult() {
//            class NameResult(name: String) : ParseResult.NameResult(name)
//            class RefNameResult(name: String) : ParseResult.NameResult(name)
//        }
    }

    data class Param(
        val name: String,
        val parseResult: ParseResult
    )
}