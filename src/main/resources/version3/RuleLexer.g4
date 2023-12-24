lexer grammar RuleLexer;

/**
 const def ID = 5487

 def ID
 ID = 12
 def ID = 12

 ID.name = "adads"
 ID.call()

 @ID = 50
 @ID.name = "asdas"
 @ID.call()

 def array = [1,2,3,4,5,6,7,8,9]
 def value1 = array[0]
 array[0] = 45545

 def ID(){
    ID = 12
    ID.name = "asdas"
    return ID//返回值
 }

 if (ID == 121){

 }else{

 }

 loop ID to b{
    b.value
    b.index
 }

 loop (0->9) to b{
    b.value
    b.index
 }


 def cloak = ID as ID

 call()

 def class ID(ID1,ID2){

 }

 false
 true

 def ID = a is ID

 1+1
 1*1
 1/1
 1-1
 1%1

 1*=1
 1/=1
 1%=1
 1+=1
 1-=1

  >
  >=
  <
  <=
  ==
  &&
  ||
  !

  :
  =
  +=
  -=
  *=
  /=
  %=
  &=
  ++
  --
**/
//定义保留字
CONST: 'const';
DEF: 'def';
CLASS: 'class';
AS: 'as';
LOOP: 'loop';
TO: 'to';
BREAK: 'break';
CONTINUE: 'continue';
RETURN: 'return';
IF: 'if';
ELSE: 'else';

//类型定义
BOOLEAN: 'true' | 'false';
NUMBER: '-'? Digit+;
NUMBER_FLOAT: '-'? Digit+ ('.' Digit+)?;
STRING: '\'' ( ~('\''|'\\') | ('\\' .) )* '\''| '"' ( ~('"'|'\\') | ('\\' .) )* '"';

fragment Digit: [0-9];

//符号定义
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
COMMA  : ',';
DOT    : '.';


ASSIGN     : '=';
GT         : '>';
LT         : '<';
BANG       : '!';
TILDE      : '~';
QUESTION   : '?';
EQUAL      : '==';
LE         : '<=';
GE         : '>=';
NOTEQUAL   : '!=';
AND        : '&&';
OR         : '||';
INC        : '++';
DEC        : '--';
ADD        : '+';
SUB        : '-';
MUL        : '*';
DIV        : '/';
BITAND     : '&';
BITOR      : '|';
CARET      : '^';
MOD        : '%';
ARROW      : '->';
BIT_LEFT     : '<<';
BIT_RIGHT     : '>>';

ADD_ASSIGN     : '+=';
SUB_ASSIGN     : '-=';
MUL_ASSIGN     : '*=';
DIV_ASSIGN     : '/=';
AND_ASSIGN     : '&=';
OR_ASSIGN      : '|=';
XOR_ASSIGN     : '^=';
MOD_ASSIGN     : '%=';

AT       : '@';


//定义ID
ID: Name;
ID_REF: '@' Name; //引用环境变量的定义

fragment Name: [a-zA-Z_][a-zA-Z0-9_]*;


//定义注释
WS: [ \t\r\n\u000C]+ -> skip;

COMMENT: '/*' .*? '*/' -> skip;

LINE_COMMENT: '//' ~[\r\n]* -> skip;