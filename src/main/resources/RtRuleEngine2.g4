grammar RtRuleEngine2;

program: statement+;

//语句定义
statement
    : 'val' variable '=' expression                                 # NewVariableAssignmentStatement
    | variable '=' expression                                       # VariableAssignmentStatement
    | variable '.' properties '=' expression                        # VariablePropertiesAssignmentStatement
    | expression                                                    # InvokeStatement
    | 'fun' method '(' (param (',' param)*)? ')' '{' statement* ('return' expression)? '}' # FunctionStatement
    ;

//表达式定义
expression
    :   expression '.' method '(' (expression (',' expression)*)? ')'    # ObjectMethodCallExpression
    |   method '(' (expression (',' expression)*)? ')'                   # MethodCallExpression
    |   expression op=('*'|'/') expression                               # MulDivExpression
    |   expression op=('+'|'-') expression                               # AddSubExpression
    |   'if' expression '{' ifStatment? '}' ('else' '{' elseStatment? '}')? # IfExpression
    |   'loop' expression 'to' ID '{' bloakStatment? '}'                 # LoopExpression
    |   expression '.' properties                                        # GetPropertiesExpression
    |   expression '[' INT ']'                                           # ArrayAccessExpression
    |   defineArray                                                      # DefineArrayExpression
    |   BOOLEAN                                                          # BooleanExpression
    |   ID                                                               # IdExpression
    |   ID_REF                                                           # IdRefExpression
    |   INT                                                              # IntExpression
    |   STRING                                                           # StringExpression
    |   FLOAT                                                            # FloatExpression
    |   expression '==' expression                                       # EqualsExpression
    |   expression '&&' expression                                       # AndExpression
    |   expression '||' expression                                       # OrExpression
//    |   '(' expression ')'                                             # BlockExpression
    ;

bloakStatment: statement+;

ifStatment: statement+;

elseStatment: statement+;

variable : ID_REF | ID;

method: ID;

param: ID;

defineArray: '[' expression (',' expression)* ']';

properties: ID ('[' INT ']')?;



BOOLEAN: 'true' | 'false';//匹配boolean值
STRING: '\'' ( ~('\''|'\\') | ('\\' .) )* '\''| '"' ( ~('"'|'\\') | ('\\' .) )* '"'; //匹配带引号的文本
INT: '-'? '0'..'9'+; //匹配整数
FLOAT: '-'? INT+ ('.' INT+)?; //匹配浮点数

MUL     : '*' ;
DIV     : '/' ;
ADD     : '+' ;
SUB     : '-' ;

WS: [ \t\r\n]+ -> skip;//空格匹配

ID: [a-zA-Z_][a-zA-Z0-9_]*; //变量或者引用变量命名
ID_REF: '@' ID; //引用环境变量的定义


