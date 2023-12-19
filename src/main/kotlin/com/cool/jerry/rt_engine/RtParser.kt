package com.cool.jerry.rt_engine

import com.cool.jerry.i.InjectMethod
import com.cool.jerry.rt_engine.define.Node
import com.cool.jerry.rt_engine.exception.*
import java.lang.StringBuilder
import java.lang.reflect.Method
import java.lang.reflect.Type
import kotlin.RuntimeException
import kotlin.math.round

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

    private fun getEnvironmentMethod(key: String,nodes:List<ParseResult.ValueResult<*>>): InjectMethod {
        return injectMethods.find {
            it.name == key && it.isSameMethod(nodes)
        }?: throw NotDefineMethodException(key)
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
                val result = parseNode(node.value, params).getValueResultElseThrow()
                variables[node.id.uniqueName()] = result.value as Any
                ParseResult.Variable(node.id.name,result)
            }

            is Node.Statement.VariableAssignmentStatement -> {
                val result = parseNode(node.value, params)
                if (result !is ParseResult.ValueResult<*>){
                    throw RuntimeException("$result can't be a valid value to ${node.id.name}")
                }
                variables[node.id.uniqueName()] = result.value as Any
                ParseResult.Variable(node.id.name, result)
            }

            is Node.Statement.VariablePropertiesAssignmentStatement -> {
                if (node.id !is Node.Expression.IdExpression.IdRef){
                    throw RuntimeException("variable properties only support inject value")
                }

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
                ParseResult.NoneResult
            }

            is Node.Statement.IfStatement -> {
                val ifCondition = parseNode(node.ifCondition,params)
                if (ifCondition !is ParseResult.ValueResult.BooleanValueResult){
                    throw RuntimeException("ifCondition must return boolean")
                }
                if (ifCondition.value){
                    for (ifStatement in node.ifStatements) {
                        parseNode(ifStatement,params)
                    }
                }else{
                    for (elseStatement in node.elseStatements) {
                        parseNode(elseStatement,params)
                    }
                }

                ParseResult.NoneResult
            }
        }
    }

    private fun parseIdExpression(node: Node.Expression.IdExpression, params: List<Param>): ParseResult {
        return when (node) {
            is Node.Expression.IdExpression.Id -> {
                params.find {
                    it.name == node.uniqueName()
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
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Float + Boolean")
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Float + Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Int + Boolean")
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Int + Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value + rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.FloatValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.IntValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("String + Boolean")
                            }
                        }
                        is ParseResult.ValueResult.BooleanValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> leftExpression.value && rightExpression.value
                                is ParseResult.ValueResult.FloatValueResult,
                                is ParseResult.ValueResult.IntValueResult,
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Boolean + Any")
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }
                    }
                } else {
                    //-
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                        is ParseResult.ValueResult.BooleanValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> leftExpression.value && rightExpression.value
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Boolean - Any")
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport("Boolean - Float")
                                is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport("Boolean - Int")
                                is ParseResult.ValueResult.StringValueResult -> "${leftExpression.value}${rightExpression.value}"
                            }
                        }
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Float - Boolean")
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Float - Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Float - String")
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Int - Boolean")
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Int - Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value - rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Int - String")
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("String - Boolean")
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("String - Any")
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
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Float * Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value * rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Float * String")
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Float * Boolean")
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

                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Int * Boolean")
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
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("String * Boolean")
                            }
                        }

                        is ParseResult.ValueResult.BooleanValueResult -> {
                            when(rightExpression){
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Boolean * Any")
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Boolean * Boolean")
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport("Boolean * Float")
                                is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport("Boolean * Int")
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Boolean * String")
                            }
                        }
                    }
                } else {
                    //-
                    when (leftExpression) {
                        is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                        is ParseResult.ValueResult.FloatValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Float / Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Float / String")
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Float / Boolean")
                            }
                        }

                        is ParseResult.ValueResult.IntValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.IntValueResult -> leftExpression.value / rightExpression.value
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("String / String")
                                is ParseResult.ValueResult.BooleanValueResult ->  throw OpNotSupport("Int / Boolean")
                            }
                        }

                        is ParseResult.ValueResult.StringValueResult -> {
                            when (rightExpression) {
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Any")
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport("String / Float")
                                is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport("String / Int")
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("String / String")
                                is ParseResult.ValueResult.BooleanValueResult ->  throw OpNotSupport("String / Boolean")
                            }
                        }

                        is ParseResult.ValueResult.BooleanValueResult -> {
                            when(rightExpression){
                                is ParseResult.ValueResult.AnyValueResult -> throw OpNotSupport("Boolean / Any")
                                is ParseResult.ValueResult.BooleanValueResult -> throw OpNotSupport("Boolean / Boolean")
                                is ParseResult.ValueResult.FloatValueResult -> throw OpNotSupport("Boolean / Float")
                                is ParseResult.ValueResult.IntValueResult -> throw OpNotSupport("Boolean / Int")
                                is ParseResult.ValueResult.StringValueResult -> throw OpNotSupport("Boolean / String")
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
                        it.getValueResultElseThrow()
                    }
                    val injectMethod = getEnvironmentMethod(methodIdExpression,nodeParams)
                    val parameters = injectMethod.method.parameters
                    val realNodeParams = nodeParams.map {
                        it.value
                    }.mapIndexed { index, any ->
                        any?.toTargetTypeIns(parameters[index].type)
                    }
                    injectMethod.invokeMethod(*realNodeParams.toTypedArray()).toValueResultElseNone()
                }
            }

            is Node.Expression.ObjectMethodCallExpression -> {
                val callIdExpression = parseNode(node.callIdExpression,params)
                val methodIdExpression = node.methodIdExpression.name
                val callParams = node.params

                return when(callIdExpression){
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
                            it.parseResult.getValueResultElseThrow().value
                        }.mapIndexed { index, it ->
                            it?.toTargetTypeIns(parameters[index].type)
                        }
                        method.invoke(value, *nodeParams.toTypedArray()).toValueResultElseNone()
                    }
                    ParseResult.NoneResult,
                    is ParseResult.Variable -> {
                        throw IllStmtException("$callIdExpression can't invoke method")
                    }
                }
            }

            is Node.Expression.AndExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).getValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).getValueResultElseThrow()

                if (leftExpression !is ParseResult.ValueResult.BooleanValueResult || rightExpression !is ParseResult.ValueResult.BooleanValueResult){
                    throw RuntimeException("'==' left expr and right expr must boolean")
                }
                ParseResult.ValueResult.BooleanValueResult(leftExpression.value && rightExpression.value)
            }
            is Node.Expression.EqualsExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).getValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).getValueResultElseThrow()
                ParseResult.ValueResult.BooleanValueResult(leftExpression.value == rightExpression.value)
            }
            is Node.Expression.OrExpression -> {
                val leftExpression = parseExpression(node.leftExpression, params).getValueResultElseThrow()
                val rightExpression = parseExpression(node.rightExpression, params).getValueResultElseThrow()

                if (leftExpression !is ParseResult.ValueResult.BooleanValueResult || rightExpression !is ParseResult.ValueResult.BooleanValueResult){
                    throw RuntimeException("'==' left expr and right expr must boolean")
                }
                ParseResult.ValueResult.BooleanValueResult(leftExpression.value || rightExpression.value)
            }
        }
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
            is ParseResult.ValueResult.BooleanValueResult -> this
        }
    }

    private fun Any?.toValueResultElseNone():ParseResult{
        return if (this is Unit || this == null) {
            ParseResult.NoneResult
        } else {
            this.toValueResult()
        }
    }

    private fun Any.toValueResult(): ParseResult.ValueResult<*> {
        return when (this) {
            is Int,
            is Long -> {
                ParseResult.ValueResult.IntValueResult(this as Long)
            }

            is String -> {
                ParseResult.ValueResult.StringValueResult(this)
            }

            is Float,
            is Double -> {
                ParseResult.ValueResult.FloatValueResult(this as Double)
            }

            is Boolean->{
                ParseResult.ValueResult.BooleanValueResult(this)
            }

            else -> {
                ParseResult.ValueResult.AnyValueResult(this)
            }
        }
    }

    private fun Any.toTargetTypeIns(type:Class<*>):Any{
        return when(type){
            Int::class.java->{
                try {
                    this as Int
                }catch (e:ClassCastException){
                    this.toString().toInt()
                }
            }
            Long::class.java->{
                try {
                    this as Long
                }catch (e:ClassCastException){
                    this.toString().toLong()
                }
            }
            String::class.java->{
                this as String
            }
            Boolean::class.java->{
                this as Boolean
            }
            Double::class.java->{
                try {
                    this as Double
                }catch (e:ClassCastException){
                    this.toString().toDouble()
                }
            }
            Float::class.java->{
                try {
                    this as Float
                }catch (e:ClassCastException){
                    this.toString().toFloat()
                }
            }
            Short::class.java->{
                try {
                    this as Short
                }catch (e:ClassCastException){
                    this.toString().toShort()
                }
            }
            Byte::class.java->{
                this as Byte
            }
            ByteArray::class.java->{
                this as ByteArray
            }
            else-> this
        }
    }

    sealed class ParseResult {
        sealed class ValueResult<T>(open val value: T) : ParseResult() {
            data class IntValueResult(override val value: Long) : ValueResult<Long>(value)
            data class StringValueResult(override val value: String) : ValueResult<String>(value)
            data class FloatValueResult(override val value: Double) : ValueResult<Double>(value)
            data class BooleanValueResult(override val value: Boolean) : ValueResult<Boolean>(value)
            data class AnyValueResult(override val value: Any) : ValueResult<Any>(value)
        }

        data object NoneResult : ParseResult()

        class Variable(val name: String, val value: ValueResult<*>) : ParseResult()
    }

    data class Param(
        val name: String,
        val parseResult: ParseResult
    )
}