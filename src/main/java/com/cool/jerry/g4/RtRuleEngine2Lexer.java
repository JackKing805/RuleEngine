// Generated from /Users/vps_developer/Desktop/github/RuleEngine-main/src/main/resources/RtRuleEngine2.g4 by ANTLR 4.13.1
package com.cool.jerry.g4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class RtRuleEngine2Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, WS=11, ID=12, ID_REF=13, STRING=14, INT=15, FLOAT=16, MUL=17, 
		DIV=18, ADD=19, SUB=20;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "WS", "ID", "ID_REF", "STRING", "INT", "FLOAT", "MUL", "DIV", 
			"ADD", "SUB"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'val'", "'='", "'.'", "'fun'", "'('", "','", "')'", "'{'", "'return'", 
			"'}'", null, null, null, null, null, null, "'*'", "'/'", "'+'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "WS", 
			"ID", "ID_REF", "STRING", "INT", "FLOAT", "MUL", "DIV", "ADD", "SUB"
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


	public RtRuleEngine2Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "RtRuleEngine2.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0014\u008d\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0004\nH\b\n\u000b\n\f\nI\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0005\u000bP\b\u000b\n\u000b\f\u000bS\t\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\\\b\r\n\r\f\r_\t\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0005\rf\b\r\n\r\f\ri\t\r\u0001\r\u0003"+
		"\rl\b\r\u0001\u000e\u0003\u000eo\b\u000e\u0001\u000e\u0004\u000er\b\u000e"+
		"\u000b\u000e\f\u000es\u0001\u000f\u0003\u000fw\b\u000f\u0001\u000f\u0004"+
		"\u000fz\b\u000f\u000b\u000f\f\u000f{\u0001\u000f\u0001\u000f\u0004\u000f"+
		"\u0080\b\u000f\u000b\u000f\f\u000f\u0081\u0003\u000f\u0084\b\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0000\u0000\u0014\u0001\u0001\u0003\u0002\u0005\u0003"+
		"\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015"+
		"\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012"+
		"%\u0013\'\u0014\u0001\u0000\u0005\u0003\u0000\t\n\r\r  \u0003\u0000AZ"+
		"__az\u0004\u000009AZ__az\u0002\u0000\'\'\\\\\u0002\u0000\"\"\\\\\u0099"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0001)\u0001\u0000\u0000\u0000"+
		"\u0003-\u0001\u0000\u0000\u0000\u0005/\u0001\u0000\u0000\u0000\u00071"+
		"\u0001\u0000\u0000\u0000\t5\u0001\u0000\u0000\u0000\u000b7\u0001\u0000"+
		"\u0000\u0000\r9\u0001\u0000\u0000\u0000\u000f;\u0001\u0000\u0000\u0000"+
		"\u0011=\u0001\u0000\u0000\u0000\u0013D\u0001\u0000\u0000\u0000\u0015G"+
		"\u0001\u0000\u0000\u0000\u0017M\u0001\u0000\u0000\u0000\u0019T\u0001\u0000"+
		"\u0000\u0000\u001bk\u0001\u0000\u0000\u0000\u001dn\u0001\u0000\u0000\u0000"+
		"\u001fv\u0001\u0000\u0000\u0000!\u0085\u0001\u0000\u0000\u0000#\u0087"+
		"\u0001\u0000\u0000\u0000%\u0089\u0001\u0000\u0000\u0000\'\u008b\u0001"+
		"\u0000\u0000\u0000)*\u0005v\u0000\u0000*+\u0005a\u0000\u0000+,\u0005l"+
		"\u0000\u0000,\u0002\u0001\u0000\u0000\u0000-.\u0005=\u0000\u0000.\u0004"+
		"\u0001\u0000\u0000\u0000/0\u0005.\u0000\u00000\u0006\u0001\u0000\u0000"+
		"\u000012\u0005f\u0000\u000023\u0005u\u0000\u000034\u0005n\u0000\u0000"+
		"4\b\u0001\u0000\u0000\u000056\u0005(\u0000\u00006\n\u0001\u0000\u0000"+
		"\u000078\u0005,\u0000\u00008\f\u0001\u0000\u0000\u00009:\u0005)\u0000"+
		"\u0000:\u000e\u0001\u0000\u0000\u0000;<\u0005{\u0000\u0000<\u0010\u0001"+
		"\u0000\u0000\u0000=>\u0005r\u0000\u0000>?\u0005e\u0000\u0000?@\u0005t"+
		"\u0000\u0000@A\u0005u\u0000\u0000AB\u0005r\u0000\u0000BC\u0005n\u0000"+
		"\u0000C\u0012\u0001\u0000\u0000\u0000DE\u0005}\u0000\u0000E\u0014\u0001"+
		"\u0000\u0000\u0000FH\u0007\u0000\u0000\u0000GF\u0001\u0000\u0000\u0000"+
		"HI\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000"+
		"\u0000JK\u0001\u0000\u0000\u0000KL\u0006\n\u0000\u0000L\u0016\u0001\u0000"+
		"\u0000\u0000MQ\u0007\u0001\u0000\u0000NP\u0007\u0002\u0000\u0000ON\u0001"+
		"\u0000\u0000\u0000PS\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000"+
		"QR\u0001\u0000\u0000\u0000R\u0018\u0001\u0000\u0000\u0000SQ\u0001\u0000"+
		"\u0000\u0000TU\u0005@\u0000\u0000UV\u0003\u0017\u000b\u0000V\u001a\u0001"+
		"\u0000\u0000\u0000W]\u0005\'\u0000\u0000X\\\b\u0003\u0000\u0000YZ\u0005"+
		"\\\u0000\u0000Z\\\t\u0000\u0000\u0000[X\u0001\u0000\u0000\u0000[Y\u0001"+
		"\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000"+
		"]^\u0001\u0000\u0000\u0000^`\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000"+
		"\u0000`l\u0005\'\u0000\u0000ag\u0005\"\u0000\u0000bf\b\u0004\u0000\u0000"+
		"cd\u0005\\\u0000\u0000df\t\u0000\u0000\u0000eb\u0001\u0000\u0000\u0000"+
		"ec\u0001\u0000\u0000\u0000fi\u0001\u0000\u0000\u0000ge\u0001\u0000\u0000"+
		"\u0000gh\u0001\u0000\u0000\u0000hj\u0001\u0000\u0000\u0000ig\u0001\u0000"+
		"\u0000\u0000jl\u0005\"\u0000\u0000kW\u0001\u0000\u0000\u0000ka\u0001\u0000"+
		"\u0000\u0000l\u001c\u0001\u0000\u0000\u0000mo\u0005-\u0000\u0000nm\u0001"+
		"\u0000\u0000\u0000no\u0001\u0000\u0000\u0000oq\u0001\u0000\u0000\u0000"+
		"pr\u000209\u0000qp\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000s"+
		"q\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000t\u001e\u0001\u0000"+
		"\u0000\u0000uw\u0005-\u0000\u0000vu\u0001\u0000\u0000\u0000vw\u0001\u0000"+
		"\u0000\u0000wy\u0001\u0000\u0000\u0000xz\u0003\u001d\u000e\u0000yx\u0001"+
		"\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000"+
		"{|\u0001\u0000\u0000\u0000|\u0083\u0001\u0000\u0000\u0000}\u007f\u0005"+
		".\u0000\u0000~\u0080\u0003\u001d\u000e\u0000\u007f~\u0001\u0000\u0000"+
		"\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000"+
		"\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0084\u0001\u0000\u0000"+
		"\u0000\u0083}\u0001\u0000\u0000\u0000\u0083\u0084\u0001\u0000\u0000\u0000"+
		"\u0084 \u0001\u0000\u0000\u0000\u0085\u0086\u0005*\u0000\u0000\u0086\""+
		"\u0001\u0000\u0000\u0000\u0087\u0088\u0005/\u0000\u0000\u0088$\u0001\u0000"+
		"\u0000\u0000\u0089\u008a\u0005+\u0000\u0000\u008a&\u0001\u0000\u0000\u0000"+
		"\u008b\u008c\u0005-\u0000\u0000\u008c(\u0001\u0000\u0000\u0000\u000e\u0000"+
		"IQ[]egknsv{\u0081\u0083\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}