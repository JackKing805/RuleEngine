package com.cool.jerry.v3

import com.cool.jerry.extern.Embed
import com.cool.jerry.model.InjectMethod
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
        r3Parser.setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log", String::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log", Int::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log", Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log", Float::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log", Double::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("currentTimestamp", Embed::class.java.getMethod("currentTimestamp")))
        r3Parser.setEnvironmentMethod(InjectMethod("uuid", Embed::class.java.getMethod("uuid")))
        r3Parser.setEnvironmentMethod(InjectMethod("sleep", Embed::class.java.getMethod("sleep",Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("randomInt", Embed::class.java.getMethod("randomInt", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("toString", Embed::class.java.getMethod("toString", Any::class.java)))


        //注入常用变量
        r3Parser.setEnvironment("MAX_INT",Int.MAX_VALUE)
        r3Parser.setEnvironment("MIN_INT",Int.MIN_VALUE)


        r3Parser.setEnvironment(environments)
        r3Parser.setEnvironmentMethod(environmentMethods)
    }

    fun execute(rule: String): R3Node {
        val visit = visit(rule)
        val r3Parser = R3Parser()
        initR3(r3Parser)
        r3Parser.parse(visit, mutableListOf(), mutableListOf(), mutableListOf())
        return visit
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

