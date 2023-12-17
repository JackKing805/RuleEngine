// Generated from E:/Projects/JavaProjects/RuleEngine/src/main/resources/RtRuleEngine2.g4 by ANTLR 4.13.1
package com.cool.jerry.g4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class RtRuleEngine2Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		WS=10, ID=11, ID_REF=12, STRING=13, INT=14, FLOAT=15, MUL=16, DIV=17, 
		ADD=18, SUB=19;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_expression = 2, RULE_variable = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "expression", "variable"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'val'", "'='", "'.'", "'fun'", "'('", "','", "')'", "'{'", "'}'", 
			null, null, null, null, null, null, "'*'", "'/'", "'+'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "WS", "ID", 
			"ID_REF", "STRING", "INT", "FLOAT", "MUL", "DIV", "ADD", "SUB"
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
	public String getGrammarFileName() { return "RtRuleEngine2.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RtRuleEngine2Parser(TokenStream input) {
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitProgram(this);
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
			setState(9); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(8);
				statement();
				}
				}
				setState(11); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 63538L) != 0) );
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
		public List<TerminalNode> ID() { return getTokens(RtRuleEngine2Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(RtRuleEngine2Parser.ID, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FunctionStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitFunctionStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InvokeStatementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public InvokeStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitInvokeStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VariablePropertiesAssignmentStatementContext extends StatementContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode ID() { return getToken(RtRuleEngine2Parser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariablePropertiesAssignmentStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitVariablePropertiesAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VariableAssignmentStatementContext extends StatementContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableAssignmentStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitVariableAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewVariableAssignmentStatementContext extends StatementContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NewVariableAssignmentStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitNewVariableAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			setState(51);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new NewVariableAssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(13);
				match(T__0);
				setState(14);
				variable();
				setState(15);
				match(T__1);
				setState(16);
				expression(0);
				}
				break;
			case 2:
				_localctx = new VariableAssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(18);
				variable();
				setState(19);
				match(T__1);
				setState(20);
				expression(0);
				}
				break;
			case 3:
				_localctx = new VariablePropertiesAssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(22);
				variable();
				setState(23);
				match(T__2);
				setState(24);
				match(ID);
				setState(25);
				match(T__1);
				setState(26);
				expression(0);
				}
				break;
			case 4:
				_localctx = new InvokeStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(28);
				expression(0);
				}
				break;
			case 5:
				_localctx = new FunctionStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(29);
				match(T__3);
				setState(30);
				match(ID);
				setState(31);
				match(T__4);
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(32);
					match(ID);
					setState(37);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__5) {
						{
						{
						setState(33);
						match(T__5);
						setState(34);
						match(ID);
						}
						}
						setState(39);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(42);
				match(T__6);
				setState(43);
				match(T__7);
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(44);
					statement();
					}
					}
					setState(47); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 63538L) != 0) );
				setState(49);
				match(T__8);
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
	public static class AddSubExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(RtRuleEngine2Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(RtRuleEngine2Parser.SUB, 0); }
		public AddSubExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitAddSubExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MethodCallExpressionContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(RtRuleEngine2Parser.ID, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MethodCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitMethodCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntExpressionContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(RtRuleEngine2Parser.INT, 0); }
		public IntExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitIntExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringExpressionContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(RtRuleEngine2Parser.STRING, 0); }
		public StringExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitStringExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdExpressionContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(RtRuleEngine2Parser.ID, 0); }
		public IdExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitIdExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatExpressionContext extends ExpressionContext {
		public TerminalNode FLOAT() { return getToken(RtRuleEngine2Parser.FLOAT, 0); }
		public FloatExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitFloatExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitBlockExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdRefExpressionContext extends ExpressionContext {
		public TerminalNode ID_REF() { return getToken(RtRuleEngine2Parser.ID_REF, 0); }
		public IdRefExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitIdRefExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulDivExpressionContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(RtRuleEngine2Parser.MUL, 0); }
		public TerminalNode DIV() { return getToken(RtRuleEngine2Parser.DIV, 0); }
		public MulDivExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitMulDivExpression(this);
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
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new IdExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(54);
				match(ID);
				}
				break;
			case 2:
				{
				_localctx = new IdRefExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55);
				match(ID_REF);
				}
				break;
			case 3:
				{
				_localctx = new IntExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(56);
				match(INT);
				}
				break;
			case 4:
				{
				_localctx = new StringExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(57);
				match(STRING);
				}
				break;
			case 5:
				{
				_localctx = new FloatExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(58);
				match(FLOAT);
				}
				break;
			case 6:
				{
				_localctx = new MethodCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(62);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(59);
					variable();
					setState(60);
					match(T__2);
					}
					break;
				}
				setState(64);
				match(ID);
				setState(65);
				match(T__4);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63520L) != 0)) {
					{
					setState(66);
					expression(0);
					setState(71);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__5) {
						{
						{
						setState(67);
						match(T__5);
						setState(68);
						expression(0);
						}
						}
						setState(73);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(76);
				match(T__6);
				}
				break;
			case 7:
				{
				_localctx = new BlockExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(77);
				match(T__4);
				setState(78);
				expression(0);
				setState(79);
				match(T__6);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(91);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(89);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(83);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(84);
						((MulDivExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((MulDivExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(85);
						expression(10);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(86);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(87);
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
						setState(88);
						expression(9);
						}
						break;
					}
					} 
				}
				setState(93);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public TerminalNode ID_REF() { return getToken(RtRuleEngine2Parser.ID_REF, 0); }
		public TerminalNode ID() { return getToken(RtRuleEngine2Parser.ID, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RtRuleEngine2Visitor ) return ((RtRuleEngine2Visitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_variable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		case 1:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0013a\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0004\u0000\n\b"+
		"\u0000\u000b\u0000\f\u0000\u000b\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0005\u0001$\b\u0001\n\u0001\f\u0001\'\t\u0001\u0003\u0001)\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0004\u0001.\b\u0001\u000b\u0001"+
		"\f\u0001/\u0001\u0001\u0001\u0001\u0003\u00014\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u0002?\b\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0005\u0002F\b\u0002\n\u0002\f\u0002I\t"+
		"\u0002\u0003\u0002K\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u0002R\b\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002Z\b\u0002\n\u0002"+
		"\f\u0002]\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0000\u0001\u0004"+
		"\u0004\u0000\u0002\u0004\u0006\u0000\u0003\u0001\u0000\u0010\u0011\u0001"+
		"\u0000\u0012\u0013\u0001\u0000\u000b\fo\u0000\t\u0001\u0000\u0000\u0000"+
		"\u00023\u0001\u0000\u0000\u0000\u0004Q\u0001\u0000\u0000\u0000\u0006^"+
		"\u0001\u0000\u0000\u0000\b\n\u0003\u0002\u0001\u0000\t\b\u0001\u0000\u0000"+
		"\u0000\n\u000b\u0001\u0000\u0000\u0000\u000b\t\u0001\u0000\u0000\u0000"+
		"\u000b\f\u0001\u0000\u0000\u0000\f\u0001\u0001\u0000\u0000\u0000\r\u000e"+
		"\u0005\u0001\u0000\u0000\u000e\u000f\u0003\u0006\u0003\u0000\u000f\u0010"+
		"\u0005\u0002\u0000\u0000\u0010\u0011\u0003\u0004\u0002\u0000\u00114\u0001"+
		"\u0000\u0000\u0000\u0012\u0013\u0003\u0006\u0003\u0000\u0013\u0014\u0005"+
		"\u0002\u0000\u0000\u0014\u0015\u0003\u0004\u0002\u0000\u00154\u0001\u0000"+
		"\u0000\u0000\u0016\u0017\u0003\u0006\u0003\u0000\u0017\u0018\u0005\u0003"+
		"\u0000\u0000\u0018\u0019\u0005\u000b\u0000\u0000\u0019\u001a\u0005\u0002"+
		"\u0000\u0000\u001a\u001b\u0003\u0004\u0002\u0000\u001b4\u0001\u0000\u0000"+
		"\u0000\u001c4\u0003\u0004\u0002\u0000\u001d\u001e\u0005\u0004\u0000\u0000"+
		"\u001e\u001f\u0005\u000b\u0000\u0000\u001f(\u0005\u0005\u0000\u0000 %"+
		"\u0005\u000b\u0000\u0000!\"\u0005\u0006\u0000\u0000\"$\u0005\u000b\u0000"+
		"\u0000#!\u0001\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001\u0000"+
		"\u0000\u0000%&\u0001\u0000\u0000\u0000&)\u0001\u0000\u0000\u0000\'%\u0001"+
		"\u0000\u0000\u0000( \u0001\u0000\u0000\u0000()\u0001\u0000\u0000\u0000"+
		")*\u0001\u0000\u0000\u0000*+\u0005\u0007\u0000\u0000+-\u0005\b\u0000\u0000"+
		",.\u0003\u0002\u0001\u0000-,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000"+
		"\u0000/-\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000\u000001\u0001\u0000"+
		"\u0000\u000012\u0005\t\u0000\u000024\u0001\u0000\u0000\u00003\r\u0001"+
		"\u0000\u0000\u00003\u0012\u0001\u0000\u0000\u00003\u0016\u0001\u0000\u0000"+
		"\u00003\u001c\u0001\u0000\u0000\u00003\u001d\u0001\u0000\u0000\u00004"+
		"\u0003\u0001\u0000\u0000\u000056\u0006\u0002\uffff\uffff\u00006R\u0005"+
		"\u000b\u0000\u00007R\u0005\f\u0000\u00008R\u0005\u000e\u0000\u00009R\u0005"+
		"\r\u0000\u0000:R\u0005\u000f\u0000\u0000;<\u0003\u0006\u0003\u0000<=\u0005"+
		"\u0003\u0000\u0000=?\u0001\u0000\u0000\u0000>;\u0001\u0000\u0000\u0000"+
		">?\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@A\u0005\u000b\u0000"+
		"\u0000AJ\u0005\u0005\u0000\u0000BG\u0003\u0004\u0002\u0000CD\u0005\u0006"+
		"\u0000\u0000DF\u0003\u0004\u0002\u0000EC\u0001\u0000\u0000\u0000FI\u0001"+
		"\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"HK\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000JB\u0001\u0000\u0000"+
		"\u0000JK\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LR\u0005\u0007"+
		"\u0000\u0000MN\u0005\u0005\u0000\u0000NO\u0003\u0004\u0002\u0000OP\u0005"+
		"\u0007\u0000\u0000PR\u0001\u0000\u0000\u0000Q5\u0001\u0000\u0000\u0000"+
		"Q7\u0001\u0000\u0000\u0000Q8\u0001\u0000\u0000\u0000Q9\u0001\u0000\u0000"+
		"\u0000Q:\u0001\u0000\u0000\u0000Q>\u0001\u0000\u0000\u0000QM\u0001\u0000"+
		"\u0000\u0000R[\u0001\u0000\u0000\u0000ST\n\t\u0000\u0000TU\u0007\u0000"+
		"\u0000\u0000UZ\u0003\u0004\u0002\nVW\n\b\u0000\u0000WX\u0007\u0001\u0000"+
		"\u0000XZ\u0003\u0004\u0002\tYS\u0001\u0000\u0000\u0000YV\u0001\u0000\u0000"+
		"\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000"+
		"\u0000\u0000\\\u0005\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000"+
		"^_\u0007\u0002\u0000\u0000_\u0007\u0001\u0000\u0000\u0000\u000b\u000b"+
		"%(/3>GJQY[";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}