package com.cool.jerry.v3

import com.cool.jerry.version3.RuleParser
import com.cool.jerry.version3.RuleParserBaseVisitor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

class RuleParserVisitorImpl : RuleParserBaseVisitor<R3Node>() {
    override fun visitProgram(ctx: RuleParser.ProgramContext): R3Node {
        return ctx.statement().mapNotNull {
            visit(it)
        }.let {
            val sorted = it.sortedBy {node->
                node !is R3Node.Statement.ClassStatement
            }.sortedBy {node->
                node !is R3Node.Statement.FunctionStatement
            }
            R3Node.Program(ctx.text, sorted).apply {
                levelSet(null)
            }
        }
    }

    override fun visitFunctionStatement(ctx: RuleParser.FunctionStatementContext): R3Node {
        return visit(ctx.function())
    }

    override fun visitFunction(ctx: RuleParser.FunctionContext): R3Node {
        val functionName = ctx.methodName().ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val params = ctx.params()?.let {
            visit(it) as R3Node.Expression.Define.Params
        } ?: R3Node.Expression.Define.Params("", emptyList())
        val functionBody = ctx.functionBody().mapNotNull {
            visit(it) as R3Node.Expression
        }
        return R3Node.Statement.FunctionStatement(
            ctx.text,
            functionName,
            params,
            functionBody
        )
    }

    override fun visitFunctionBody(ctx: RuleParser.FunctionBodyContext): R3Node {
        val expression = ctx.expression()
        val returnExpression = ctx.returnExpression()
        val returnEmptyExpression = ctx.returnEmptyExpression()
        return if (expression != null) {
            visit(expression)
        } else if (returnExpression != null) {
            visit(returnExpression)
        } else {
            visit(returnEmptyExpression)
        }
    }


    override fun visitClassStatement(ctx: RuleParser.ClassStatementContext): R3Node {
        return visit(ctx.class_())
    }

    override fun visitExpressionStatement(ctx: RuleParser.ExpressionStatementContext): R3Node {
        return visit(ctx.expression())
    }

    override fun visitReturnExpression(ctx: RuleParser.ReturnExpressionContext): R3Node {
        return R3Node.Expression.ReturnExpression(ctx.text, visit(ctx.expression()) as R3Node.Expression)
    }

    override fun visitReturnEmptyExpression(ctx: RuleParser.ReturnEmptyExpressionContext): R3Node {
        return R3Node.Expression.ReturnExpression(ctx.text, null)
    }


    override fun visitClass(ctx: RuleParser.ClassContext): R3Node {
        val className = ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val params = ctx.params()?.let {
            visit(it) as R3Node.Expression.Define.Params
        } ?: R3Node.Expression.Define.Params("", emptyList())
        val classBody = ctx.classBody()?.mapNotNull {
            visit(it)
        } ?: emptyList()
        return R3Node.Statement.ClassStatement(
            ctx.text,
            className,
            params,
            classBody
        )
    }

    override fun visitConstructorFunction(ctx: RuleParser.ConstructorFunctionContext): R3Node {
            val constructorName = ctx.methodName().ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val params = ctx.params()?.let {
            visit(it) as R3Node.Expression.Define.Params
        } ?: R3Node.Expression.Define.Params("", emptyList())
        val functionBody = ctx.functionBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()
        return R3Node.Statement.ConstructorFunctionStatement(
            ctx.text,
            R3Node.Statement.FunctionStatement(
                ctx.text,
                constructorName,
                params,
                functionBody
            )
        )
    }

    override fun visitParams(ctx: RuleParser.ParamsContext): R3Node {
        val parameters = ctx.paramName().map {
            it.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        }
        return R3Node.Expression.Define.Params(ctx.text, parameters)
    }


    override fun visitClassBody(ctx: RuleParser.ClassBodyContext): R3Node {
        val function = ctx.function()
        val defineVariable = ctx.defineVariable()
        val defineConstVariable = ctx.defineConstVariable()
        val defineConstructorFunctionExpression  = ctx.constructorFunction()
        return if (function != null) {
            visit(function)
        } else if (defineVariable != null) {
            visit(defineVariable)
        } else if (defineConstructorFunctionExpression!=null){
            visit(defineConstructorFunctionExpression)
        }else {
            visit(defineConstVariable)
        }
    }

    override fun visitDefineVariable(ctx: RuleParser.DefineVariableContext): R3Node {
        val variableName = ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val variableValue = visit(ctx.expression()) as R3Node.Expression

        return R3Node.Expression.VariableExpression(
            ctx.text,
            variableName,
            variableValue,
            false
        )
    }

    override fun visitDefineConstVariable(ctx: RuleParser.DefineConstVariableContext): R3Node {
        val variableName = ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val variableValue = visit(ctx.expression()) as R3Node.Expression

        return R3Node.Expression.VariableExpression(
            ctx.text,
            variableName,
            variableValue,
            true
        )
    }

    override fun visitDefineExpression(ctx: RuleParser.DefineExpressionContext): R3Node {
        return if (ctx.ID() != null) {
            ctx.ID().toIdExpression()
        } else {
            ctx.ID_REF().toIdExpression()
        }
    }

    override fun visitDefineTypeExpression(ctx: RuleParser.DefineTypeExpressionContext): R3Node {
        return visit(ctx.typeExpression())
    }

    override fun visitTypeExpression(ctx: RuleParser.TypeExpressionContext): R3Node {
        return if (ctx.NUMBER() != null) {
            R3Node.Expression.TypeExpression.NumberTypeExpression(ctx.text, ctx.NUMBER().text.toLong())
        } else if (ctx.BOOLEAN() != null) {
            R3Node.Expression.TypeExpression.BooleanTypeExpression(ctx.text, ctx.BOOLEAN().text.toBoolean())
        } else if (ctx.STRING() != null) {
            if (ctx.text.startsWith("\'")) {
                R3Node.Expression.TypeExpression.StringTypeExpression(
                    ctx.text,
                    ctx.STRING().text.removePrefix("\'").removeSuffix("\'")
                )
            }else{
                R3Node.Expression.TypeExpression.StringTypeExpression(
                    ctx.text,
                    ctx.STRING().text.removePrefix("\"").removeSuffix("\"")
                )
            }
        } else {
            R3Node.Expression.TypeExpression.FloatNumberTypeExpression(ctx.text, ctx.NUMBER_FLOAT().text.toDouble())
        }
    }

    override fun visitObjectPropertiesExpression(ctx: RuleParser.ObjectPropertiesExpressionContext): R3Node {
        val objectExpression = visit(ctx.expression()) as R3Node.Expression
        val propertiesName = ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        return R3Node.Expression.ObjectPropertiesExpression(
            ctx.text,
            objectExpression,
            propertiesName
        )
    }

    override fun visitBitOperationExpression(ctx: RuleParser.BitOperationExpressionContext): R3Node {
        return if (ctx.BIT_LEFT() != null) {
            R3Node.Expression.OperateExpression.BitOperateExpression.BitLeftOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.BIT_RIGHT() != null) {
            R3Node.Expression.OperateExpression.BitOperateExpression.BitRightOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.BITAND() != null) {
            R3Node.Expression.OperateExpression.BitOperateExpression.BitAndOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.BITOR() != null) {
            R3Node.Expression.OperateExpression.BitOperateExpression.BitOrOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else {
            R3Node.Expression.OperateExpression.BitOperateExpression.BitXorOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        }
    }

    override fun visitMulDivYuExpression(ctx: RuleParser.MulDivYuExpressionContext): R3Node {
        return if (ctx.MUL() != null) {
            R3Node.Expression.OperateExpression.MathOperateExpression.MulOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.DIV() != null) {
            R3Node.Expression.OperateExpression.MathOperateExpression.DivOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else {
            R3Node.Expression.OperateExpression.MathOperateExpression.ModOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        }
    }

    override fun visitAddSubExpression(ctx: RuleParser.AddSubExpressionContext): R3Node {
        return if (ctx.SUB() != null) {
            R3Node.Expression.OperateExpression.MathOperateExpression.SubOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else {
            R3Node.Expression.OperateExpression.MathOperateExpression.AddOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        }
    }

    override fun visitAddAssignExpression(ctx: RuleParser.AddAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.AddAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitSubAssignExpression(ctx: RuleParser.SubAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.SubAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitMulAssignExpression(ctx: RuleParser.MulAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.MulAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitDivAssignExpression(ctx: RuleParser.DivAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.DivAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitAndAssignExpression(ctx: RuleParser.AndAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.AndAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitOrAssignExpression(ctx: RuleParser.OrAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.OrAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitXorAssignExpression(ctx: RuleParser.XorAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.XorAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitModAssignExpression(ctx: RuleParser.ModAssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.MathAssignOperateExpression.ModAssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitAssignExpression(ctx: RuleParser.AssignExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.AssignOperateExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression
        )
    }

    override fun visitCompareExpression(ctx: RuleParser.CompareExpressionContext): R3Node {
        return if (ctx.AND() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.AndCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.OR() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.OrCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.EQUAL() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.EqualCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.NOTEQUAL() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.NotEqualCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.GE() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.MoreEqualCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.LE() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.LessEqualCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else if (ctx.GT() != null) {
            R3Node.Expression.OperateExpression.CompareOperateExpression.MoreCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        } else {
            R3Node.Expression.OperateExpression.CompareOperateExpression.LessCompareOperateExpression(
                ctx.text,
                visit(ctx.expression(0)) as R3Node.Expression,
                visit(ctx.expression(1)) as R3Node.Expression
            )
        }
    }


    override fun visitDefineIfExpression(ctx: RuleParser.DefineIfExpressionContext): R3Node {
        return visit(ctx.ifExpression())
    }

    override fun visitIfExpression(ctx: RuleParser.IfExpressionContext): R3Node {
        val condition = visit(ctx.expression()) as R3Node.Expression
        val thenBody = ctx.ifThenBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()
        val elseBody = ctx.ifElseBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()
        return R3Node.Expression.IfExpression(
            ctx.text,
            condition,
            thenBody,
            elseBody
        )
    }

    override fun visitIfThenBody(ctx: RuleParser.IfThenBodyContext): R3Node {
        return visit(ctx.expression()?:ctx.breakExpression()?:ctx.returnExpression()?:ctx.returnEmptyExpression()?:ctx.continueExpression())
    }

    override fun visitIfElseBody(ctx: RuleParser.IfElseBodyContext): R3Node {
        return visit(ctx.expression()?:ctx.breakExpression()?:ctx.returnExpression()?:ctx.returnEmptyExpression()?:ctx.continueExpression())
    }

    override fun visitCatchErrorExpression(ctx: RuleParser.CatchErrorExpressionContext): R3Node {
        return visit(ctx.catchErrorDefine())
    }

    override fun visitCatchErrorDefine(ctx: RuleParser.CatchErrorDefineContext): R3Node {
        val errorName = ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val watchBody = ctx.watchBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()
        val errorBody = ctx.errorBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()
        return R3Node.Expression.CatchErrorExpression(
            ctx.text,
            watchBody,
            errorBody,
            errorName
        )
    }

    override fun visitWatchBody(ctx: RuleParser.WatchBodyContext): R3Node {
        return visit(ctx.expression()?:ctx.breakExpression()?:ctx.returnExpression()?:ctx.returnEmptyExpression()?:ctx.continueExpression())
    }

    override fun visitErrorBody(ctx: RuleParser.ErrorBodyContext): R3Node {
        return visit(ctx.expression()?:ctx.breakExpression()?:ctx.returnExpression()?:ctx.returnEmptyExpression()?:ctx.continueExpression())
    }

    override fun visitLoopExpression(ctx: RuleParser.LoopExpressionContext): R3Node {
        val loopBody = ctx.loopBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()

        if (ctx.expression()==null){
            return R3Node.Expression.LoopExpression(
                ctx.text,
                null,
                null,
                loopBody
            )
        }


        val condition = visit(ctx.expression()) as R3Node.Expression
        val conditionProxy = if (ctx.ID()!=null){
            ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        }else{
            null
        }
        return R3Node.Expression.LoopExpression(
            ctx.text,
            condition,
            conditionProxy,
            loopBody
        )
    }

    override fun visitAsyncBody(ctx: RuleParser.AsyncBodyContext): R3Node {
        return visit(ctx.expression()?:ctx.returnEmptyExpression()?:ctx.returnExpression())
    }

    override fun visitAsyncExpression(ctx: RuleParser.AsyncExpressionContext): R3Node {
        val asyncBody = ctx.asyncBody()?.mapNotNull {
            visit(it) as R3Node.Expression
        } ?: emptyList()
        return R3Node.Expression.AsyncExpression(
            ctx.text,
            asyncBody
        )
    }

    override fun visitLoopBody(ctx: RuleParser.LoopBodyContext): R3Node {
        return if (ctx.expression()!= null) {
            visit(ctx.expression())
        }else if (ctx.continueExpression()!=null){
            visit(ctx.continueExpression())
        }else if (ctx.breakExpression()!=null){
            visit(ctx.breakExpression())
        }else if(ctx.returnExpression()!=null){
            visit(ctx.returnExpression())
        }else{
            visit(ctx.returnEmptyExpression())
        }
    }

    override fun visitContinueExpression(ctx: RuleParser.ContinueExpressionContext): R3Node {
        return R3Node.Expression.ContinueExpression(ctx.text)
    }

    override fun visitBreakExpression(ctx: RuleParser.BreakExpressionContext): R3Node {
        return R3Node.Expression.BreakExpression(ctx.text)
    }

    override fun visitDefineRangeExpression(ctx: RuleParser.DefineRangeExpressionContext): R3Node {
        return R3Node.Expression.RangeExpression(
            ctx.text,
            visit(ctx.expression(0)) as R3Node.Expression,
            visit(ctx.expression(1)) as R3Node.Expression,
        )
    }

    override fun visitArrayExpression(ctx: RuleParser.ArrayExpressionContext): R3Node {
        return visit(ctx.array())
    }

    override fun visitArray(ctx: RuleParser.ArrayContext): R3Node {
        val list = ctx.expression()?.mapNotNull {
            visit(it) as R3Node.Expression
        }?: emptyList()

        val array = list.toTypedArray()
        return R3Node.Expression.TypeExpression.ArrayExpression(
            ctx.text,
            array
        )
    }

    override fun visitArrayAccessExpression(ctx: RuleParser.ArrayAccessExpressionContext): R3Node {
        val arrayObject = visit(ctx.expression()[0]) as R3Node.Expression
        val index = visit(ctx.expression()[1]) as R3Node.Expression
        return R3Node.Expression.ArrayAccessExpression(
            ctx.text,
            arrayObject,
            index
        )
    }

    override fun visitObjectMethodCallExpression(ctx: RuleParser.ObjectMethodCallExpressionContext): R3Node {
        val objectObject = visit(ctx.expression()) as R3Node.Expression
        val methodCall = visit(ctx.methodCallExpression()) as R3Node.Expression.MethodCallExpression
        return R3Node.Expression.ObjectMethodCallExpression(
            ctx.text,
            objectObject,
            methodCall.methodName,
            methodCall.arguments
        )
    }

    override fun visitCallMethodExpression(ctx: RuleParser.CallMethodExpressionContext): R3Node {
        return visit(ctx.methodCallExpression())
    }

    override fun visitMethodCallExpression(ctx: RuleParser.MethodCallExpressionContext): R3Node {
        val methodName = ctx.ID().toIdExpression() as R3Node.Expression.Define.Identifier.ID
        val arguments = ctx.expression()?.mapNotNull {
            visit(it) as R3Node.Expression
        }?: emptyList()
        return R3Node.Expression.MethodCallExpression(
            ctx.text,
            methodName,
            arguments
        )
    }

    override fun visitConstVariableExpression(ctx: RuleParser.ConstVariableExpressionContext): R3Node {
        return visit(ctx.defineConstVariable())
    }

    override fun visitVariableExpression(ctx: RuleParser.VariableExpressionContext): R3Node {
        return visit(ctx.defineVariable())
    }

    override fun visitMethodName(ctx: RuleParser.MethodNameContext): R3Node {
        return ctx.ID().toIdExpression()
    }

    override fun visitNumberAutoExpression(ctx: RuleParser.NumberAutoExpressionContext): R3Node {
        return visit(ctx.numberAutoIncreaseReduceExpression())
    }

    override fun visitNumberAutoIncreaseExpression(ctx: RuleParser.NumberAutoIncreaseExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoIncreaseOperateExpression(
            ctx.text,
            visit(ctx.defineExpression()) as R3Node.Expression.Define.Identifier
        )
    }

    override fun visitNumberAutoReduceExpression(ctx: RuleParser.NumberAutoReduceExpressionContext): R3Node {
        return R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoDecreaseOperateExpression(
            ctx.text,
            visit(ctx.defineExpression()) as R3Node.Expression.Define.Identifier
        )
    }

    override fun visitPriorityExpression(ctx: RuleParser.PriorityExpressionContext): R3Node {
        return visit(ctx.parenthesizedExpression())
    }

    override fun visitParenthesizedExpression(ctx: RuleParser.ParenthesizedExpressionContext): R3Node {
        return visit(ctx.expression())
    }

    override fun visitSignedExpression(ctx: RuleParser.SignedExpressionContext): R3Node {
        return R3Node.Expression.SignedExpression(ctx.text,visit(ctx.expression()) as R3Node.Expression)
    }

    override fun visitLamdaExpression(ctx: RuleParser.LamdaExpressionContext): R3Node {
        return visit(ctx.lamdaExpressionDefine())
    }

    override fun visitLamdaExpressionDefine(ctx: RuleParser.LamdaExpressionDefineContext): R3Node {
        val params = ctx.params()?.let {
            visit(it) as R3Node.Expression.Define.Params
        } ?: R3Node.Expression.Define.Params("", emptyList())
        val functionBody = ctx.functionBody().mapNotNull {
            visit(it) as R3Node.Expression
        }
        return R3Node.Expression.LambdaExpression(
            ctx.text,
            params,
            functionBody
        )
    }

    override fun visitMapExpression(ctx: RuleParser.MapExpressionContext): R3Node {
        val map = mutableMapOf<R3Node.Expression,R3Node.Expression>()

        for (mapEntryContext in ctx.mapDefine().mapEntry()) {
            map[visit(mapEntryContext.expression()[0]) as R3Node.Expression] = visit(mapEntryContext.expression()[1]) as R3Node.Expression
        }
         return R3Node.Expression.MapExpression(
            ctx.text,
             map
        )
    }

    override fun visitReserveExpression(ctx: RuleParser.ReserveExpressionContext): R3Node {
        return R3Node.Expression.ReserveExpression(
            ctx.text,
            visit(ctx.reserveExp()) as R3Node.Expression
        )
    }

    override fun visitReserveExp(ctx: RuleParser.ReserveExpContext): R3Node {
        return visit(ctx.expression())
    }

    override fun visitErrorNode(node: ErrorNode?): R3Node {
        throw RuntimeException("ErrorNode:${node?.text}")
    }

    private fun TerminalNode.toIdExpression(): R3Node.Expression.Define.Identifier {
        return if (text.startsWith("@")) {
            R3Node.Expression.Define.Identifier.ID_REF(text, text.removePrefix("@"))
        } else {
            R3Node.Expression.Define.Identifier.ID(text, text)
        }
    }
}


