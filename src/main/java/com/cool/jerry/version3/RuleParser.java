// Generated from E:/Projects/JavaProjects/RuleEngine/src/main/resources/version3/RuleParser.g4 by ANTLR 4.13.1
package com.cool.jerry.version3;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class RuleParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CONST=1, DEF=2, CLASS=3, AS=4, LOOP=5, TO=6, BREAK=7, CONTINUE=8, RETURN=9, 
		IF=10, ELSE=11, BOOLEAN=12, NUMBER=13, NUMBER_FLOAT=14, STRING=15, LPAREN=16, 
		RPAREN=17, LBRACE=18, RBRACE=19, LBRACK=20, RBRACK=21, COMMA=22, DOT=23, 
		ASSIGN=24, GT=25, LT=26, BANG=27, TILDE=28, QUESTION=29, EQUAL=30, LE=31, 
		GE=32, NOTEQUAL=33, AND=34, OR=35, INC=36, DEC=37, ADD=38, SUB=39, MUL=40, 
		DIV=41, BITAND=42, BITOR=43, CARET=44, MOD=45, ARROW=46, BIT_LEFT=47, 
		BIT_RIGHT=48, ADD_ASSIGN=49, SUB_ASSIGN=50, MUL_ASSIGN=51, DIV_ASSIGN=52, 
		AND_ASSIGN=53, OR_ASSIGN=54, XOR_ASSIGN=55, MOD_ASSIGN=56, AT=57, ID=58, 
		ID_REF=59, NEWLINE=60, WS=61, COMMENT=62, LINE_COMMENT=63;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_methodName = 2, RULE_function = 3, 
		RULE_constructorFunction = 4, RULE_paramName = 5, RULE_params = 6, RULE_functionBody = 7, 
		RULE_class = 8, RULE_classBody = 9, RULE_defineVariable = 10, RULE_defineConstVariable = 11, 
		RULE_array = 12, RULE_ifExpression = 13, RULE_ifThenBody = 14, RULE_ifElseBody = 15, 
		RULE_loopBody = 16, RULE_returnEmptyExpression = 17, RULE_returnExpression = 18, 
		RULE_breakExpression = 19, RULE_continueExpression = 20, RULE_methodCallExpression = 21, 
		RULE_parenthesizedExpression = 22, RULE_defineExpression = 23, RULE_typeExpression = 24, 
		RULE_numberAutoIncreaseReduceExpression = 25, RULE_expression = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "methodName", "function", "constructorFunction", 
			"paramName", "params", "functionBody", "class", "classBody", "defineVariable", 
			"defineConstVariable", "array", "ifExpression", "ifThenBody", "ifElseBody", 
			"loopBody", "returnEmptyExpression", "returnExpression", "breakExpression", 
			"continueExpression", "methodCallExpression", "parenthesizedExpression", 
			"defineExpression", "typeExpression", "numberAutoIncreaseReduceExpression", 
			"expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'const'", "'def'", "'class'", "'as'", "'loop'", "'to'", "'break'", 
			"'continue'", "'return'", "'if'", "'else'", null, null, null, null, "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "','", "'.'", "'='", "'>'", "'<'", 
			"'!'", "'~'", "'?'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", 
			"'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", 
			"'->'", "'<<'", "'>>'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", 
			"'^='", "'%='", "'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "CONST", "DEF", "CLASS", "AS", "LOOP", "TO", "BREAK", "CONTINUE", 
			"RETURN", "IF", "ELSE", "BOOLEAN", "NUMBER", "NUMBER_FLOAT", "STRING", 
			"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", 
			"DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", "EQUAL", "LE", 
			"GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", 
			"BITAND", "BITOR", "CARET", "MOD", "ARROW", "BIT_LEFT", "BIT_RIGHT", 
			"ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", 
			"OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "AT", "ID", "ID_REF", "NEWLINE", 
			"WS", "COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RuleParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    boolean isParenthesized(String text) {
	        // 根据你的条件判断是否是括号表达式
	        // 返回 true 表示是括号表达式，false 表示不是
	        // 例如，可以检查 text 是否以 '(' 开始且以 ')' 结束
	        return text.startsWith("(") && text.endsWith(")");
	    }

	    boolean isMethodCall(String text) {
	        // 根据你的条件判断是否是方法调用
	        // 返回 true 表示是方法调用，false 表示不是
	        // 例如，可以检查 text 是否包含 '(' 和 ')'
	        return text.contains("(") && text.contains(")");
	    }

	public RuleParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212125734L) != 0)) {
				{
				{
				setState(54);
				statement();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionStatementContext extends StatementContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public FunctionStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterFunctionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitFunctionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitFunctionStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassStatementContext extends StatementContext {
		public ClassContext class_() {
			return getRuleContext(ClassContext.class,0);
		}
		public ClassStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterClassStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitClassStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitClassStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionStatementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(63);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				_localctx = new FunctionStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				function();
				}
				break;
			case 2:
				_localctx = new ClassStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				class_();
				}
				break;
			case 3:
				_localctx = new ExpressionStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(62);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public MethodNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterMethodName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitMethodName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitMethodName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodNameContext methodName() throws RecognitionException {
		MethodNameContext _localctx = new MethodNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_methodName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(RuleParser.DEF, 0); }
		public MethodNameContext methodName() {
			return getRuleContext(MethodNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(RuleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(RuleParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(RuleParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(RuleParser.RBRACE, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<FunctionBodyContext> functionBody() {
			return getRuleContexts(FunctionBodyContext.class);
		}
		public FunctionBodyContext functionBody(int i) {
			return getRuleContext(FunctionBodyContext.class,i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(DEF);
			setState(68);
			methodName();
			setState(69);
			match(LPAREN);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(70);
				params();
				}
			}

			setState(73);
			match(RPAREN);
			setState(74);
			match(LBRACE);
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212126246L) != 0)) {
				{
				{
				setState(75);
				functionBody();
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorFunctionContext extends ParserRuleContext {
		public MethodNameContext methodName() {
			return getRuleContext(MethodNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(RuleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(RuleParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(RuleParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(RuleParser.RBRACE, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<FunctionBodyContext> functionBody() {
			return getRuleContexts(FunctionBodyContext.class);
		}
		public FunctionBodyContext functionBody(int i) {
			return getRuleContext(FunctionBodyContext.class,i);
		}
		public ConstructorFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterConstructorFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitConstructorFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitConstructorFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorFunctionContext constructorFunction() throws RecognitionException {
		ConstructorFunctionContext _localctx = new ConstructorFunctionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constructorFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			methodName();
			setState(84);
			match(LPAREN);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(85);
				params();
				}
			}

			setState(88);
			match(RPAREN);
			setState(89);
			match(LBRACE);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212126246L) != 0)) {
				{
				{
				setState(90);
				functionBody();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public ParamNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterParamName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitParamName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitParamName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamNameContext paramName() throws RecognitionException {
		ParamNameContext _localctx = new ParamNameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_paramName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamsContext extends ParserRuleContext {
		public List<ParamNameContext> paramName() {
			return getRuleContexts(ParamNameContext.class);
		}
		public ParamNameContext paramName(int i) {
			return getRuleContext(ParamNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(RuleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(RuleParser.COMMA, i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			paramName();
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(101);
				match(COMMA);
				setState(102);
				paramName();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionBodyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnExpressionContext returnExpression() {
			return getRuleContext(ReturnExpressionContext.class,0);
		}
		public ReturnEmptyExpressionContext returnEmptyExpression() {
			return getRuleContext(ReturnEmptyExpressionContext.class,0);
		}
		public FunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterFunctionBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitFunctionBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitFunctionBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionBodyContext functionBody() throws RecognitionException {
		FunctionBodyContext _localctx = new FunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionBody);
		try {
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(108);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				returnExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				returnEmptyExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(RuleParser.DEF, 0); }
		public TerminalNode CLASS() { return getToken(RuleParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(RuleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(RuleParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(RuleParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(RuleParser.RBRACE, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<ClassBodyContext> classBody() {
			return getRuleContexts(ClassBodyContext.class);
		}
		public ClassBodyContext classBody(int i) {
			return getRuleContext(ClassBodyContext.class,i);
		}
		public ClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassContext class_() throws RecognitionException {
		ClassContext _localctx = new ClassContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_class);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(DEF);
			setState(114);
			match(CLASS);
			setState(115);
			match(ID);
			setState(116);
			match(LPAREN);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(117);
				params();
				}
			}

			setState(120);
			match(RPAREN);
			setState(121);
			match(LBRACE);
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288230376151711750L) != 0)) {
				{
				{
				setState(122);
				classBody();
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(128);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBodyContext extends ParserRuleContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public DefineVariableContext defineVariable() {
			return getRuleContext(DefineVariableContext.class,0);
		}
		public DefineConstVariableContext defineConstVariable() {
			return getRuleContext(DefineConstVariableContext.class,0);
		}
		public ConstructorFunctionContext constructorFunction() {
			return getRuleContext(ConstructorFunctionContext.class,0);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_classBody);
		try {
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				function();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				defineVariable();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(132);
				defineConstVariable();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(133);
				constructorFunction();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefineVariableContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(RuleParser.DEF, 0); }
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(RuleParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DefineVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefineVariableContext defineVariable() throws RecognitionException {
		DefineVariableContext _localctx = new DefineVariableContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_defineVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(DEF);
			setState(137);
			match(ID);
			setState(138);
			match(ASSIGN);
			setState(139);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefineConstVariableContext extends ParserRuleContext {
		public TerminalNode CONST() { return getToken(RuleParser.CONST, 0); }
		public TerminalNode DEF() { return getToken(RuleParser.DEF, 0); }
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(RuleParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DefineConstVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineConstVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineConstVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineConstVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineConstVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefineConstVariableContext defineConstVariable() throws RecognitionException {
		DefineConstVariableContext _localctx = new DefineConstVariableContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_defineConstVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(CONST);
			setState(142);
			match(DEF);
			setState(143);
			match(ID);
			setState(144);
			match(ASSIGN);
			setState(145);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(RuleParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(RuleParser.RBRACK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(RuleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(RuleParser.COMMA, i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(LBRACK);
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212125734L) != 0)) {
				{
				setState(148);
				expression(0);
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(149);
					match(COMMA);
					setState(150);
					expression(0);
					}
					}
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(158);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfExpressionContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(RuleParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(RuleParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RuleParser.RPAREN, 0); }
		public List<TerminalNode> LBRACE() { return getTokens(RuleParser.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(RuleParser.LBRACE, i);
		}
		public List<TerminalNode> RBRACE() { return getTokens(RuleParser.RBRACE); }
		public TerminalNode RBRACE(int i) {
			return getToken(RuleParser.RBRACE, i);
		}
		public List<IfThenBodyContext> ifThenBody() {
			return getRuleContexts(IfThenBodyContext.class);
		}
		public IfThenBodyContext ifThenBody(int i) {
			return getRuleContext(IfThenBodyContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(RuleParser.ELSE, 0); }
		public List<IfElseBodyContext> ifElseBody() {
			return getRuleContexts(IfElseBodyContext.class);
		}
		public IfElseBodyContext ifElseBody(int i) {
			return getRuleContext(IfElseBodyContext.class,i);
		}
		public IfExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterIfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitIfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitIfExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfExpressionContext ifExpression() throws RecognitionException {
		IfExpressionContext _localctx = new IfExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ifExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(IF);
			setState(161);
			match(LPAREN);
			setState(162);
			expression(0);
			setState(163);
			match(RPAREN);
			setState(164);
			match(LBRACE);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212126374L) != 0)) {
				{
				{
				setState(165);
				ifThenBody();
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
			match(RBRACE);
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(172);
				match(ELSE);
				setState(173);
				match(LBRACE);
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212126374L) != 0)) {
					{
					{
					setState(174);
					ifElseBody();
					}
					}
					setState(179);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(180);
				match(RBRACE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfThenBodyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BreakExpressionContext breakExpression() {
			return getRuleContext(BreakExpressionContext.class,0);
		}
		public ReturnExpressionContext returnExpression() {
			return getRuleContext(ReturnExpressionContext.class,0);
		}
		public ReturnEmptyExpressionContext returnEmptyExpression() {
			return getRuleContext(ReturnEmptyExpressionContext.class,0);
		}
		public IfThenBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifThenBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterIfThenBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitIfThenBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitIfThenBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfThenBodyContext ifThenBody() throws RecognitionException {
		IfThenBodyContext _localctx = new IfThenBodyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ifThenBody);
		try {
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(183);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(184);
				breakExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(185);
				returnExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				returnEmptyExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfElseBodyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BreakExpressionContext breakExpression() {
			return getRuleContext(BreakExpressionContext.class,0);
		}
		public ReturnExpressionContext returnExpression() {
			return getRuleContext(ReturnExpressionContext.class,0);
		}
		public ReturnEmptyExpressionContext returnEmptyExpression() {
			return getRuleContext(ReturnEmptyExpressionContext.class,0);
		}
		public IfElseBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifElseBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterIfElseBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitIfElseBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitIfElseBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfElseBodyContext ifElseBody() throws RecognitionException {
		IfElseBodyContext _localctx = new IfElseBodyContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifElseBody);
		try {
			setState(193);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				breakExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(191);
				returnExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(192);
				returnEmptyExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LoopBodyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ContinueExpressionContext continueExpression() {
			return getRuleContext(ContinueExpressionContext.class,0);
		}
		public BreakExpressionContext breakExpression() {
			return getRuleContext(BreakExpressionContext.class,0);
		}
		public ReturnExpressionContext returnExpression() {
			return getRuleContext(ReturnExpressionContext.class,0);
		}
		public ReturnEmptyExpressionContext returnEmptyExpression() {
			return getRuleContext(ReturnEmptyExpressionContext.class,0);
		}
		public LoopBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterLoopBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitLoopBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitLoopBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopBodyContext loopBody() throws RecognitionException {
		LoopBodyContext _localctx = new LoopBodyContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_loopBody);
		try {
			setState(200);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(195);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(196);
				continueExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(197);
				breakExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(198);
				returnExpression();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(199);
				returnEmptyExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnEmptyExpressionContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(RuleParser.RETURN, 0); }
		public ReturnEmptyExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnEmptyExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterReturnEmptyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitReturnEmptyExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitReturnEmptyExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnEmptyExpressionContext returnEmptyExpression() throws RecognitionException {
		ReturnEmptyExpressionContext _localctx = new ReturnEmptyExpressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_returnEmptyExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(RETURN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnExpressionContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(RuleParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterReturnExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitReturnExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitReturnExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnExpressionContext returnExpression() throws RecognitionException {
		ReturnExpressionContext _localctx = new ReturnExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_returnExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(RETURN);
			setState(205);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BreakExpressionContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(RuleParser.BREAK, 0); }
		public BreakExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterBreakExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitBreakExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitBreakExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakExpressionContext breakExpression() throws RecognitionException {
		BreakExpressionContext _localctx = new BreakExpressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_breakExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(BREAK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContinueExpressionContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(RuleParser.CONTINUE, 0); }
		public ContinueExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterContinueExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitContinueExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitContinueExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueExpressionContext continueExpression() throws RecognitionException {
		ContinueExpressionContext _localctx = new ContinueExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_continueExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(CONTINUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodCallExpressionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(RuleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(RuleParser.RPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(RuleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(RuleParser.COMMA, i);
		}
		public MethodCallExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCallExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterMethodCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitMethodCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitMethodCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodCallExpressionContext methodCallExpression() throws RecognitionException {
		MethodCallExpressionContext _localctx = new MethodCallExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_methodCallExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(ID);
			setState(212);
			match(LPAREN);
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212125734L) != 0)) {
				{
				setState(213);
				expression(0);
				setState(218);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(214);
					match(COMMA);
					setState(215);
					expression(0);
					}
					}
					setState(220);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(223);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesizedExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(RuleParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RuleParser.RPAREN, 0); }
		public ParenthesizedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesizedExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitParenthesizedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitParenthesizedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParenthesizedExpressionContext parenthesizedExpression() throws RecognitionException {
		ParenthesizedExpressionContext _localctx = new ParenthesizedExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_parenthesizedExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(LPAREN);
			setState(226);
			expression(0);
			setState(227);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefineExpressionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public TerminalNode ID_REF() { return getToken(RuleParser.ID_REF, 0); }
		public DefineExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefineExpressionContext defineExpression() throws RecognitionException {
		DefineExpressionContext _localctx = new DefineExpressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_defineExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==ID_REF) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeExpressionContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(RuleParser.NUMBER, 0); }
		public TerminalNode NUMBER_FLOAT() { return getToken(RuleParser.NUMBER_FLOAT, 0); }
		public TerminalNode STRING() { return getToken(RuleParser.STRING, 0); }
		public TerminalNode BOOLEAN() { return getToken(RuleParser.BOOLEAN, 0); }
		public TypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeExpressionContext typeExpression() throws RecognitionException {
		TypeExpressionContext _localctx = new TypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_typeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 61440L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberAutoIncreaseReduceExpressionContext extends ParserRuleContext {
		public NumberAutoIncreaseReduceExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberAutoIncreaseReduceExpression; }
	 
		public NumberAutoIncreaseReduceExpressionContext() { }
		public void copyFrom(NumberAutoIncreaseReduceExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberAutoReduceExpressionContext extends NumberAutoIncreaseReduceExpressionContext {
		public DefineExpressionContext defineExpression() {
			return getRuleContext(DefineExpressionContext.class,0);
		}
		public TerminalNode DEC() { return getToken(RuleParser.DEC, 0); }
		public NumberAutoReduceExpressionContext(NumberAutoIncreaseReduceExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterNumberAutoReduceExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitNumberAutoReduceExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitNumberAutoReduceExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberAutoIncreaseExpressionContext extends NumberAutoIncreaseReduceExpressionContext {
		public DefineExpressionContext defineExpression() {
			return getRuleContext(DefineExpressionContext.class,0);
		}
		public TerminalNode INC() { return getToken(RuleParser.INC, 0); }
		public NumberAutoIncreaseExpressionContext(NumberAutoIncreaseReduceExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterNumberAutoIncreaseExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitNumberAutoIncreaseExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitNumberAutoIncreaseExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberAutoIncreaseReduceExpressionContext numberAutoIncreaseReduceExpression() throws RecognitionException {
		NumberAutoIncreaseReduceExpressionContext _localctx = new NumberAutoIncreaseReduceExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_numberAutoIncreaseReduceExpression);
		try {
			setState(239);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new NumberAutoIncreaseExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(233);
				defineExpression();
				setState(234);
				match(INC);
				}
				break;
			case 2:
				_localctx = new NumberAutoReduceExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(236);
				defineExpression();
				setState(237);
				match(DEC);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConstVariableExpressionContext extends ExpressionContext {
		public DefineConstVariableContext defineConstVariable() {
			return getRuleContext(DefineConstVariableContext.class,0);
		}
		public ConstVariableExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterConstVariableExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitConstVariableExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitConstVariableExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(RuleParser.ASSIGN, 0); }
		public AssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallMethodExpressionContext extends ExpressionContext {
		public MethodCallExpressionContext methodCallExpression() {
			return getRuleContext(MethodCallExpressionContext.class,0);
		}
		public CallMethodExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterCallMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitCallMethodExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitCallMethodExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ObjectPropertiesExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(RuleParser.DOT, 0); }
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public ObjectPropertiesExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterObjectPropertiesExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitObjectPropertiesExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitObjectPropertiesExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulDivYuExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(RuleParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(RuleParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(RuleParser.MOD, 0); }
		public MulDivYuExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterMulDivYuExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitMulDivYuExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitMulDivYuExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefineRangeExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ARROW() { return getToken(RuleParser.ARROW, 0); }
		public DefineRangeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineRangeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineRangeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineRangeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ModAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MOD_ASSIGN() { return getToken(RuleParser.MOD_ASSIGN, 0); }
		public ModAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterModAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitModAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitModAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberAutoExpressionContext extends ExpressionContext {
		public NumberAutoIncreaseReduceExpressionContext numberAutoIncreaseReduceExpression() {
			return getRuleContext(NumberAutoIncreaseReduceExpressionContext.class,0);
		}
		public NumberAutoExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterNumberAutoExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitNumberAutoExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitNumberAutoExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompareExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(RuleParser.AND, 0); }
		public TerminalNode OR() { return getToken(RuleParser.OR, 0); }
		public TerminalNode EQUAL() { return getToken(RuleParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(RuleParser.NOTEQUAL, 0); }
		public TerminalNode GE() { return getToken(RuleParser.GE, 0); }
		public TerminalNode LE() { return getToken(RuleParser.LE, 0); }
		public TerminalNode LT() { return getToken(RuleParser.LT, 0); }
		public TerminalNode GT() { return getToken(RuleParser.GT, 0); }
		public CompareExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterCompareExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitCompareExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitCompareExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddSubExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(RuleParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(RuleParser.SUB, 0); }
		public AddSubExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterAddSubExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitAddSubExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitAddSubExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SUB_ASSIGN() { return getToken(RuleParser.SUB_ASSIGN, 0); }
		public SubAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterSubAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitSubAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitSubAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DivAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode DIV_ASSIGN() { return getToken(RuleParser.DIV_ASSIGN, 0); }
		public DivAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDivAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDivAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDivAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD_ASSIGN() { return getToken(RuleParser.ADD_ASSIGN, 0); }
		public AddAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterAddAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitAddAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitAddAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BitOperationExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BIT_LEFT() { return getToken(RuleParser.BIT_LEFT, 0); }
		public TerminalNode BIT_RIGHT() { return getToken(RuleParser.BIT_RIGHT, 0); }
		public TerminalNode BITAND() { return getToken(RuleParser.BITAND, 0); }
		public TerminalNode BITOR() { return getToken(RuleParser.BITOR, 0); }
		public TerminalNode CARET() { return getToken(RuleParser.CARET, 0); }
		public BitOperationExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterBitOperationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitBitOperationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitBitOperationExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefineTypeExpressionContext extends ExpressionContext {
		public TypeExpressionContext typeExpression() {
			return getRuleContext(TypeExpressionContext.class,0);
		}
		public DefineTypeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefineNameExpressionContext extends ExpressionContext {
		public DefineExpressionContext defineExpression() {
			return getRuleContext(DefineExpressionContext.class,0);
		}
		public DefineNameExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineNameExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineNameExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineNameExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PriorityExpressionContext extends ExpressionContext {
		public ParenthesizedExpressionContext parenthesizedExpression() {
			return getRuleContext(ParenthesizedExpressionContext.class,0);
		}
		public PriorityExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterPriorityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitPriorityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitPriorityExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefineIfExpressionContext extends ExpressionContext {
		public IfExpressionContext ifExpression() {
			return getRuleContext(IfExpressionContext.class,0);
		}
		public DefineIfExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterDefineIfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitDefineIfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitDefineIfExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR_ASSIGN() { return getToken(RuleParser.OR_ASSIGN, 0); }
		public OrAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterOrAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitOrAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitOrAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VariableExpressionContext extends ExpressionContext {
		public DefineVariableContext defineVariable() {
			return getRuleContext(DefineVariableContext.class,0);
		}
		public VariableExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterVariableExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitVariableExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitVariableExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND_ASSIGN() { return getToken(RuleParser.AND_ASSIGN, 0); }
		public AndAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterAndAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitAndAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitAndAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL_ASSIGN() { return getToken(RuleParser.MUL_ASSIGN, 0); }
		public MulAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterMulAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitMulAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitMulAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LoopExpressionContext extends ExpressionContext {
		public TerminalNode LOOP() { return getToken(RuleParser.LOOP, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TO() { return getToken(RuleParser.TO, 0); }
		public TerminalNode ID() { return getToken(RuleParser.ID, 0); }
		public TerminalNode LBRACE() { return getToken(RuleParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(RuleParser.RBRACE, 0); }
		public List<LoopBodyContext> loopBody() {
			return getRuleContexts(LoopBodyContext.class);
		}
		public LoopBodyContext loopBody(int i) {
			return getRuleContext(LoopBodyContext.class,i);
		}
		public LoopExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterLoopExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitLoopExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitLoopExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ObjectMethodCallExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(RuleParser.DOT, 0); }
		public MethodCallExpressionContext methodCallExpression() {
			return getRuleContext(MethodCallExpressionContext.class,0);
		}
		public ObjectMethodCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterObjectMethodCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitObjectMethodCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitObjectMethodCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayExpressionContext extends ExpressionContext {
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public ArrayExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterArrayExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitArrayExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitArrayExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayAccessExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(RuleParser.LBRACK, 0); }
		public TerminalNode NUMBER() { return getToken(RuleParser.NUMBER, 0); }
		public TerminalNode RBRACK() { return getToken(RuleParser.RBRACK, 0); }
		public ArrayAccessExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterArrayAccessExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitArrayAccessExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitArrayAccessExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XorAssignExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode XOR_ASSIGN() { return getToken(RuleParser.XOR_ASSIGN, 0); }
		public XorAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterXorAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitXorAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitXorAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SignedExpressionContext extends ExpressionContext {
		public TerminalNode SUB() { return getToken(RuleParser.SUB, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SignedExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).enterSignedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RuleParserListener ) ((RuleParserListener)listener).exitSignedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RuleParserVisitor ) return ((RuleParserVisitor<? extends T>)visitor).visitSignedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				_localctx = new CallMethodExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(242);
				methodCallExpression();
				}
				break;
			case 2:
				{
				_localctx = new PriorityExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(243);
				parenthesizedExpression();
				}
				break;
			case 3:
				{
				_localctx = new DefineNameExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(244);
				defineExpression();
				}
				break;
			case 4:
				{
				_localctx = new DefineTypeExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(245);
				typeExpression();
				}
				break;
			case 5:
				{
				_localctx = new ArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(246);
				array();
				}
				break;
			case 6:
				{
				_localctx = new VariableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(247);
				defineVariable();
				}
				break;
			case 7:
				{
				_localctx = new ConstVariableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(248);
				defineConstVariable();
				}
				break;
			case 8:
				{
				_localctx = new DefineIfExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(249);
				ifExpression();
				}
				break;
			case 9:
				{
				_localctx = new LoopExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(250);
				match(LOOP);
				setState(251);
				expression(0);
				setState(252);
				match(TO);
				setState(253);
				match(ID);
				setState(254);
				match(LBRACE);
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 864691678212126630L) != 0)) {
					{
					{
					setState(255);
					loopBody();
					}
					}
					setState(260);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(261);
				match(RBRACE);
				}
				break;
			case 10:
				{
				_localctx = new NumberAutoExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(263);
				numberAutoIncreaseReduceExpression();
				}
				break;
			case 11:
				{
				_localctx = new SignedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(264);
				match(SUB);
				setState(265);
				expression(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(322);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(320);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new BitOperationExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(268);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(269);
						((BitOperationExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 452998790643712L) != 0)) ) {
							((BitOperationExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(270);
						expression(20);
						}
						break;
					case 2:
						{
						_localctx = new MulDivYuExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(271);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(272);
						((MulDivYuExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 38482906972160L) != 0)) ) {
							((MulDivYuExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(273);
						expression(19);
						}
						break;
					case 3:
						{
						_localctx = new AddSubExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(274);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(275);
						((AddSubExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((AddSubExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(276);
						expression(18);
						}
						break;
					case 4:
						{
						_localctx = new AssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(277);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(278);
						match(ASSIGN);
						setState(279);
						expression(17);
						}
						break;
					case 5:
						{
						_localctx = new AddAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(280);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(281);
						((AddAssignExpressionContext)_localctx).op = match(ADD_ASSIGN);
						setState(282);
						expression(16);
						}
						break;
					case 6:
						{
						_localctx = new SubAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(283);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(284);
						((SubAssignExpressionContext)_localctx).op = match(SUB_ASSIGN);
						setState(285);
						expression(15);
						}
						break;
					case 7:
						{
						_localctx = new MulAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(286);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(287);
						((MulAssignExpressionContext)_localctx).op = match(MUL_ASSIGN);
						setState(288);
						expression(14);
						}
						break;
					case 8:
						{
						_localctx = new DivAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(289);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(290);
						((DivAssignExpressionContext)_localctx).op = match(DIV_ASSIGN);
						setState(291);
						expression(13);
						}
						break;
					case 9:
						{
						_localctx = new AndAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(292);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(293);
						((AndAssignExpressionContext)_localctx).op = match(AND_ASSIGN);
						setState(294);
						expression(12);
						}
						break;
					case 10:
						{
						_localctx = new OrAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(295);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(296);
						((OrAssignExpressionContext)_localctx).op = match(OR_ASSIGN);
						setState(297);
						expression(11);
						}
						break;
					case 11:
						{
						_localctx = new XorAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(298);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(299);
						((XorAssignExpressionContext)_localctx).op = match(XOR_ASSIGN);
						setState(300);
						expression(10);
						}
						break;
					case 12:
						{
						_localctx = new ModAssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(301);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(302);
						((ModAssignExpressionContext)_localctx).op = match(MOD_ASSIGN);
						setState(303);
						expression(9);
						}
						break;
					case 13:
						{
						_localctx = new CompareExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(304);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(305);
						((CompareExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 67746398208L) != 0)) ) {
							((CompareExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(306);
						expression(8);
						}
						break;
					case 14:
						{
						_localctx = new DefineRangeExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(307);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(308);
						match(ARROW);
						setState(309);
						expression(5);
						}
						break;
					case 15:
						{
						_localctx = new ObjectMethodCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(310);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(311);
						match(DOT);
						setState(312);
						methodCallExpression();
						}
						break;
					case 16:
						{
						_localctx = new ArrayAccessExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(313);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(314);
						match(LBRACK);
						setState(315);
						match(NUMBER);
						setState(316);
						match(RBRACK);
						}
						break;
					case 17:
						{
						_localctx = new ObjectPropertiesExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(317);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(318);
						match(DOT);
						setState(319);
						match(ID);
						}
						break;
					}
					} 
				}
				setState(324);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 26:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 19);
		case 1:
			return precpred(_ctx, 18);
		case 2:
			return precpred(_ctx, 17);
		case 3:
			return precpred(_ctx, 16);
		case 4:
			return precpred(_ctx, 15);
		case 5:
			return precpred(_ctx, 14);
		case 6:
			return precpred(_ctx, 13);
		case 7:
			return precpred(_ctx, 12);
		case 8:
			return precpred(_ctx, 11);
		case 9:
			return precpred(_ctx, 10);
		case 10:
			return precpred(_ctx, 9);
		case 11:
			return precpred(_ctx, 8);
		case 12:
			return precpred(_ctx, 7);
		case 13:
			return precpred(_ctx, 4);
		case 14:
			return precpred(_ctx, 26);
		case 15:
			return precpred(_ctx, 22);
		case 16:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001?\u0146\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0001\u0000\u0005\u0000"+
		"8\b\u0000\n\u0000\f\u0000;\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001@\b\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u0003H\b\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0005\u0003M\b\u0003\n\u0003\f\u0003P\t\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004W\b\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\\\b\u0004\n\u0004\f\u0004"+
		"_\t\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006h\b\u0006\n\u0006\f\u0006k\t\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007p\b\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0003\bw\b\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b|\b\b\n\b\f\b\u007f\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u0087\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0005\f\u0098\b\f\n\f\f\f\u009b\t\f\u0003\f\u009d\b"+
		"\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005"+
		"\r\u00a7\b\r\n\r\f\r\u00aa\t\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r"+
		"\u00b0\b\r\n\r\f\r\u00b3\t\r\u0001\r\u0003\r\u00b6\b\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00bc\b\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00c2\b\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00c9\b\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0005\u0015\u00d9\b\u0015\n\u0015\f\u0015\u00dc\t\u0015"+
		"\u0003\u0015\u00de\b\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u00f0\b\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a"+
		"\u0101\b\u001a\n\u001a\f\u001a\u0104\t\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u010b\b\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u0141\b\u001a\n\u001a\f\u001a"+
		"\u0144\t\u001a\u0001\u001a\u0000\u00014\u001b\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,."+
		"024\u0000\u0006\u0001\u0000:;\u0001\u0000\f\u000f\u0002\u0000*,/0\u0002"+
		"\u0000()--\u0001\u0000&\'\u0002\u0000\u0019\u001a\u001e#\u0167\u00009"+
		"\u0001\u0000\u0000\u0000\u0002?\u0001\u0000\u0000\u0000\u0004A\u0001\u0000"+
		"\u0000\u0000\u0006C\u0001\u0000\u0000\u0000\bS\u0001\u0000\u0000\u0000"+
		"\nb\u0001\u0000\u0000\u0000\fd\u0001\u0000\u0000\u0000\u000eo\u0001\u0000"+
		"\u0000\u0000\u0010q\u0001\u0000\u0000\u0000\u0012\u0086\u0001\u0000\u0000"+
		"\u0000\u0014\u0088\u0001\u0000\u0000\u0000\u0016\u008d\u0001\u0000\u0000"+
		"\u0000\u0018\u0093\u0001\u0000\u0000\u0000\u001a\u00a0\u0001\u0000\u0000"+
		"\u0000\u001c\u00bb\u0001\u0000\u0000\u0000\u001e\u00c1\u0001\u0000\u0000"+
		"\u0000 \u00c8\u0001\u0000\u0000\u0000\"\u00ca\u0001\u0000\u0000\u0000"+
		"$\u00cc\u0001\u0000\u0000\u0000&\u00cf\u0001\u0000\u0000\u0000(\u00d1"+
		"\u0001\u0000\u0000\u0000*\u00d3\u0001\u0000\u0000\u0000,\u00e1\u0001\u0000"+
		"\u0000\u0000.\u00e5\u0001\u0000\u0000\u00000\u00e7\u0001\u0000\u0000\u0000"+
		"2\u00ef\u0001\u0000\u0000\u00004\u010a\u0001\u0000\u0000\u000068\u0003"+
		"\u0002\u0001\u000076\u0001\u0000\u0000\u00008;\u0001\u0000\u0000\u0000"+
		"97\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:\u0001\u0001\u0000"+
		"\u0000\u0000;9\u0001\u0000\u0000\u0000<@\u0003\u0006\u0003\u0000=@\u0003"+
		"\u0010\b\u0000>@\u00034\u001a\u0000?<\u0001\u0000\u0000\u0000?=\u0001"+
		"\u0000\u0000\u0000?>\u0001\u0000\u0000\u0000@\u0003\u0001\u0000\u0000"+
		"\u0000AB\u0005:\u0000\u0000B\u0005\u0001\u0000\u0000\u0000CD\u0005\u0002"+
		"\u0000\u0000DE\u0003\u0004\u0002\u0000EG\u0005\u0010\u0000\u0000FH\u0003"+
		"\f\u0006\u0000GF\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000HI\u0001"+
		"\u0000\u0000\u0000IJ\u0005\u0011\u0000\u0000JN\u0005\u0012\u0000\u0000"+
		"KM\u0003\u000e\u0007\u0000LK\u0001\u0000\u0000\u0000MP\u0001\u0000\u0000"+
		"\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OQ\u0001\u0000"+
		"\u0000\u0000PN\u0001\u0000\u0000\u0000QR\u0005\u0013\u0000\u0000R\u0007"+
		"\u0001\u0000\u0000\u0000ST\u0003\u0004\u0002\u0000TV\u0005\u0010\u0000"+
		"\u0000UW\u0003\f\u0006\u0000VU\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000"+
		"\u0000WX\u0001\u0000\u0000\u0000XY\u0005\u0011\u0000\u0000Y]\u0005\u0012"+
		"\u0000\u0000Z\\\u0003\u000e\u0007\u0000[Z\u0001\u0000\u0000\u0000\\_\u0001"+
		"\u0000\u0000\u0000][\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000"+
		"^`\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000`a\u0005\u0013\u0000"+
		"\u0000a\t\u0001\u0000\u0000\u0000bc\u0005:\u0000\u0000c\u000b\u0001\u0000"+
		"\u0000\u0000di\u0003\n\u0005\u0000ef\u0005\u0016\u0000\u0000fh\u0003\n"+
		"\u0005\u0000ge\u0001\u0000\u0000\u0000hk\u0001\u0000\u0000\u0000ig\u0001"+
		"\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000j\r\u0001\u0000\u0000\u0000"+
		"ki\u0001\u0000\u0000\u0000lp\u00034\u001a\u0000mp\u0003$\u0012\u0000n"+
		"p\u0003\"\u0011\u0000ol\u0001\u0000\u0000\u0000om\u0001\u0000\u0000\u0000"+
		"on\u0001\u0000\u0000\u0000p\u000f\u0001\u0000\u0000\u0000qr\u0005\u0002"+
		"\u0000\u0000rs\u0005\u0003\u0000\u0000st\u0005:\u0000\u0000tv\u0005\u0010"+
		"\u0000\u0000uw\u0003\f\u0006\u0000vu\u0001\u0000\u0000\u0000vw\u0001\u0000"+
		"\u0000\u0000wx\u0001\u0000\u0000\u0000xy\u0005\u0011\u0000\u0000y}\u0005"+
		"\u0012\u0000\u0000z|\u0003\u0012\t\u0000{z\u0001\u0000\u0000\u0000|\u007f"+
		"\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000"+
		"\u0000~\u0080\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0005\u0013\u0000\u0000\u0081\u0011\u0001\u0000\u0000\u0000\u0082"+
		"\u0087\u0003\u0006\u0003\u0000\u0083\u0087\u0003\u0014\n\u0000\u0084\u0087"+
		"\u0003\u0016\u000b\u0000\u0085\u0087\u0003\b\u0004\u0000\u0086\u0082\u0001"+
		"\u0000\u0000\u0000\u0086\u0083\u0001\u0000\u0000\u0000\u0086\u0084\u0001"+
		"\u0000\u0000\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\u0013\u0001"+
		"\u0000\u0000\u0000\u0088\u0089\u0005\u0002\u0000\u0000\u0089\u008a\u0005"+
		":\u0000\u0000\u008a\u008b\u0005\u0018\u0000\u0000\u008b\u008c\u00034\u001a"+
		"\u0000\u008c\u0015\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u0001\u0000"+
		"\u0000\u008e\u008f\u0005\u0002\u0000\u0000\u008f\u0090\u0005:\u0000\u0000"+
		"\u0090\u0091\u0005\u0018\u0000\u0000\u0091\u0092\u00034\u001a\u0000\u0092"+
		"\u0017\u0001\u0000\u0000\u0000\u0093\u009c\u0005\u0014\u0000\u0000\u0094"+
		"\u0099\u00034\u001a\u0000\u0095\u0096\u0005\u0016\u0000\u0000\u0096\u0098"+
		"\u00034\u001a\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0098\u009b\u0001"+
		"\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099\u009a\u0001"+
		"\u0000\u0000\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b\u0099\u0001"+
		"\u0000\u0000\u0000\u009c\u0094\u0001\u0000\u0000\u0000\u009c\u009d\u0001"+
		"\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009f\u0005"+
		"\u0015\u0000\u0000\u009f\u0019\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005"+
		"\n\u0000\u0000\u00a1\u00a2\u0005\u0010\u0000\u0000\u00a2\u00a3\u00034"+
		"\u001a\u0000\u00a3\u00a4\u0005\u0011\u0000\u0000\u00a4\u00a8\u0005\u0012"+
		"\u0000\u0000\u00a5\u00a7\u0003\u001c\u000e\u0000\u00a6\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00ab\u0001\u0000"+
		"\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab\u00b5\u0005\u0013"+
		"\u0000\u0000\u00ac\u00ad\u0005\u000b\u0000\u0000\u00ad\u00b1\u0005\u0012"+
		"\u0000\u0000\u00ae\u00b0\u0003\u001e\u000f\u0000\u00af\u00ae\u0001\u0000"+
		"\u0000\u0000\u00b0\u00b3\u0001\u0000\u0000\u0000\u00b1\u00af\u0001\u0000"+
		"\u0000\u0000\u00b1\u00b2\u0001\u0000\u0000\u0000\u00b2\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000\u00b4\u00b6\u0005\u0013"+
		"\u0000\u0000\u00b5\u00ac\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b6\u001b\u0001\u0000\u0000\u0000\u00b7\u00bc\u00034\u001a"+
		"\u0000\u00b8\u00bc\u0003&\u0013\u0000\u00b9\u00bc\u0003$\u0012\u0000\u00ba"+
		"\u00bc\u0003\"\u0011\u0000\u00bb\u00b7\u0001\u0000\u0000\u0000\u00bb\u00b8"+
		"\u0001\u0000\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bb\u00ba"+
		"\u0001\u0000\u0000\u0000\u00bc\u001d\u0001\u0000\u0000\u0000\u00bd\u00c2"+
		"\u00034\u001a\u0000\u00be\u00c2\u0003&\u0013\u0000\u00bf\u00c2\u0003$"+
		"\u0012\u0000\u00c0\u00c2\u0003\"\u0011\u0000\u00c1\u00bd\u0001\u0000\u0000"+
		"\u0000\u00c1\u00be\u0001\u0000\u0000\u0000\u00c1\u00bf\u0001\u0000\u0000"+
		"\u0000\u00c1\u00c0\u0001\u0000\u0000\u0000\u00c2\u001f\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c9\u00034\u001a\u0000\u00c4\u00c9\u0003(\u0014\u0000\u00c5"+
		"\u00c9\u0003&\u0013\u0000\u00c6\u00c9\u0003$\u0012\u0000\u00c7\u00c9\u0003"+
		"\"\u0011\u0000\u00c8\u00c3\u0001\u0000\u0000\u0000\u00c8\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c5\u0001\u0000\u0000\u0000\u00c8\u00c6\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c7\u0001\u0000\u0000\u0000\u00c9!\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cb\u0005\t\u0000\u0000\u00cb#\u0001\u0000\u0000\u0000"+
		"\u00cc\u00cd\u0005\t\u0000\u0000\u00cd\u00ce\u00034\u001a\u0000\u00ce"+
		"%\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005\u0007\u0000\u0000\u00d0\'"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\b\u0000\u0000\u00d2)\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0005:\u0000\u0000\u00d4\u00dd\u0005\u0010"+
		"\u0000\u0000\u00d5\u00da\u00034\u001a\u0000\u00d6\u00d7\u0005\u0016\u0000"+
		"\u0000\u00d7\u00d9\u00034\u001a\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000"+
		"\u00d9\u00dc\u0001\u0000\u0000\u0000\u00da\u00d8\u0001\u0000\u0000\u0000"+
		"\u00da\u00db\u0001\u0000\u0000\u0000\u00db\u00de\u0001\u0000\u0000\u0000"+
		"\u00dc\u00da\u0001\u0000\u0000\u0000\u00dd\u00d5\u0001\u0000\u0000\u0000"+
		"\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u00df\u0001\u0000\u0000\u0000"+
		"\u00df\u00e0\u0005\u0011\u0000\u0000\u00e0+\u0001\u0000\u0000\u0000\u00e1"+
		"\u00e2\u0005\u0010\u0000\u0000\u00e2\u00e3\u00034\u001a\u0000\u00e3\u00e4"+
		"\u0005\u0011\u0000\u0000\u00e4-\u0001\u0000\u0000\u0000\u00e5\u00e6\u0007"+
		"\u0000\u0000\u0000\u00e6/\u0001\u0000\u0000\u0000\u00e7\u00e8\u0007\u0001"+
		"\u0000\u0000\u00e81\u0001\u0000\u0000\u0000\u00e9\u00ea\u0003.\u0017\u0000"+
		"\u00ea\u00eb\u0005$\u0000\u0000\u00eb\u00f0\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ed\u0003.\u0017\u0000\u00ed\u00ee\u0005%\u0000\u0000\u00ee\u00f0\u0001"+
		"\u0000\u0000\u0000\u00ef\u00e9\u0001\u0000\u0000\u0000\u00ef\u00ec\u0001"+
		"\u0000\u0000\u0000\u00f03\u0001\u0000\u0000\u0000\u00f1\u00f2\u0006\u001a"+
		"\uffff\uffff\u0000\u00f2\u010b\u0003*\u0015\u0000\u00f3\u010b\u0003,\u0016"+
		"\u0000\u00f4\u010b\u0003.\u0017\u0000\u00f5\u010b\u00030\u0018\u0000\u00f6"+
		"\u010b\u0003\u0018\f\u0000\u00f7\u010b\u0003\u0014\n\u0000\u00f8\u010b"+
		"\u0003\u0016\u000b\u0000\u00f9\u010b\u0003\u001a\r\u0000\u00fa\u00fb\u0005"+
		"\u0005\u0000\u0000\u00fb\u00fc\u00034\u001a\u0000\u00fc\u00fd\u0005\u0006"+
		"\u0000\u0000\u00fd\u00fe\u0005:\u0000\u0000\u00fe\u0102\u0005\u0012\u0000"+
		"\u0000\u00ff\u0101\u0003 \u0010\u0000\u0100\u00ff\u0001\u0000\u0000\u0000"+
		"\u0101\u0104\u0001\u0000\u0000\u0000\u0102\u0100\u0001\u0000\u0000\u0000"+
		"\u0102\u0103\u0001\u0000\u0000\u0000\u0103\u0105\u0001\u0000\u0000\u0000"+
		"\u0104\u0102\u0001\u0000\u0000\u0000\u0105\u0106\u0005\u0013\u0000\u0000"+
		"\u0106\u010b\u0001\u0000\u0000\u0000\u0107\u010b\u00032\u0019\u0000\u0108"+
		"\u0109\u0005\'\u0000\u0000\u0109\u010b\u00034\u001a\u0001\u010a\u00f1"+
		"\u0001\u0000\u0000\u0000\u010a\u00f3\u0001\u0000\u0000\u0000\u010a\u00f4"+
		"\u0001\u0000\u0000\u0000\u010a\u00f5\u0001\u0000\u0000\u0000\u010a\u00f6"+
		"\u0001\u0000\u0000\u0000\u010a\u00f7\u0001\u0000\u0000\u0000\u010a\u00f8"+
		"\u0001\u0000\u0000\u0000\u010a\u00f9\u0001\u0000\u0000\u0000\u010a\u00fa"+
		"\u0001\u0000\u0000\u0000\u010a\u0107\u0001\u0000\u0000\u0000\u010a\u0108"+
		"\u0001\u0000\u0000\u0000\u010b\u0142\u0001\u0000\u0000\u0000\u010c\u010d"+
		"\n\u0013\u0000\u0000\u010d\u010e\u0007\u0002\u0000\u0000\u010e\u0141\u0003"+
		"4\u001a\u0014\u010f\u0110\n\u0012\u0000\u0000\u0110\u0111\u0007\u0003"+
		"\u0000\u0000\u0111\u0141\u00034\u001a\u0013\u0112\u0113\n\u0011\u0000"+
		"\u0000\u0113\u0114\u0007\u0004\u0000\u0000\u0114\u0141\u00034\u001a\u0012"+
		"\u0115\u0116\n\u0010\u0000\u0000\u0116\u0117\u0005\u0018\u0000\u0000\u0117"+
		"\u0141\u00034\u001a\u0011\u0118\u0119\n\u000f\u0000\u0000\u0119\u011a"+
		"\u00051\u0000\u0000\u011a\u0141\u00034\u001a\u0010\u011b\u011c\n\u000e"+
		"\u0000\u0000\u011c\u011d\u00052\u0000\u0000\u011d\u0141\u00034\u001a\u000f"+
		"\u011e\u011f\n\r\u0000\u0000\u011f\u0120\u00053\u0000\u0000\u0120\u0141"+
		"\u00034\u001a\u000e\u0121\u0122\n\f\u0000\u0000\u0122\u0123\u00054\u0000"+
		"\u0000\u0123\u0141\u00034\u001a\r\u0124\u0125\n\u000b\u0000\u0000\u0125"+
		"\u0126\u00055\u0000\u0000\u0126\u0141\u00034\u001a\f\u0127\u0128\n\n\u0000"+
		"\u0000\u0128\u0129\u00056\u0000\u0000\u0129\u0141\u00034\u001a\u000b\u012a"+
		"\u012b\n\t\u0000\u0000\u012b\u012c\u00057\u0000\u0000\u012c\u0141\u0003"+
		"4\u001a\n\u012d\u012e\n\b\u0000\u0000\u012e\u012f\u00058\u0000\u0000\u012f"+
		"\u0141\u00034\u001a\t\u0130\u0131\n\u0007\u0000\u0000\u0131\u0132\u0007"+
		"\u0005\u0000\u0000\u0132\u0141\u00034\u001a\b\u0133\u0134\n\u0004\u0000"+
		"\u0000\u0134\u0135\u0005.\u0000\u0000\u0135\u0141\u00034\u001a\u0005\u0136"+
		"\u0137\n\u001a\u0000\u0000\u0137\u0138\u0005\u0017\u0000\u0000\u0138\u0141"+
		"\u0003*\u0015\u0000\u0139\u013a\n\u0016\u0000\u0000\u013a\u013b\u0005"+
		"\u0014\u0000\u0000\u013b\u013c\u0005\r\u0000\u0000\u013c\u0141\u0005\u0015"+
		"\u0000\u0000\u013d\u013e\n\u0002\u0000\u0000\u013e\u013f\u0005\u0017\u0000"+
		"\u0000\u013f\u0141\u0005:\u0000\u0000\u0140\u010c\u0001\u0000\u0000\u0000"+
		"\u0140\u010f\u0001\u0000\u0000\u0000\u0140\u0112\u0001\u0000\u0000\u0000"+
		"\u0140\u0115\u0001\u0000\u0000\u0000\u0140\u0118\u0001\u0000\u0000\u0000"+
		"\u0140\u011b\u0001\u0000\u0000\u0000\u0140\u011e\u0001\u0000\u0000\u0000"+
		"\u0140\u0121\u0001\u0000\u0000\u0000\u0140\u0124\u0001\u0000\u0000\u0000"+
		"\u0140\u0127\u0001\u0000\u0000\u0000\u0140\u012a\u0001\u0000\u0000\u0000"+
		"\u0140\u012d\u0001\u0000\u0000\u0000\u0140\u0130\u0001\u0000\u0000\u0000"+
		"\u0140\u0133\u0001\u0000\u0000\u0000\u0140\u0136\u0001\u0000\u0000\u0000"+
		"\u0140\u0139\u0001\u0000\u0000\u0000\u0140\u013d\u0001\u0000\u0000\u0000"+
		"\u0141\u0144\u0001\u0000\u0000\u0000\u0142\u0140\u0001\u0000\u0000\u0000"+
		"\u0142\u0143\u0001\u0000\u0000\u0000\u01435\u0001\u0000\u0000\u0000\u0144"+
		"\u0142\u0001\u0000\u0000\u0000\u001a9?GNV]iov}\u0086\u0099\u009c\u00a8"+
		"\u00b1\u00b5\u00bb\u00c1\u00c8\u00da\u00dd\u00ef\u0102\u010a\u0140\u0142";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}