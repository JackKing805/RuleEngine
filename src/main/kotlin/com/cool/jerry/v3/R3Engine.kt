package com.cool.jerry.v3

import com.cool.jerry.extern.Embed
import com.cool.jerry.model.ExecuteResult
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.model.ParseResult
import com.cool.jerry.version3.RuleLexer
import com.cool.jerry.version3.RuleParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class R3Engine {
    private val environments = mutableMapOf<String, Any>()
    private val environmentMethods = mutableListOf<InjectMethod>()

    fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    fun setEnvironmentMethod(method: InjectMethod) {
        environmentMethods.find {
            it.method.name == method.method.name && it.method.parameterCount == method.method.parameterCount &&
                    it.method.parameters.zip(method.method.parameters)
                        .all { pair ->
                            pair.first.type == pair.second.type
                        }
        }?.let {
            throw RuntimeException("method:${method.name} already define")
        }

        environmentMethods.add(method)
    }

    private fun initR3(r3Parser: R3Parser) {
        r3Parser.reset()

        //注入常用方法
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", String::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Int::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Float::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Double::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", String::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Int::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Float::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Double::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("currentTimestamp", Embed::class.java.getMethod("currentTime")))
        r3Parser.setEnvironmentMethod(InjectMethod("uuid", Embed::class.java.getMethod("uuid")))
        r3Parser.setEnvironmentMethod(InjectMethod("sleep", Embed::class.java.getMethod("sleep",Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("randomInt", Embed::class.java.getMethod("randomInt", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("toString", Embed::class.java.getMethod("toString", Any::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("currentThread", Embed::class.java.getMethod("currentThread")))


        //注入常用变量
        r3Parser.setEnvironment("MAX_INT",Int.MAX_VALUE)
        r3Parser.setEnvironment("MIN_INT",Int.MIN_VALUE)


        r3Parser.setEnvironment(environments)
        r3Parser.setEnvironmentMethod(environmentMethods)
    }

    suspend fun execute(rule: String): ExecuteResult {
        val visit = visit(rule)
        val r3Parser = R3Parser()
        initR3(r3Parser)
        val result = when(val parseResult = r3Parser.parse(visit, mutableListOf(), mutableListOf(), mutableListOf())){
            is ParseResult.Define.ClassDefine -> null
            is ParseResult.Define.ConstructorDefine -> null
            is ParseResult.Define.FunctionDefine -> null
            is ParseResult.Define.Variable -> null
            ParseResult.NoneResult -> null
            ParseResult.OperateResult.Break -> null
            ParseResult.OperateResult.Continue -> null
            is ParseResult.OperateResult.Return -> parseResult.value!!.value
            is ParseResult.ValueResult.AnyValueResult -> parseResult.value
            is ParseResult.ValueResult.ArrayValueResult -> parseResult.value
            is ParseResult.ValueResult.BooleanValueResult -> parseResult.value
            is ParseResult.ValueResult.FloatValueResult -> parseResult.value
            is ParseResult.ValueResult.IntValueResult -> parseResult.value
            is ParseResult.ValueResult.StringValueResult -> parseResult.value
        }
        return ExecuteResult(visit,result)
    }

    private fun visit(rule: String): R3Node {
        val cs = CharStreams.fromString(rule)
        val lexer = RuleLexer(cs)
        val tokens = CommonTokenStream(lexer)
        val visitor = RuleParserVisitorImpl()
        val parser = RuleParser(tokens)
        return visitor.visit(parser.program())
    }
}

