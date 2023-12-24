package com.cool.jerry.v3

import com.cool.jerry.version3.RuleLexer
import com.cool.jerry.version3.RuleParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class R3Engine {
    fun execute(rule: String):R3Node {
        return visit(rule)
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

