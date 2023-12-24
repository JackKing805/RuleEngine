package com.cool.jerry.v2.rt_engine

import com.cool.jerry.g4.RtRuleEngine2BaseVisitor
import com.cool.jerry.g4.RtRuleEngine2Parser
import com.cool.jerry.v2.rt_engine.define.Node
import org.antlr.v4.runtime.tree.TerminalNode

class RtRuleEngine2VisitorImpl : RtRuleEngine2BaseVisitor<Node>() {
    override fun visitProgram(ctx: RtRuleEngine2Parser.ProgramContext): Node {
        return ctx.statement().map {
            visit(it)
        }.let { it ->
            val sorted = it.sortedBy {
                it !is Node.Statement.FunctionStatement
            }
            Node.Program(sorted, ctx.text)
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
        return Node.Statement.NewVariableAssignmentStatement(idDefine, valueDeclaration, ctx.text)
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
        return Node.Statement.VariableAssignmentStatement(idDefine, valueDeclaration, ctx.text)
    }

    override fun visitVariablePropertiesAssignmentStatement(ctx: RtRuleEngine2Parser.VariablePropertiesAssignmentStatementContext): Node {
        val idDefine = visit(ctx.variable()) as Node.Expression.IdExpression
        val methodNameDefine = ctx.properties().ID().toIdExpression() as Node.Expression.IdExpression.Id
        val valueDeclaration = visit(ctx.expression())
        return Node.Statement.VariablePropertiesAssignmentStatement(
            idDefine,
            methodNameDefine,
            valueDeclaration,
            ctx.text
        )
    }

    override fun visitInvokeStatement(ctx: RtRuleEngine2Parser.InvokeStatementContext): Node {
        return Node.Statement.InvokeStatement(visit(ctx.expression()) as Node.Expression, ctx.text)
    }

    override fun visitFunctionStatement(ctx: RtRuleEngine2Parser.FunctionStatementContext): Node {
        val functionNameDefine = ctx.method().ID().toIdExpression() as Node.Expression.IdExpression.Id
        val params = mutableListOf<Node.Expression.IdExpression.Id>()
        for (terminalNode in ctx.param()) {
            params.add(Node.Expression.IdExpression.Id(terminalNode.text, ctx.text))
        }
        val statements = ctx.statement().map {
            visit(it)
        }
        val returnExpression = ctx.expression()?.let {
            visit(it)
        }
        return Node.Statement.FunctionStatement(functionNameDefine, params, statements, returnExpression, ctx.text)
    }

    override fun visitGetPropertiesExpression(ctx: RtRuleEngine2Parser.GetPropertiesExpressionContext): Node {
        val expression = visit(ctx.expression()) as Node.Expression
        val properties = visit(ctx.properties()) as Node.Properties.Properties
        return Node.Expression.GetPropertiesExpression(
            expression,
            properties,
            ctx.text
        )
    }

    override fun visitIfExpression(ctx: RtRuleEngine2Parser.IfExpressionContext): Node {
        val condition = visit(ctx.expression()) as Node.Expression
        val ifStatements = ctx.ifStatment()?.statement()?.map {
            visit(it) as Node.Statement
        }?: emptyList()

        val elseStatements = ctx.elseStatment()?.statement()?.map {
            visit(it) as Node.Statement
        }?: emptyList()

        return Node.Expression.IfExpression(
            condition,
            ifStatements,
            elseStatements,
            ctx.text
        )
    }

    override fun visitObjectMethodCallExpression(ctx: RtRuleEngine2Parser.ObjectMethodCallExpressionContext): Node {
        val expression = ctx.expression()
        val callExpression = visit(expression[0])
        val methodIdExpression = ctx.method().ID().toIdExpression() as Node.Expression.IdExpression.Id

        val params = mutableListOf<Node>()
        if (ctx.expression().size>1){
            for ((index,expressionContext) in ctx.expression().withIndex()) {
                if (index>0){
                    visit(expressionContext).let {
                        params.add(it)
                    }
                }
            }
        }

        return Node.Expression.ObjectMethodCallExpression(callExpression, methodIdExpression, params, ctx.text)
    }

    override fun visitMethodCallExpression(ctx: RtRuleEngine2Parser.MethodCallExpressionContext): Node {
        val methodIdExpression = ctx.method().ID().toIdExpression() as Node.Expression.IdExpression.Id
        val params = mutableListOf<Node>()
        for (expressionContext in ctx.expression()) {
            visit(expressionContext).let {
                params.add(it)
            }
        }

        return Node.Expression.MethodCallExpression(methodIdExpression, params, ctx.text)
    }

    override fun visitLoopExpression(ctx: RtRuleEngine2Parser.LoopExpressionContext): Node {
        val expression = visit(ctx.expression()) as Node.Expression
        val id = ctx.ID().toIdExpression() as Node.Expression.IdExpression.Id
        val bloakStatement = ctx.bloakStatment()?.statement()?.map {
            visit(it) as Node.Statement
        }?: emptyList()

        return Node.Expression.LoopExpression(
            expression,
            id,
            bloakStatement,
            ctx.text
        )
    }

    override fun visitArrayAccessExpression(ctx: RtRuleEngine2Parser.ArrayAccessExpressionContext): Node {
        val id = visit(ctx.expression()) as Node.Expression
        val index = Node.Expression.TypeExpression.IntType(ctx.INT().text.toLong(), ctx.text)
        return Node.Expression.ArrayAccessExpression(
            id,
            index,
            ctx.text
        )
    }

    override fun visitDefineArrayExpression(ctx: RtRuleEngine2Parser.DefineArrayExpressionContext): Node {
        return visit(ctx.defineArray())
    }

    override fun visitDefineArray(ctx: RtRuleEngine2Parser.DefineArrayContext): Node {
        val items = ctx.expression().map {
            visit(it) as Node.Expression
        }
        return Node.Expression.DefineArrayExpression(
            items,
            ctx.text
        )
    }

    override fun visitVariable(ctx: RtRuleEngine2Parser.VariableContext): Node {
        return if (ctx.ID() != null) {
            ctx.ID().toIdExpression()
        } else {
            ctx.ID_REF().toIdExpression()
        }
    }

    override fun visitMulDivExpression(ctx: RtRuleEngine2Parser.MulDivExpressionContext): Node {
        val expression = ctx.expression()
        val left = visit(expression[0]) as Node.Expression
        val right = visit(expression[1]) as Node.Expression

        val op = ctx.MUL() ?: ctx.DIV()!!

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

        val op = ctx.ADD() ?: ctx.SUB()!!

        return Node.Expression.AddSubExpression(
            op.text,
            left,
            right,
            ctx.text
        )
    }

    override fun visitProperties(ctx: RtRuleEngine2Parser.PropertiesContext): Node {
        val id = ctx.ID().toIdExpression() as Node.Expression.IdExpression.Id
        val index = ctx.INT()?.let {
            Node.Expression.TypeExpression.IntType(it.text.toLong(),it.text)
        }
        return Node.Properties.Properties(
            id,
            index,
            ctx.text
        )
    }

    /**
     * 1
     */
    override fun visitIntExpression(ctx: RtRuleEngine2Parser.IntExpressionContext): Node {
        return Node.Expression.TypeExpression.IntType(ctx.INT().text.toLong(), ctx.text)
    }

    /**
     * "asdas"
     */
    override fun visitStringExpression(ctx: RtRuleEngine2Parser.StringExpressionContext): Node {
        if (ctx.text.startsWith("\'")) {
            return Node.Expression.TypeExpression.StringType(ctx.text.removePrefix("\'").removeSuffix("\'"), ctx.text)
        }
        return Node.Expression.TypeExpression.StringType(ctx.text.removePrefix("\"").removeSuffix("\""), ctx.text)
    }

    /**
     * 1.1
     */
    override fun visitFloatExpression(ctx: RtRuleEngine2Parser.FloatExpressionContext): Node {
        return Node.Expression.TypeExpression.FloatType(ctx.FLOAT().text.toDouble(), ctx.text)
    }

    /**
     * true | false
     */
    override fun visitBooleanExpression(ctx: RtRuleEngine2Parser.BooleanExpressionContext): Node {
        val boole = ctx.BOOLEAN().text.lowercase().takeIf {
            it=="true"
        }?.let {
            true
        }?:false
        return Node.Expression.TypeExpression.BooleanType(boole,ctx.text)
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

    override fun visitEqualsExpression(ctx: RtRuleEngine2Parser.EqualsExpressionContext): Node {
        val expression = ctx.expression()
        val left = visit(expression[0]) as Node.Expression
        val right = visit(expression[1]) as Node.Expression

        return Node.Expression.EqualsExpression(
            left,
            right,
            ctx.text
        )
    }

    override fun visitAndExpression(ctx: RtRuleEngine2Parser.AndExpressionContext): Node {
        val expression = ctx.expression()
        val left = visit(expression[0]) as Node.Expression
        val right = visit(expression[1]) as Node.Expression

        return Node.Expression.AndExpression(
            left,
            right,
            ctx.text
        )
    }

    override fun visitOrExpression(ctx: RtRuleEngine2Parser.OrExpressionContext): Node {
        val expression = ctx.expression()
        val left = visit(expression[0]) as Node.Expression
        val right = visit(expression[1]) as Node.Expression

        return Node.Expression.OrExpression(
            left,
            right,
            ctx.text
        )
    }

    private fun TerminalNode.toIdExpression(): Node.Expression.IdExpression {
        return if (text.startsWith("@")) {
            Node.Expression.IdExpression.IdRef(text.removePrefix("@"), text)
        } else {
            Node.Expression.IdExpression.Id(text, text)
        }
    }
}