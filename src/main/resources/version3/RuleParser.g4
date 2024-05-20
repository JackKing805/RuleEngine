parser grammar RuleParser;

options {
    tokenVocab = RuleLexer;
}

// 语义谓词
@members {
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
}

//program
//    : (statement NEWLINE)* EOF
//    ;


program
    : statement* EOF
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

constructorFunction
    : methodName '(' params? ')' '{' functionBody* '}'
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
    | constructorFunction
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
    | continueExpression
    | breakExpression
    | returnExpression
    | returnEmptyExpression
    ;

ifElseBody
    : expression
    | continueExpression
    | breakExpression
    | returnExpression
    | returnEmptyExpression
    ;

loopBody
    : expression
    | continueExpression
    | breakExpression
    | returnExpression
    | returnEmptyExpression
    ;


asyncBody
    : expression
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

//rangeExpression
//    : expression '->' expression
//    ;




parenthesizedExpression
    : '(' expression ')'
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
lamdaExpressionDefine
    :'(' params? ')' '->' '{' functionBody* '}'
    ;


catchErrorDefine
    : WATCH '{' watchBody* '}' ERROR '(' ID ')' '{' errorBody* '}'
    ;

watchBody
    : expression
    | continueExpression
    | breakExpression
    | returnExpression
    | returnEmptyExpression
    | NEWLINE
    ;

errorBody
    : expression
    | continueExpression
    | breakExpression
    | returnExpression
    | returnEmptyExpression
    | NEWLINE
    ;


mapDefine
    : '{' (mapEntry ( ',' mapEntry)*)? '}'
    ;

mapEntry
    : expression COLON expression
    | NEWLINE
    ;

reserveExp
    : '!' expression
    ;

//methodCallExpression
//    : expression '(' (expression (',' expression)*)? ')'
//    ;

expression
    : expression '(' (expression (',' expression)*)? ')'                        #CallMethodExpression
    | parenthesizedExpression                                                   #PriorityExpression
    | expression '.' ID '(' (expression (',' expression)*)? ')'         #ObjectMethodCallExpression
    | defineExpression                                                          #DefineNameExpression
    | typeExpression                                                            #DefineTypeExpression
    | array                                                                     #ArrayExpression
    | expression '[' expression ']'                                             #ArrayAccessExpression
    | defineVariable                                                            #VariableExpression
    | defineConstVariable                                                       #ConstVariableExpression
    | expression op=(BIT_LEFT | BIT_RIGHT | '&' | '|' | '^') (expression)         #BitOperationExpression
    | expression op=('*'|'/'|'%') (expression)                                    #MulDivYuExpression
    | expression op=('+'|'-') (expression)                                        #AddSubExpression
    | expression '=' (expression)                                                 #AssignExpression
    | expression op=ADD_ASSIGN (expression)                                       #AddAssignExpression
    | expression op=SUB_ASSIGN (expression)                                       #SubAssignExpression
    | expression op=MUL_ASSIGN (expression)                                       #MulAssignExpression
    | expression op=DIV_ASSIGN (expression)                                       #DivAssignExpression
    | expression op=AND_ASSIGN (expression)                                       #AndAssignExpression
    | expression op=OR_ASSIGN (expression)                                        #OrAssignExpression
    | expression op=XOR_ASSIGN (expression)                                       #XorAssignExpression
    | expression op=MOD_ASSIGN (expression)                                       #ModAssignExpression
    | expression op=('&&' | '||' | '==' |'!=' | '>=' | '<=' | '<' | '>') (expression)         #CompareExpression
    | ifExpression                                                              #DefineIfExpression
    | LOOP (expression (TO ID)?)? '{' loopBody* '}'                                #LoopExpression
    | expression '->' expression                                                #DefineRangeExpression
    | numberAutoIncreaseReduceExpression                                        #NumberAutoExpression
    | expression '.' ID                                                         #ObjectPropertiesExpression
    | '-' expression                                                            #SignedExpression
    | lamdaExpressionDefine                                                     #LamdaExpression
    | catchErrorDefine                                                          #CatchErrorExpression
    | mapDefine                                                                 #MapExpression
    | reserveExp                                                                #ReserveExpression
    | ASYNC '{' asyncBody* '}'                                                  #AsyncExpression
    ;


