// Generated from E:/Projects/JavaProjects/RuleEngine/src/main/resources/version3/RuleParser.g4 by ANTLR 4.13.1
package com.cool.jerry.version3;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RuleParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RuleParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RuleParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(RuleParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionStatement(RuleParser.FunctionStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassStatement(RuleParser.ClassStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(RuleParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(RuleParser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(RuleParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#constructorFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorFunction(RuleParser.ConstructorFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#paramName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamName(RuleParser.ParamNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(RuleParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(RuleParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#class}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass(RuleParser.ClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(RuleParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#defineVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineVariable(RuleParser.DefineVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#defineConstVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineConstVariable(RuleParser.DefineConstVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(RuleParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#ifExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(RuleParser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#ifThenBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenBody(RuleParser.IfThenBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#ifElseBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseBody(RuleParser.IfElseBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#loopBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopBody(RuleParser.LoopBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#returnEmptyExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnEmptyExpression(RuleParser.ReturnEmptyExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#returnExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnExpression(RuleParser.ReturnExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#breakExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakExpression(RuleParser.BreakExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#continueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueExpression(RuleParser.ContinueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#methodCallExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpression(RuleParser.MethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(RuleParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#defineExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineExpression(RuleParser.DefineExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RuleParser#typeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeExpression(RuleParser.TypeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberAutoIncreaseExpression}
	 * labeled alternative in {@link RuleParser#numberAutoIncreaseReduceExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAutoIncreaseExpression(RuleParser.NumberAutoIncreaseExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberAutoReduceExpression}
	 * labeled alternative in {@link RuleParser#numberAutoIncreaseReduceExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAutoReduceExpression(RuleParser.NumberAutoReduceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstVariableExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstVariableExpression(RuleParser.ConstVariableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpression(RuleParser.AssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallMethodExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallMethodExpression(RuleParser.CallMethodExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectPropertiesExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectPropertiesExpression(RuleParser.ObjectPropertiesExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivYuExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivYuExpression(RuleParser.MulDivYuExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefineRangeExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineRangeExpression(RuleParser.DefineRangeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModAssignExpression(RuleParser.ModAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberAutoExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAutoExpression(RuleParser.NumberAutoExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompareExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpression(RuleParser.CompareExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpression(RuleParser.AddSubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubAssignExpression(RuleParser.SubAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivAssignExpression(RuleParser.DivAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddAssignExpression(RuleParser.AddAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitOperationExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitOperationExpression(RuleParser.BitOperationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefineTypeExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineTypeExpression(RuleParser.DefineTypeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefineNameExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineNameExpression(RuleParser.DefineNameExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PriorityExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPriorityExpression(RuleParser.PriorityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefineIfExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineIfExpression(RuleParser.DefineIfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrAssignExpression(RuleParser.OrAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExpression(RuleParser.VariableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndAssignExpression(RuleParser.AndAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulAssignExpression(RuleParser.MulAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LoopExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopExpression(RuleParser.LoopExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectMethodCallExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectMethodCallExpression(RuleParser.ObjectMethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpression(RuleParser.ArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAccessExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpression(RuleParser.ArrayAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XorAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXorAssignExpression(RuleParser.XorAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SignedExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignedExpression(RuleParser.SignedExpressionContext ctx);
}