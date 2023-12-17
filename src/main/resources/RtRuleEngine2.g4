grammar RtRuleEngine2;

program: statement+;

//语句定义
statement
    : 'val' variable '=' expression                                # NewVariableAssignmentStatement
    | variable '=' expression                                      # VariableAssignmentStatement
    | variable '.' ID '=' expression                               # VariablePropertiesAssignmentStatement
    | expression                                                   # InvokeStatement
    | 'fun' ID '(' (ID (',' ID)*)? ')' '{' statement+ '}'          # FunctionStatement
    ;

//表达式定义
expression
    :   expression op=('*'|'/') expression # MulDivExpression
    |   expression op=('+'|'-') expression # AddSubExpression
    |   ID                                 # IdExpression
    |   ID_REF                             # IdRefExpression
    |   INT                                # IntExpression
    |   STRING                             # StringExpression
    |   FLOAT                              # FloatExpression
    |   (variable '.')? ID '(' (expression (',' expression)*)? ')' #MethodCallExpression
    |   '(' expression ')'                 # BlockExpression
    ;


variable : ID_REF | ID;


WS: [ \t\r\n]+ -> skip;//空格匹配

ID: [a-zA-Z_][a-zA-Z0-9_]*; //变量或者引用变量命名
ID_REF: '$' ID; //引用环境变量的定义

STRING: '\'' ( ~('\''|'\\') | ('\\' .) )* '\''| '"' ( ~('"'|'\\') | ('\\' .) )* '"'; //匹配带引号的文本
INT: '0'..'9'+; //匹配整数
FLOAT:'-'? INT+ ('.' INT+)?; //匹配浮点数

MUL     : '*' ;
DIV     : '/' ;
ADD     : '+' ;
SUB     : '-' ;