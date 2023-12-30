grammar test;

options {
    tokenVocab = RuleLexer; // 使用你的词法分析器
}

statement:parse+;

parse
    : methodCall
    | parenthesizedExpression
    ;

methodCall
    : ID '(' (expression (',' expression)*)? ')'
    ;

parenthesizedExpression
    : '(' expression ')'
    ;

expression
    : ID
    | INT
    ;

ID : [a-zA-Z]+;
INT : [0-9]+;
WS : [ \t\r\n]+ -> skip;
