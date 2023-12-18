package com.cool.jerry.rt_engine

import com.cool.jerry.g4.RtRuleEngine2BaseVisitor
import com.cool.jerry.g4.RtRuleEngine2Parser
import com.cool.jerry.rt_engine.define.Node
import org.antlr.v4.runtime.tree.TerminalNode

class RtRuleEngine2VisitorImpl: RtRuleEngine2BaseVisitor<Node>() {
    override fun visitProgram(ctx: RtRuleEngine2Parser.ProgramContext): Node {
        return ctx.statement().map {
            visit(it)
        }.let {
            Node.Program(it,ctx.text)
        }
    }

    /**
     * val a = $context
     * val a = $context.getA()
     * val a = 1
     * val a = 1.1
     * val a = "aaaa"
     * val a = getA()
     * val a = 1+1
     * val a = 1+1.1
     * val a = 1+"ASD"
     */
    override fun visitNewVariableAssignmentStatement(ctx: RtRuleEngine2Parser.NewVariableAssignmentStatementContext): Node {
        val idDefine = visit(ctx.variable()) as Node.Expression.IdExpression
        val valueDeclaration = visit(ctx.expression())
        return Node.Statement.NewVariableAssignmentStatement(idDefine, valueDeclaration,ctx.text)
    }

    /**
     *  a = $context
     *  a = $context.getA()
     *  a = 1
     *  a = 1.1
     *  a = "aaaa"
     *  a = getA()
     *  a = 1+1
     *  a = 1+1.1
     *  a = 1+"ASD"
     */
    override fun visitVariableAssignmentStatement(ctx: RtRuleEngine2Parser.VariableAssignmentStatementContext): Node {
        val idDefine = visit(ctx.variable()) as Node.Expression.IdExpression
        val valueDeclaration = visit(ctx.expression())
        return Node.Statement.VariableAssignmentStatement(idDefine, valueDeclaration,ctx.text)
    }

    override fun visitVariablePropertiesAssignmentStatement(ctx: RtRuleEngine2Parser.VariablePropertiesAssignmentStatementContext): Node {
        val idDefine = visit(ctx.variable()) as Node.Expression.IdExpression
        val methodNameDefine = ctx.properties().ID().toIdExpression() as Node.Expression.IdExpression.Id
        val valueDeclaration = visit(ctx.expression())
        return Node.Statement.VariablePropertiesAssignmentStatement(idDefine,methodNameDefine,valueDeclaration,ctx.text)
    }

    override fun visitInvokeStatement(ctx: RtRuleEngine2Parser.InvokeStatementContext): Node {
        return Node.Statement.InvokeStatement(visit(ctx.expression()) as Node.Expression,ctx.text)
    }

    override fun visitFunctionStatement(ctx: RtRuleEngine2Parser.FunctionStatementContext): Node {
        val functionNameDefine = ctx.method().ID().toIdExpression() as Node.Expression.IdExpression.Id
        val params = mutableListOf<Node.Expression.IdExpression.Id>()
        for (terminalNode in ctx.param()) {
            params.add(Node.Expression.IdExpression.Id(terminalNode.text,ctx.text))
        }
        val statements = ctx.statement().map {
            visit(it)
        }
        val returnExpression = visit(ctx.expression())
        return Node.Statement.FunctionStatement(functionNameDefine,params,statements,returnExpression,ctx.text)
    }

    override fun visitMethodCallExpression(ctx: RtRuleEngine2Parser.MethodCallExpressionContext): Node {
        val callExpression = ctx.variable()?.let {
            visit(it) as Node.Expression.IdExpression
        }
        val methodIdExpression = ctx.method().ID().toIdExpression() as Node.Expression.IdExpression.Id

        val params = mutableListOf<Node>()
        for (expressionContext in ctx.expression()) {
            visit(expressionContext).let {
                params.add(it)
            }
        }

        return Node.Expression.MethodCallExpression(callExpression,methodIdExpression,params,ctx.text)
    }

    override fun visitVariable(ctx: RtRuleEngine2Parser.VariableContext): Node {
        return if (ctx.ID()!=null){
             Node.Expression.IdExpression.Id(ctx.ID().text,ctx.text)
        }else{
             Node.Expression.IdExpression.IdRef(ctx.ID_REF().text.removePrefix("@"),ctx.text)
        }
    }

    override fun visitMulDivExpression(ctx: RtRuleEngine2Parser.MulDivExpressionContext): Node {
        val expression = ctx.expression()
        val left = visit(expression[0]) as Node.Expression
        val right = visit(expression[1]) as Node.Expression

        val op = ctx.MUL()?:ctx.DIV()!!

        return Node.Expression.MulDivExpression(
            op.text,
            left,
            right,
            ctx.text
        )
    }

    override fun visitAddSubExpression(ctx: RtRuleEngine2Parser.AddSubExpressionContext): Node {
        val expression = ctx.expression()
        val left = visit(expression[0]) as Node.Expression
        val right = visit(expression[1]) as Node.Expression

        val op = ctx.ADD()?:ctx.SUB()!!

        return Node.Expression.AddSubExpression(
            op.text,
            left,
            right,
            ctx.text
        )
    }

    override fun visitProperties(ctx: RtRuleEngine2Parser.PropertiesContext): Node {
        return visit(ctx.ID())
    }

    /**
     * (aas)
     */
    override fun visitBlockExpression(ctx: RtRuleEngine2Parser.BlockExpressionContext): Node {
        return visit(ctx.expression())
    }

    /**
     * 1
     */
    override fun visitIntExpression(ctx: RtRuleEngine2Parser.IntExpressionContext): Node {
        return Node.Expression.TypeExpression.IntType(ctx.INT().text.toLong(),ctx.text)
    }

    /**
     * "asdas"
     */
    override fun visitStringExpression(ctx: RtRuleEngine2Parser.StringExpressionContext): Node {
        if (ctx.text.startsWith("\'")){
            return Node.Expression.TypeExpression.StringType(ctx.text.removePrefix("\'").removeSuffix("\'"),ctx.text)
        }
        return Node.Expression.TypeExpression.StringType(ctx.text.removePrefix("\"").removeSuffix("\""),ctx.text)
    }

    /**
     * 1.1
     */
    override fun visitFloatExpression(ctx: RtRuleEngine2Parser.FloatExpressionContext): Node {
        return Node.Expression.TypeExpression.FloatType(ctx.FLOAT().text.toDouble(),ctx.text)
    }

    /**
     * Id_9
     */
    override fun visitIdExpression(ctx: RtRuleEngine2Parser.IdExpressionContext): Node {
        return ctx.ID().toIdExpression()
    }

    /**
     * @Id_9
     */
    override fun visitIdRefExpression(ctx: RtRuleEngine2Parser.IdRefExpressionContext): Node {
        return ctx.ID_REF().toIdExpression()
    }

    private fun TerminalNode.toIdExpression():Node.Expression.IdExpression{
        return if (text.startsWith("@")){
             Node.Expression.IdExpression.IdRef(text.removePrefix("@"),text)
        }else{
             Node.Expression.IdExpression.Id(text,text)
        }
    }
}