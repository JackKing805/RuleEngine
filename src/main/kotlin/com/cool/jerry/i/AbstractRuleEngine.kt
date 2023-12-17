package com.cool.jerry.i

import com.cool.jerry.g4.RtRuleEngine2BaseVisitor
import com.cool.jerry.g4.RtRuleEngine2Lexer
import com.cool.jerry.g4.RtRuleEngine2Parser
import com.cool.jerry.i.define.Define
import com.cool.jerry.rt_engine.RtRuleEngine
import com.cool.jerry.i.define.Node
import com.example.testsensor.rule.RuleEngine
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream


abstract  class AbstractRuleEngine: RuleEngine, RtRuleEngine2BaseVisitor<Node>() {
    private val environments = mutableMapOf<String,Any>()

    override fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    override fun getEnvironment(key: String): Any {
        return environments[key]?:NullPointerException("not found value at environment")
    }


    override fun visitNewVariableAssignmentStatement(ctx: RtRuleEngine2Parser.NewVariableAssignmentStatementContext): Node {
        val idDefine = if (ctx.variable().ID()!=null){
            Define.IdDefine(ctx.variable().ID().text)
        }else{
            Define.RefIdDefine(ctx.variable().ID_REF().text)
        }
        val valueDeclaration = visit(ctx.expression())
        return Node.Statement.AssignmentStatement(idDefine, valueDeclaration)
    }

    override fun visitBlockExpression(ctx: RtRuleEngine2Parser.BlockExpressionContext): Node {
        return visit(ctx.expression())
    }
}

fun main() {
    val program = """
        val a = 1
    """.trimIndent()

    val cs = CharStreams.fromString(program)
    val lexer =  RtRuleEngine2Lexer(cs)
    val tokens =  CommonTokenStream(lexer)
    val parser =  RtRuleEngine2Parser(tokens)
    val context = parser.program()
    val visitor =  RtRuleEngine()
    visitor.visit(context)
}



