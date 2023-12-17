grammar RtRuleEngine;


program: statement+;

statement: assignment | math | functionDeclaration | functionCall;//val a = $b

assignment : 'val'? NameDefine '=' valueDeclaration;//定义变量值的获取方式

valueDeclaration: NameDefine | RefNameDefine | INT | FLOAT | STRING | math | functionCall;//$A A 1 2.2 "ASDSADA"

math: term (('+' term) | ('-' term))*;

term: factor (('*' factor) | ('/' factor))*;

factor: STRING | INT | FLOAT | RefNameDefine | NameDefine;

functionDeclaration: 'fun' NameDefine '(' parameterList? ')' '{' statement+ '}';

parameterList: NameDefine (',' NameDefine)*;

functionCall: (RefNameDefine | NameDefine) '.' NameDefine '(' argumentList? ')' | NameDefine '(' argumentList? ')';

argumentList: argument (',' argument)*;

argument: RefNameDefine  | NameDefine | STRING | INT | FLOAT | math;

NameDefine: [a-zA-Z_][a-zA-Z0-9_]*; //变量或者引用变量命名

RefNameDefine: '$' NameDefine; //引用环境变量的定义

WS: [ \t\r\n]+ -> skip;//空格匹配

STRING: '\'' ( ~('\''|'\\') | ('\\' .) )* '\''| '"' ( ~('"'|'\\') | ('\\' .) )* '"'; //匹配带引号的文本
INT: '0'..'9'+; //匹配整数
FLOAT:'-'? INT+ ('.' INT+)?; //匹配浮点数



MUL     : '*' ;
DIV     : '/' ;
ADD     : '+' ;
SUB     : '-' ;