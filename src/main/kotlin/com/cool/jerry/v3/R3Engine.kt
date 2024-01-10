package com.cool.jerry.v3

import com.cool.jerry.bridge.FunctionCallBridge0
import com.cool.jerry.extern.Embed
import com.cool.jerry.model.ExecuteResult
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.model.ParseResult
import com.cool.jerry.model.Result
import com.cool.jerry.version3.RuleLexer
import com.cool.jerry.version3.RuleParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File
import java.nio.charset.Charset

class R3Engine {
    private val environments = mutableMapOf<String, Any>()
    private val environmentMethods = mutableListOf<InjectMethod>()

    fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    fun setEnvironmentMethod(method: InjectMethod) {
        environmentMethods.find {
            it.method.name == method.method.name && it.parameterCount == method.parameterCount &&
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
        r3Parser.setEnvironmentMethod(InjectMethod("currentTime", Embed::class.java.getMethod("currentTime")))
        r3Parser.setEnvironmentMethod(InjectMethod("uuid", Embed::class.java.getMethod("uuid")))
        r3Parser.setEnvironmentMethod(InjectMethod("sleep", Embed::class.java.getMethod("sleep",Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("randomInt", Embed::class.java.getMethod("randomInt", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("toString", Embed::class.java.getMethod("toString", Any::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("currentThread", Embed::class.java.getMethod("currentThread")))
        r3Parser.setEnvironmentMethod(InjectMethod("thread", Embed::class.java.getMethod("thread",FunctionCallBridge0::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("get", Embed::class.java.getMethod("get",String::class.java)))

        //注入常用变量
        r3Parser.setEnvironment("MAX_INT",Int.MAX_VALUE)
        r3Parser.setEnvironment("MIN_INT",Int.MIN_VALUE)

        r3Parser.setEnvironment(environments)
        r3Parser.setEnvironmentMethod(environmentMethods)
    }

    fun execute(file:File):ExecuteResult{
        if (!file.exists()){
            throw RuntimeException("file:${file.absolutePath} not exists")
        }
        val readText = file.readText(Charset.defaultCharset())
        return execute(readText)
    }

    fun execute(rule: String): ExecuteResult {
        val visit = visit(rule.trim())
        val r3Parser = R3Parser()
        initR3(r3Parser)
        val result = r3Parser.parse(visit, mutableListOf(), mutableListOf(), mutableListOf()).getResult()
        return ExecuteResult(visit, Result(visit,result))
    }

    private fun visit(rule: String): R3Node {
        val cs = CharStreams.fromString(rule)
        val lexer = RuleLexer(cs)
        val tokens = CommonTokenStream(lexer)
        val visitor = RuleParserVisitorImpl()
        val parser = RuleParser(tokens)
        parser.errorHandler = ErrorHandler()
        return visitor.visit(parser.program())
    }

    private fun ParseResult.getResult():Any?{
        return when(this){
            is ParseResult.Define.ClassDefine -> null
            is ParseResult.Define.ConstructorDefine -> null
            is ParseResult.Define.FunctionDefine -> null
            is ParseResult.Define.Variable -> null
            ParseResult.NoneResult -> null
            ParseResult.OperateResult.Break -> null
            ParseResult.OperateResult.Continue -> null
            is ParseResult.OperateResult.Return -> value?.getResult()
            is ParseResult.ValueResult.AnyValueResult -> value
            is ParseResult.ValueResult.ArrayValueResult -> value
            is ParseResult.ValueResult.BooleanValueResult -> value
            is ParseResult.ValueResult.FloatValueResult -> value
            is ParseResult.ValueResult.IntValueResult -> value
            is ParseResult.ValueResult.StringValueResult -> value
            is ParseResult.ValueResult.RangeValueResult -> value
        }
    }
}

