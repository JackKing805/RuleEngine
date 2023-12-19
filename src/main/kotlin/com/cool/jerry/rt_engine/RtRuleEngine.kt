package com.cool.jerry.rt_engine

import com.cool.jerry.g4.RtRuleEngine2Lexer
import com.cool.jerry.g4.RtRuleEngine2Parser
import com.cool.jerry.i.InjectMethod
import com.cool.jerry.i.RuleEngine
import com.cool.jerry.rt_engine.define.Node
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream


class RtRuleEngine: RuleEngine {
    private val environments = mutableMapOf<String,Any>()
    private val environmentMethods = mutableListOf<InjectMethod>()

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

