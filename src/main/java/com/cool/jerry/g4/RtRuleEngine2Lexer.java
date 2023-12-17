// Generated from E:/Projects/JavaProjects/RuleEngine/src/main/resources/RtRuleEngine2.g4 by ANTLR 4.13.1
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
		WS=10, ID=11, ID_REF=12, STRING=13, INT=14, FLOAT=15, MUL=16, DIV=17, 
		ADD=18, SUB=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"WS", "ID", "ID_REF", "STRING", "INT", "FLOAT", "MUL", "DIV", "ADD", 
			"SUB"
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
		"\u0004\u0000\u0013\u0081\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\t\u0004\t?\b\t\u000b\t\f\t@\u0001\t\u0001\t\u0001\n\u0001\n\u0005\nG"+
		"\b\n\n\n\f\nJ\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0005\fS\b\f\n\f\f\fV\t\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0005\f]\b\f\n\f\f\f`\t\f\u0001\f\u0003\fc\b\f\u0001\r\u0004"+
		"\rf\b\r\u000b\r\f\rg\u0001\u000e\u0003\u000ek\b\u000e\u0001\u000e\u0004"+
		"\u000en\b\u000e\u000b\u000e\f\u000eo\u0001\u000e\u0001\u000e\u0004\u000e"+
		"t\b\u000e\u000b\u000e\f\u000eu\u0003\u000ex\b\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0000\u0000\u0013\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\u0001"+
		"\u0000\u0005\u0003\u0000\t\n\r\r  \u0003\u0000AZ__az\u0004\u000009AZ_"+
		"_az\u0002\u0000\'\'\\\\\u0002\u0000\"\"\\\\\u008c\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0001\'\u0001"+
		"\u0000\u0000\u0000\u0003+\u0001\u0000\u0000\u0000\u0005-\u0001\u0000\u0000"+
		"\u0000\u0007/\u0001\u0000\u0000\u0000\t3\u0001\u0000\u0000\u0000\u000b"+
		"5\u0001\u0000\u0000\u0000\r7\u0001\u0000\u0000\u0000\u000f9\u0001\u0000"+
		"\u0000\u0000\u0011;\u0001\u0000\u0000\u0000\u0013>\u0001\u0000\u0000\u0000"+
		"\u0015D\u0001\u0000\u0000\u0000\u0017K\u0001\u0000\u0000\u0000\u0019b"+
		"\u0001\u0000\u0000\u0000\u001be\u0001\u0000\u0000\u0000\u001dj\u0001\u0000"+
		"\u0000\u0000\u001fy\u0001\u0000\u0000\u0000!{\u0001\u0000\u0000\u0000"+
		"#}\u0001\u0000\u0000\u0000%\u007f\u0001\u0000\u0000\u0000\'(\u0005v\u0000"+
		"\u0000()\u0005a\u0000\u0000)*\u0005l\u0000\u0000*\u0002\u0001\u0000\u0000"+
		"\u0000+,\u0005=\u0000\u0000,\u0004\u0001\u0000\u0000\u0000-.\u0005.\u0000"+
		"\u0000.\u0006\u0001\u0000\u0000\u0000/0\u0005f\u0000\u000001\u0005u\u0000"+
		"\u000012\u0005n\u0000\u00002\b\u0001\u0000\u0000\u000034\u0005(\u0000"+
		"\u00004\n\u0001\u0000\u0000\u000056\u0005,\u0000\u00006\f\u0001\u0000"+
		"\u0000\u000078\u0005)\u0000\u00008\u000e\u0001\u0000\u0000\u00009:\u0005"+
		"{\u0000\u0000:\u0010\u0001\u0000\u0000\u0000;<\u0005}\u0000\u0000<\u0012"+
		"\u0001\u0000\u0000\u0000=?\u0007\u0000\u0000\u0000>=\u0001\u0000\u0000"+
		"\u0000?@\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001\u0000"+
		"\u0000\u0000AB\u0001\u0000\u0000\u0000BC\u0006\t\u0000\u0000C\u0014\u0001"+
		"\u0000\u0000\u0000DH\u0007\u0001\u0000\u0000EG\u0007\u0002\u0000\u0000"+
		"FE\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000"+
		"\u0000HI\u0001\u0000\u0000\u0000I\u0016\u0001\u0000\u0000\u0000JH\u0001"+
		"\u0000\u0000\u0000KL\u0005$\u0000\u0000LM\u0003\u0015\n\u0000M\u0018\u0001"+
		"\u0000\u0000\u0000NT\u0005\'\u0000\u0000OS\b\u0003\u0000\u0000PQ\u0005"+
		"\\\u0000\u0000QS\t\u0000\u0000\u0000RO\u0001\u0000\u0000\u0000RP\u0001"+
		"\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000"+
		"TU\u0001\u0000\u0000\u0000UW\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000"+
		"\u0000Wc\u0005\'\u0000\u0000X^\u0005\"\u0000\u0000Y]\b\u0004\u0000\u0000"+
		"Z[\u0005\\\u0000\u0000[]\t\u0000\u0000\u0000\\Y\u0001\u0000\u0000\u0000"+
		"\\Z\u0001\u0000\u0000\u0000]`\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000"+
		"\u0000^_\u0001\u0000\u0000\u0000_a\u0001\u0000\u0000\u0000`^\u0001\u0000"+
		"\u0000\u0000ac\u0005\"\u0000\u0000bN\u0001\u0000\u0000\u0000bX\u0001\u0000"+
		"\u0000\u0000c\u001a\u0001\u0000\u0000\u0000df\u000209\u0000ed\u0001\u0000"+
		"\u0000\u0000fg\u0001\u0000\u0000\u0000ge\u0001\u0000\u0000\u0000gh\u0001"+
		"\u0000\u0000\u0000h\u001c\u0001\u0000\u0000\u0000ik\u0005-\u0000\u0000"+
		"ji\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000km\u0001\u0000\u0000"+
		"\u0000ln\u0003\u001b\r\u0000ml\u0001\u0000\u0000\u0000no\u0001\u0000\u0000"+
		"\u0000om\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pw\u0001\u0000"+
		"\u0000\u0000qs\u0005.\u0000\u0000rt\u0003\u001b\r\u0000sr\u0001\u0000"+
		"\u0000\u0000tu\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000uv\u0001"+
		"\u0000\u0000\u0000vx\u0001\u0000\u0000\u0000wq\u0001\u0000\u0000\u0000"+
		"wx\u0001\u0000\u0000\u0000x\u001e\u0001\u0000\u0000\u0000yz\u0005*\u0000"+
		"\u0000z \u0001\u0000\u0000\u0000{|\u0005/\u0000\u0000|\"\u0001\u0000\u0000"+
		"\u0000}~\u0005+\u0000\u0000~$\u0001\u0000\u0000\u0000\u007f\u0080\u0005"+
		"-\u0000\u0000\u0080&\u0001\u0000\u0000\u0000\r\u0000@HRT\\^bgjouw\u0001"+
		"\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}