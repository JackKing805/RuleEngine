package com.cool.jerry.v2.rt_engine

import com.cool.jerry.exception.*
import com.cool.jerry.model.*
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.v2.rt_engine.define.Node
import java.lang.StringBuilder
import kotlin.RuntimeException

class RtParser {
    private val environments = mutableMapOf<String, Any>()
    private val injectMethods = mutableListOf<InjectMethod>()

    fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    fun setEnvironment(map: Map<String, Any>) {
        environments.putAll(map)
    }


    fun setEnvironmentMethod(method: InjectMethod) {
        injectMethods.add(method)
    }

    fun setEnvironmentMethod(list: List<InjectMethod>) {
        injectMethods.addAll(list)
    }

    private fun getEnvironmentMethod(key: String, nodes: List<ParseResult.ValueResult<*>>): InjectMethod {
        return injectMethods.find {
            it.name == key && it.isSameMethod(nodes)
        } ?: throw NotDefineMethodException(key)
    }

    private fun getEnvironment(key: String): Any {
        return environments[key] ?: throw RefNotDefineException(key)
    }

    private fun getEnvironmentAsValueResult(key: String): ParseResult.ValueResult<*> {
        return getEnvironment(key).toValueResult()
    }

    //变量池
    private val variables = mutableMapOf<String, Any>()
    private val functions = mutableMapOf<String, Node.Statement.FunctionStatement>()

    fun parse(node: Node) {
        variables.clear()
        functions.clear()

        parseNode(node, emptyList())
    }

    private fun parseStatement(node: Node.Statement, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Statement.FunctionStatement -> {
                functions[node.functionName.name] = node
                ParseResult.NoneResult
            }

            is Node.Statement.InvokeStatement -> {
                parseExpression(node.expression, params)
            }

            is Node.Statement.NewVariableAssignmentStatement -> {
                if (variables.containsKey(node.id.uniqueName())) {
                    throw DulVarDefineException(node.id.name)
                }
                val result = parseNode(node.value, params).toValueResultElseThrow()
                variables[node.id.uniqueName()] = result.value as Any
                ParseResult.NoneResult
            }

            is Node.Statement.VariableAssignmentStatement -> {
                val result = parseNode(node.value, params)
                if (result !is ParseResult.ValueResult<*>) {
                    throw RuntimeException("$result can't be a valid value to ${node.id.name}")
                }
                variables[node.id.uniqueName()] = result.value as Any
                ParseResult.NoneResult
            }

            is Node.Statement.VariablePropertiesAssignmentStatement -> {
                if (node.id !is Node.Expression.IdExpression.IdRef) {
                    throw RuntimeException("variable properties only support inject value")
                }

                val variableName = node.id.name
                val methodName = node.propertiesIdExpression.name
                val parseNode = parseNode(node.value, params)

                val any = getEnvironment(variableName)
                any::class.java.declaredFields.find {
                    it.name == methodName
                }?.let {
                    val sourceAccessible = it.isAccessible
                    it.isAccessible = true
                    parseNode.toValueResultElseThrow().let { value ->
                        it.set(any, value.value)
                    }
                    if (!sourceAccessible) {
                        it.isAccessible = false
                    }
                } ?: throw RefNotDefinePropertiesException(variableName, methodName)
                ParseResult.NoneResult
            }
        }
    }

    private fun parseIdExpression(node: Node.Expression.IdExpression, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression.IdExpression.Id -> {
                params.find {
                    it.paramName() == node.uniqueName()
                }?.parseResult ?: run {
                    if (!variables.containsKey(node.uniqueName())) {
                        throw VarNotDefineException(node.name)
                    }
                    variables[node.uniqueName()]!!.toValueResult()
                }
            }

            is Node.Expression.IdExpression.IdRef -> {
                getEnvironmentAsValueResult(node.name)
            }
        }
    }

    private fun parseTypeExpression(node: Node.Expression.TypeExpression): ParseResult {
        return when (node) {
            is Node.Expression.TypeExpression.FloatType -> ParseResult.ValueResult.FloatValueResult(node.value)
            is Node.Expression.TypeExpression.IntType -> ParseResult.ValueResult.IntValueResult(node.value)
            is Node.Expression.TypeExpression.StringType -> ParseResult.ValueResult.StringValueResult(node.value)
            is Node.Expression.TypeExpression.BooleanType -> ParseResult.ValueResult.BooleanValueResult(node.value)
            is Node.Expression.TypeExpression.ArrayType -> {
                ParseResult.ValueResult.ArrayValueResult(node.value.map {
                    parseTypeExpression(it).toValueResultElseThrow()
                }.toTypedArray())
            }
        }
    }

    private fun parseExpression(node: Node.Expression, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression.IdExpression -> parseIdExpression(node, params)

            is Node.Expression.TypeExpression -> parseTypeExpression(node)

            is Node.Expression.AddSubExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).toValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).toValueResultElseThrow()
                val result = if (node.op == "+") {
                    //+
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                    "Float + Boolean",
                                    node.source
                                )

                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Float + Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                    "Float + Array",
                                    node.source
                                )
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                    "Int + Boolean",
                                    node.source
                                )

                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Int + Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                    "Int + Array",
                                    node.source
                                )
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.FloatValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.IntValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
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
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> leftExpression.value && rightExpression.value
                                is ParseResult.ValueResult.FloatValueResult,
                                is ParseResult.ValueResult.IntValueResult,
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Boolean + Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                    "Boolean + Array",
                                    node.source
                                )
                            }
                        }

                        is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport("Array +", node.source)

                    }
                } else {
                    //-
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                        is ParseResult.ValueResult.BooleanValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> leftExpression.value && rightExpression.value
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

                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.ArrayValueResult -> throw OpNotSupport(
                                    "Boolean - Array",
                                    node.source
                                )
                            }
                        }

                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                    "Float - Boolean",
                                    node.source
                                )

                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Float - Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value - rightExpression.value
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
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                    "Int - Boolean",
                                    node.source
                                )

                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Int - Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value - rightExpression.value
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
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport(
                                    "String - Boolean",
                                    node.source
                                )

                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "String - Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.IntValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.StringValueResult -> leftExpression.value.replace(
                                    rightExpression.value,
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
                val leftExpression = parseExpression(node.leftExpression, params).toValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).toValueResultElseThrow()
                val result = if (node.op == "*") {
                    //+
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Float * Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value * rightExpression.value
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
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> {
                                    if (leftExpression.value <= 0) {
                                        throw OpNotSupport("o or -Int * String", node.source)
                                    }

                                    val string = StringBuilder()
                                    for (l in (0..<leftExpression.value)) {
                                        string.append(rightExpression.value)
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
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport(
                                    "Float * String",
                                    node.source
                                )

                                is ParseResult.ValueResult.IntValueResult -> {
                                    if (rightExpression.value <= 0) {
                                        throw OpNotSupport("o or -Int * String", node.source)
                                    }

                                    val string = StringBuilder()
                                    for (l in (0..<rightExpression.value)) {
                                        string.append(leftExpression.value)
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
                            when (rightExpression) {
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
                } else {
                    //-
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport(
                                    "Float / Any",
                                    node.source
                                )

                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value / rightExpression.value
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
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any", node.source)
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value / rightExpression.value
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
                            when (rightExpression) {
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
                            when (rightExpression) {
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
                val methodIdExpression = node.methodIdExpression.name
                val callParams = node.params

                //internal function
                val functionStatement = functions[methodIdExpression]
                if (functionStatement != null) {
                    if (functionStatement.params.size != callParams.size) {
                        throw RuntimeException("params size not equals")
                    }
                    val nodeParams = callParams.map {
                        parseNode(it, params)
                    }.mapIndexed { index, parseResult ->
                        Param(functionStatement.params[index].name, parseResult)
                    }

                    for (statement in functionStatement.statements) {
                        parseNode(statement, nodeParams)
                    }

                    functionStatement.returnExpression?.let { parseNode(it, nodeParams) } ?: ParseResult.NoneResult
                } else {
                    val nodeParams = callParams.map {
                        parseNode(it, params)
                    }.map {
                        it.toValueResultElseThrow()
                    }
                    val injectMethod = getEnvironmentMethod(methodIdExpression, nodeParams)
                    val parameters = injectMethod.method.parameters
                    val realNodeParams = nodeParams.map {
                        it.value
                    }.mapIndexed { index, any ->
                        any?.toTargetTypeInstance(parameters[index].type)
                    }
                    injectMethod.invokeMethod(*realNodeParams.toTypedArray()).toValueResultElseNone()
                }
            }

            is Node.Expression.ObjectMethodCallExpression -> {
                val callIdExpression = parseNode(node.callIdExpression, params)
                val methodIdExpression = node.methodIdExpression.name
                val callParams = node.params

                return when (callIdExpression) {
                    is ParseResult.ValueResult<*> -> {
                        val value = callIdExpression.value!!
                        val method = value::class.java.declaredMethods.find {
                            it.name == methodIdExpression && it.parameterCount == callParams.size
                        } ?: throw RefNotDefineMethodException(value.toString(), methodIdExpression)
                        val parameters = method.parameters
                        val nodeParams = callParams.map {
                            parseNode(it, params)
                        }.mapIndexed { index, parseResult ->
                            Param(parameters[index].name, parseResult)
                        }.map {
                            it.parseResult.toValueResultElseThrow().value
                        }.mapIndexed { index, it ->
                            it?.toTargetTypeInstance(parameters[index].type)
                        }
                        method.invoke(value, *nodeParams.toTypedArray()).toValueResultElseNone()
                    }

                    ParseResult.NoneResult -> {
                        throw IllStmtException("$callIdExpression can't invoke method")
                    }
//                    is ParseResult.Variable -> {
//                        throw IllStmtException("$callIdExpression can't invoke method")
//                    }
                    ParseResult.OperateResult.Break -> TODO()
                    ParseResult.OperateResult.Continue -> TODO()
                    is ParseResult.OperateResult.Return -> TODO()
                    is ParseResult.Define.ClassDefine -> TODO()
                    is ParseResult.Define.ConstructorDefine -> TODO()
                    is ParseResult.Define.FunctionDefine -> TODO()
                    is ParseResult.Define.Variable -> TODO()
                }
            }

            is Node.Expression.AndExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).toValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).toValueResultElseThrow()

                if (leftExpression is ParseResult.ValueResult.BooleanValueResult && rightExpression is ParseResult.ValueResult.BooleanValueResult) {
                    ParseResult.ValueResult.BooleanValueResult(leftExpression.value && rightExpression.value)
                } else if (leftExpression is ParseResult.ValueResult.IntValueResult && rightExpression is ParseResult.ValueResult.IntValueResult) {
                    val lB = leftExpression.value != 0L
                    val rB = rightExpression.value != 0L

                    ParseResult.ValueResult.BooleanValueResult(lB && rB)
                } else {
                    throw RuntimeException("'&&' left expr and right expr must be boolean or long or int")
                }
            }

            is Node.Expression.EqualsExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).toValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).toValueResultElseThrow()
                ParseResult.ValueResult.BooleanValueResult(leftExpression.value == rightExpression.value)
            }

            is Node.Expression.OrExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).toValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).toValueResultElseThrow()

                if (leftExpression is ParseResult.ValueResult.BooleanValueResult && rightExpression is ParseResult.ValueResult.BooleanValueResult) {
                    ParseResult.ValueResult.BooleanValueResult(leftExpression.value || rightExpression.value)
                } else if (leftExpression is ParseResult.ValueResult.IntValueResult && rightExpression is ParseResult.ValueResult.IntValueResult) {
                    val lB = leftExpression.value != 0L
                    val rB = rightExpression.value != 0L

                    ParseResult.ValueResult.BooleanValueResult(lB || rB)
                } else {
                    throw RuntimeException("'||' left expr and right expr must be boolean or long or int")
                }
            }

            is Node.Expression.IfExpression -> {
                val result = when (val ifCondition = parseNode(node.ifCondition, params)) {
                    is ParseResult.ValueResult.BooleanValueResult -> {
                        ifCondition.value
                    }

                    is ParseResult.ValueResult.IntValueResult -> {
                        ifCondition.value != 0L
                    }

                    else -> {
                        throw RuntimeException("ifCondition must return boolean")
                    }
                }
                if (result) {
                    if (node.ifStatements.isEmpty()) {
                        ParseResult.NoneResult
                    } else if (node.ifStatements.size == 1) {
                        parseNode(node.ifStatements[0], params)
                    } else {
                        for (i in 0..node.ifStatements.size - 2) {
                            parseNode(node.ifStatements[i], params)
                        }
                        parseNode(node.ifStatements[node.ifStatements.size - 1], params)
                    }
                } else {
                    if (node.elseStatements.isEmpty()) {
                        ParseResult.NoneResult
                    } else if (node.elseStatements.size == 1) {
                        parseNode(node.elseStatements[0], params)
                    } else {
                        for (i in 0..node.elseStatements.size - 2) {
                            parseNode(node.elseStatements[i], params)
                        }
                        parseNode(node.elseStatements[node.elseStatements.size - 1], params)
                    }
                }
            }

            is Node.Expression.GetPropertiesExpression -> {
                val left = parseNode(node.expression, params) as ParseResult.ValueResult<*>
//                if (left !is ParseResult.ValueResult.AnyValueResult) {
//                    throw RuntimeException("get properties only support in Any")
//                }

                val name = node.properties.id.name
                val result = (
                        left.value!!::class.java.declaredFields.find {
                            it.name == name
                        }?.let {
                            it.isAccessible = true
                            it
                        }?.get(left.value) ?: throw RefNotDefinePropertiesException(left.value!!::class.java.name, name)
                        ).toValueResult()

                if (node.properties.index == null) {
                    result
                } else {
                    if (result !is ParseResult.ValueResult.ArrayValueResult) {
                        throw RuntimeException("$name not a array")
                    }
                    result.value[node.properties.index.value.toInt()].toValueResult()
                }
            }

            is Node.Expression.ArrayAccessExpression -> {
                val idExpression = parseExpression(node.targetExpression, params).toValueResultElseThrow()
                if (idExpression !is ParseResult.ValueResult.ArrayValueResult) {
                    throw RuntimeException("${node.targetExpression.source} not a array")
                }
                val index = node.index.value.toInt()
                idExpression.value[index].toValueResult()
            }

            is Node.Expression.LoopExpression -> {
                val targetExpression = parseNode(node.targetExpression,params)
                if (targetExpression !is ParseResult.ValueResult.ArrayValueResult){
                    throw RuntimeException("loop target:${node.targetExpression.source} not a array")
                }
                for (valueResult in targetExpression.value) {
                    for (bloakStatement in node.bloakStatements) {
                        parseNode(bloakStatement, listOf(Param(node.item.name,valueResult)))
                    }
                }
                ParseResult.NoneResult
            }
            is Node.Expression.DefineArrayExpression -> {
                val transform = node.items.map {
                    parseNode(it,params).toValueResultElseThrow()
                }.toTypedArray()
                ParseResult.ValueResult.ArrayValueResult(transform)
            }
        }
    }


    private fun parseProperties(node: Node.Properties, params: List<Param>): ParseResult {
        return ParseResult.NoneResult
    }

    private fun parseProgram(node: Node.Program): ParseResult {
        val all = node.nodes.map {
            parseNode(it, emptyList())
        }
        return all.lastOrNull() ?: ParseResult.NoneResult
    }


    private fun parseNode(node: Node, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression -> parseExpression(node, params)
            is Node.Program -> parseProgram(node)
            is Node.Statement -> parseStatement(node, params)
            is Node.Properties.Properties -> parseProperties(node, params)
        }
    }





}