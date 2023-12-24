parser grammar RuleParser;

options {
    tokenVocab = RuleLexer;
}

program
    : statement*
    ;

statement
    : function   #FunctionStatement
    | class      #ClassStatement
    | expression #ExpressionStatement
    ;

methodName
    : ID
    ;

function
    : 'def' methodName '(' params? ')' '{' functionBody* '}'
    ;

paramName
    :ID
    ;

params
    : paramName (',' paramName)*
    ;

functionBody
    : expression
    | returnExpression
    | returnEmptyExpression
    ;


class
    : 'def' 'class' ID '(' params? ')' '{' classBody* '}'
    ;

classBody
    : function
    | defineVariable
    | defineConstVariable
    ;

defineVariable
    : 'def' ID '=' expression
    ;

defineConstVariable
    : 'const' 'def' ID '=' expression
    ;

array
    : '[' (expression (',' expression)*)? ']'
    ;


ifExpression
    : 'if' '(' expression ')' '{' ifThenBody* '}' ('else' '{' ifElseBody* '}')?
    ;

ifThenBody
    : expression
    ;

ifElseBody
    : expression
    ;

loopBody
    : expression
    | continueExpression
    | breakExpression
    | returnExpression
    | returnEmptyExpression
    ;

returnEmptyExpression
    : 'return'
    ;

returnExpression
    : 'return' expression
    ;

breakExpression
    : 'break'
    ;

continueExpression
    : CONTINUE
    ;

rangeExpression
    : '(' expression '->' expression ')'
    ;

methodCallExpression
    : ID '(' (expression (',' expression)*)? ')'
    ;


defineExpression
    : ID
    | ID_REF
    ;

typeExpression
    : NUMBER
    | NUMBER_FLOAT
    | STRING
    | BOOLEAN
    ;

numberAutoIncreaseReduceExpression
    : defineExpression '++'    #NumberAutoIncreaseExpression
    | defineExpression '--'    #NumberAutoReduceExpression
    ;

expression
    : defineExpression                                                          #DefineNameExpression
    | typeExpression                                                            #DefineTypeExpression
    | array                                                                     #ArrayExpression
    | expression '[' NUMBER ']'                                                 #ArrayAccessExpression
    | defineVariable                                                            #VariableExpression
    | defineConstVariable                                                       #ConstVariableExpression
    | expression op=(BIT_LEFT | BIT_RIGHT | '&' | '|' | '^') expression         #BitOperationExpression
    | expression op=('*'|'/'|'%') expression                                    #MulDivYuExpression
    | expression op=('+'|'-') expression                                        #AddSubExpression
    | expression op=ADD_ASSIGN expression                                       #AddAssignExpression
    | expression op=SUB_ASSIGN expression                                       #SubAssignExpression
    | expression op=MUL_ASSIGN expression                                       #MulAssignExpression
    | expression op=DIV_ASSIGN expression                                       #DivAssignExpression
    | expression op=AND_ASSIGN expression                                       #AndAssignExpression
    | expression op=OR_ASSIGN expression                                        #OrAssignExpression
    | expression op=XOR_ASSIGN expression                                       #XorAssignExpression
    | expression op=MOD_ASSIGN expression                                       #ModAssignExpression
    | expression op=('&&' | '||' | '==' |'!=' | '>=' | '<=' | '<' | '>') expression         #CompareExpression
    | ifExpression                                                              #DefineIfExpression
    | LOOP expression TO ID '{' loopBody* '}'                                   #LoopExpression
    | rangeExpression                                                           #DefineRangeExpression
    | expression '.' methodCallExpression                                       #ObjectMethodCallExpression
    | methodCallExpression                                                      #CallMethodExpression
    | numberAutoIncreaseReduceExpression                                        #NumberAutoExpression
    ;


