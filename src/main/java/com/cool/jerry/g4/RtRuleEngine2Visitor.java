// Generated from E:/Projects/JavaProjects/RuleEngine/src/main/resources/RtRuleEngine2.g4 by ANTLR 4.13.1
package com.cool.jerry.g4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RtRuleEngine2Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RtRuleEngine2Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(RtRuleEngine2Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewVariableAssignmentStatement}
	 * labeled alternative in {@link RtRuleEngine2Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewVariableAssignmentStatement(RtRuleEngine2Parser.NewVariableAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableAssignmentStatement}
	 * labeled alternative in {@link RtRuleEngine2Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignmentStatement(RtRuleEngine2Parser.VariableAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariablePropertiesAssignmentStatement}
	 * labeled alternative in {@link RtRuleEngine2Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariablePropertiesAssignmentStatement(RtRuleEngine2Parser.VariablePropertiesAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InvokeStatement}
	 * labeled alternative in {@link RtRuleEngine2Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvokeStatement(RtRuleEngine2Parser.InvokeStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionStatement}
	 * labeled alternative in {@link RtRuleEngine2Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionStatement(RtRuleEngine2Parser.FunctionStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(RtRuleEngine2Parser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExpression(RtRuleEngine2Parser.IntExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatExpression(RtRuleEngine2Parser.FloatExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(RtRuleEngine2Parser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpression(RtRuleEngine2Parser.AddSubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExpression(RtRuleEngine2Parser.BooleanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(RtRuleEngine2Parser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MethodCallExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpression(RtRuleEngine2Parser.MethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpression(RtRuleEngine2Parser.StringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GetPropertiesExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetPropertiesExpression(RtRuleEngine2Parser.GetPropertiesExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpression(RtRuleEngine2Parser.IdExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectMethodCallExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectMethodCallExpression(RtRuleEngine2Parser.ObjectMethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdRefExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdRefExpression(RtRuleEngine2Parser.IdRefExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpression(RtRuleEngine2Parser.MulDivExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualsExpression}
	 * labeled alternative in {@link RtRuleEngine2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsExpression(RtRuleEngine2Parser.EqualsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#ifStatment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatment(RtRuleEngine2Parser.IfStatmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#elseStatment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatment(RtRuleEngine2Parser.ElseStatmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(RtRuleEngine2Parser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(RtRuleEngine2Parser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(RtRuleEngine2Parser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link RtRuleEngine2Parser#properties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperties(RtRuleEngine2Parser.PropertiesContext ctx);
}