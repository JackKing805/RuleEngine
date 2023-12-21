package com.cool.jerry.rt_engine

import com.cool.jerry.g4.RtRuleEngine2Lexer
import com.cool.jerry.g4.RtRuleEngine2Parser
import com.cool.jerry.i.InjectMethod
import com.cool.jerry.i.RuleEngine
import com.cool.jerry.rt_engine.define.Node
import com.cool.jerry.rt_engine.utils.Embed
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream


class RtRuleEngine: RuleEngine {


    private val environments = mutableMapOf<String,Any>()
    private val environmentMethods = mutableListOf<InjectMethod>()


    init {
        setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log",String::class.java)))
        setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log",Int::class.java)))
        setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log",Long::class.java)))
        setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log",Boolean::class.java)))
        setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log",Float::class.java)))
        setEnvironmentMethod(InjectMethod("log", Embed::class.java.getMethod("log",Double::class.java)))
        setEnvironmentMethod(InjectMethod("currentTimestamp", Embed::class.java.getMethod("currentTimestamp")))
        setEnvironmentMethod(InjectMethod("uuid", Embed::class.java.getMethod("uuid")))
        setEnvironmentMethod(InjectMethod("randomInt", Embed::class.java.getMethod("randomInt",Boolean::class.java)))
        setEnvironmentMethod(InjectMethod("toString", Embed::class.java.getMethod("toString",Any::class.java)))
    }


    override fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    override fun setEnvironmentMethod(method: InjectMethod) {
        environmentMethods.find {
            it.method.name == method.method.name && it.method.parameterCount == method.method.parameterCount &&
                    it.method.parameters.zip(method.method.parameters)
                        .all { pair->
                            pair.first.type == pair.second.type
                        }
        }?.let {
            throw RuntimeException("method:${method.name} already define")
        }

        environmentMethods.add(method)
    }

    override fun execute(rule: String): Node.Program {
        val parser = RtParser()
        parser.setEnvironment(environments)
        parser.setEnvironmentMethod(environmentMethods)

        val program = visit(rule) as Node.Program
        parser.parse(program)

        return program
    }

    private fun visit(rule:String):Node{
        val cs = CharStreams.fromString(rule)
        val lexer =  RtRuleEngine2Lexer(cs)
        val tokens =  CommonTokenStream(lexer)
        val visitor = RtRuleEngine2VisitorImpl()
        val parser =  RtRuleEngine2Parser(tokens)
        return visitor.visit(parser.program())
    }
}

