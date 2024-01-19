// Generated from E:/Projects/JavaProjects/RuleEngine/src/main/resources/version3/RuleParser.g4 by ANTLR 4.13.1
package com.cool.jerry.version3;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RuleParser}.
 */
public interface RuleParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RuleParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(RuleParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(RuleParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionStatement(RuleParser.FunctionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionStatement(RuleParser.FunctionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterClassStatement(RuleParser.ClassStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitClassStatement(RuleParser.ClassStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(RuleParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionStatement}
	 * labeled alternative in {@link RuleParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(RuleParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#methodName}.
	 * @param ctx the parse tree
	 */
	void enterMethodName(RuleParser.MethodNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#methodName}.
	 * @param ctx the parse tree
	 */
	void exitMethodName(RuleParser.MethodNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(RuleParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(RuleParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#constructorFunction}.
	 * @param ctx the parse tree
	 */
	void enterConstructorFunction(RuleParser.ConstructorFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#constructorFunction}.
	 * @param ctx the parse tree
	 */
	void exitConstructorFunction(RuleParser.ConstructorFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#paramName}.
	 * @param ctx the parse tree
	 */
	void enterParamName(RuleParser.ParamNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#paramName}.
	 * @param ctx the parse tree
	 */
	void exitParamName(RuleParser.ParamNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(RuleParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(RuleParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(RuleParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(RuleParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#class}.
	 * @param ctx the parse tree
	 */
	void enterClass(RuleParser.ClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#class}.
	 * @param ctx the parse tree
	 */
	void exitClass(RuleParser.ClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(RuleParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(RuleParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#defineVariable}.
	 * @param ctx the parse tree
	 */
	void enterDefineVariable(RuleParser.DefineVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#defineVariable}.
	 * @param ctx the parse tree
	 */
	void exitDefineVariable(RuleParser.DefineVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#defineConstVariable}.
	 * @param ctx the parse tree
	 */
	void enterDefineConstVariable(RuleParser.DefineConstVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#defineConstVariable}.
	 * @param ctx the parse tree
	 */
	void exitDefineConstVariable(RuleParser.DefineConstVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(RuleParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(RuleParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(RuleParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(RuleParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#ifThenBody}.
	 * @param ctx the parse tree
	 */
	void enterIfThenBody(RuleParser.IfThenBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#ifThenBody}.
	 * @param ctx the parse tree
	 */
	void exitIfThenBody(RuleParser.IfThenBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#ifElseBody}.
	 * @param ctx the parse tree
	 */
	void enterIfElseBody(RuleParser.IfElseBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#ifElseBody}.
	 * @param ctx the parse tree
	 */
	void exitIfElseBody(RuleParser.IfElseBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#loopBody}.
	 * @param ctx the parse tree
	 */
	void enterLoopBody(RuleParser.LoopBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#loopBody}.
	 * @param ctx the parse tree
	 */
	void exitLoopBody(RuleParser.LoopBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#asyncBody}.
	 * @param ctx the parse tree
	 */
	void enterAsyncBody(RuleParser.AsyncBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#asyncBody}.
	 * @param ctx the parse tree
	 */
	void exitAsyncBody(RuleParser.AsyncBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#returnEmptyExpression}.
	 * @param ctx the parse tree
	 */
	void enterReturnEmptyExpression(RuleParser.ReturnEmptyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#returnEmptyExpression}.
	 * @param ctx the parse tree
	 */
	void exitReturnEmptyExpression(RuleParser.ReturnEmptyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#returnExpression}.
	 * @param ctx the parse tree
	 */
	void enterReturnExpression(RuleParser.ReturnExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#returnExpression}.
	 * @param ctx the parse tree
	 */
	void exitReturnExpression(RuleParser.ReturnExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#breakExpression}.
	 * @param ctx the parse tree
	 */
	void enterBreakExpression(RuleParser.BreakExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#breakExpression}.
	 * @param ctx the parse tree
	 */
	void exitBreakExpression(RuleParser.BreakExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#continueExpression}.
	 * @param ctx the parse tree
	 */
	void enterContinueExpression(RuleParser.ContinueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#continueExpression}.
	 * @param ctx the parse tree
	 */
	void exitContinueExpression(RuleParser.ContinueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(RuleParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(RuleParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#defineExpression}.
	 * @param ctx the parse tree
	 */
	void enterDefineExpression(RuleParser.DefineExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#defineExpression}.
	 * @param ctx the parse tree
	 */
	void exitDefineExpression(RuleParser.DefineExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void enterTypeExpression(RuleParser.TypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void exitTypeExpression(RuleParser.TypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberAutoIncreaseExpression}
	 * labeled alternative in {@link RuleParser#numberAutoIncreaseReduceExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumberAutoIncreaseExpression(RuleParser.NumberAutoIncreaseExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberAutoIncreaseExpression}
	 * labeled alternative in {@link RuleParser#numberAutoIncreaseReduceExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumberAutoIncreaseExpression(RuleParser.NumberAutoIncreaseExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberAutoReduceExpression}
	 * labeled alternative in {@link RuleParser#numberAutoIncreaseReduceExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumberAutoReduceExpression(RuleParser.NumberAutoReduceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberAutoReduceExpression}
	 * labeled alternative in {@link RuleParser#numberAutoIncreaseReduceExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumberAutoReduceExpression(RuleParser.NumberAutoReduceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#lamdaExpressionDefine}.
	 * @param ctx the parse tree
	 */
	void enterLamdaExpressionDefine(RuleParser.LamdaExpressionDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#lamdaExpressionDefine}.
	 * @param ctx the parse tree
	 */
	void exitLamdaExpressionDefine(RuleParser.LamdaExpressionDefineContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#catchErrorDefine}.
	 * @param ctx the parse tree
	 */
	void enterCatchErrorDefine(RuleParser.CatchErrorDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#catchErrorDefine}.
	 * @param ctx the parse tree
	 */
	void exitCatchErrorDefine(RuleParser.CatchErrorDefineContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#watchBody}.
	 * @param ctx the parse tree
	 */
	void enterWatchBody(RuleParser.WatchBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#watchBody}.
	 * @param ctx the parse tree
	 */
	void exitWatchBody(RuleParser.WatchBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#errorBody}.
	 * @param ctx the parse tree
	 */
	void enterErrorBody(RuleParser.ErrorBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#errorBody}.
	 * @param ctx the parse tree
	 */
	void exitErrorBody(RuleParser.ErrorBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#mapDefine}.
	 * @param ctx the parse tree
	 */
	void enterMapDefine(RuleParser.MapDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#mapDefine}.
	 * @param ctx the parse tree
	 */
	void exitMapDefine(RuleParser.MapDefineContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void enterMapEntry(RuleParser.MapEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void exitMapEntry(RuleParser.MapEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RuleParser#reserveExp}.
	 * @param ctx the parse tree
	 */
	void enterReserveExp(RuleParser.ReserveExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RuleParser#reserveExp}.
	 * @param ctx the parse tree
	 */
	void exitReserveExp(RuleParser.ReserveExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstVariableExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstVariableExpression(RuleParser.ConstVariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstVariableExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstVariableExpression(RuleParser.ConstVariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpression(RuleParser.AssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpression(RuleParser.AssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallMethodExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCallMethodExpression(RuleParser.CallMethodExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallMethodExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCallMethodExpression(RuleParser.CallMethodExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectPropertiesExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectPropertiesExpression(RuleParser.ObjectPropertiesExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectPropertiesExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectPropertiesExpression(RuleParser.ObjectPropertiesExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CatchErrorExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCatchErrorExpression(RuleParser.CatchErrorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CatchErrorExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCatchErrorExpression(RuleParser.CatchErrorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDivYuExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulDivYuExpression(RuleParser.MulDivYuExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivYuExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulDivYuExpression(RuleParser.MulDivYuExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefineRangeExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDefineRangeExpression(RuleParser.DefineRangeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefineRangeExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDefineRangeExpression(RuleParser.DefineRangeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ModAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterModAssignExpression(RuleParser.ModAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ModAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitModAssignExpression(RuleParser.ModAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberAutoExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNumberAutoExpression(RuleParser.NumberAutoExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberAutoExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNumberAutoExpression(RuleParser.NumberAutoExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompareExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompareExpression(RuleParser.CompareExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompareExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompareExpression(RuleParser.CompareExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AsyncExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAsyncExpression(RuleParser.AsyncExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AsyncExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAsyncExpression(RuleParser.AsyncExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpression(RuleParser.AddSubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpression(RuleParser.AddSubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubAssignExpression(RuleParser.SubAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubAssignExpression(RuleParser.SubAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDivAssignExpression(RuleParser.DivAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDivAssignExpression(RuleParser.DivAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddAssignExpression(RuleParser.AddAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddAssignExpression(RuleParser.AddAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BitOperationExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitOperationExpression(RuleParser.BitOperationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BitOperationExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitOperationExpression(RuleParser.BitOperationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefineTypeExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDefineTypeExpression(RuleParser.DefineTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefineTypeExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDefineTypeExpression(RuleParser.DefineTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefineNameExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDefineNameExpression(RuleParser.DefineNameExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefineNameExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDefineNameExpression(RuleParser.DefineNameExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReserveExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterReserveExpression(RuleParser.ReserveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReserveExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitReserveExpression(RuleParser.ReserveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMapExpression(RuleParser.MapExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMapExpression(RuleParser.MapExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PriorityExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPriorityExpression(RuleParser.PriorityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PriorityExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPriorityExpression(RuleParser.PriorityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefineIfExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDefineIfExpression(RuleParser.DefineIfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefineIfExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDefineIfExpression(RuleParser.DefineIfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrAssignExpression(RuleParser.OrAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrAssignExpression(RuleParser.OrAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpression(RuleParser.VariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpression(RuleParser.VariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndAssignExpression(RuleParser.AndAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndAssignExpression(RuleParser.AndAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulAssignExpression(RuleParser.MulAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulAssignExpression(RuleParser.MulAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LoopExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLoopExpression(RuleParser.LoopExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LoopExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLoopExpression(RuleParser.LoopExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectMethodCallExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectMethodCallExpression(RuleParser.ObjectMethodCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectMethodCallExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectMethodCallExpression(RuleParser.ObjectMethodCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpression(RuleParser.ArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpression(RuleParser.ArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LamdaExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLamdaExpression(RuleParser.LamdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LamdaExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLamdaExpression(RuleParser.LamdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccessExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpression(RuleParser.ArrayAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccessExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpression(RuleParser.ArrayAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XorAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterXorAssignExpression(RuleParser.XorAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XorAssignExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitXorAssignExpression(RuleParser.XorAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SignedExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSignedExpression(RuleParser.SignedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SignedExpression}
	 * labeled alternative in {@link RuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSignedExpression(RuleParser.SignedExpressionContext ctx);
}