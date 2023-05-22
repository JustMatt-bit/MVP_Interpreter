grammar GLang;

program : line+ EOF ;

line
 : functionDeclaration
 | statement
 ;

statement
    : variableDeclaration ';'
    | assignment ';'
    | arrayDeclaration ';'
    | arrayElementAssignment ';'
    | functionCall
    | switchStatement
    | ifStatement
    | printStatement ';'
    | returnStatement ';' ;


functionDeclaration : 'fun' (TYPE | VOID) ID '(' paramList? ')' functionBody ;

//paramList : TYPE ID (',' TYPE ID)* ;
paramList: param (',' param)* ;

param : typeParam;

typeParam : TYPE ID ;

functionBody : block ;

variableDeclaration : TYPE ID '=' expression ;

assignment : ID '=' expression ;

arrayDeclaration : TYPE '[' expression ']' ID ('=' '{' arrayElements '}')? ;
arrayElements : expression (',' expression)* ;

arrayElementAssignment : ID '[' expression ']' '=' expression ;

expression
    : INT                               #intExpression
    | ID                                #idExpression
    | ID'['expression']'                #arrayExpression
    | DOUBLE                            #doubleExpression
    | STRING                            #stringExpression
    | BOOLEAN                           #booleanExpression
    | '(' expression ')'                #parenthesesExpression
    | expression multiOp expression     #multiOpExpression
    | expression addOp expression       #addOpExpression
    | functionCall                      #functionExpression
    ;

multiOp : '*' | '/' | '%' ;
addOp : '+' | '-' ;


ifStatement : 'if' '(' condition ')' block ('else' block )? ;

condition : (expression relationOp expression) | expression ;

switchStatement : 'switch' '(' expression ')' ':' cases* ('default' block)? ;

cases : 'case' '('expression')' block;


functionCall : ID '(' argumentsList? ')' ;

argumentsList
: (expression | operatorOverload) (',' (expression | operatorOverload))*
;

operatorOverload : (relationOp relationOp) | (arithmeticOp arithmeticOp) ;

block : '{' statement* '}' ;

returnStatement : 'return' expression? ;

relationOp : '==' | '!=' | '>' | '<' | '<=' | '>=';

arithmeticOp : '+' | '-' | '*' | '/' | '%' ;

printStatement : PRINT '(' expression ')' ;

TYPE    : 'int'
        | 'bool'
        | 'string'
        | 'double'
        ;

STRING  : '"' ( ~('"'|'\\') )* '"' ;
VOID : 'void' ;
PRINT   : 'print' ;
BOOLEAN : 'true' | 'false' ;
ID      : [a-zA-Z]+ ;
INT     : '-'? [0-9]+ ;
DOUBLE  : '-'? [0-9]+ '.' [0-9]+ | [0-9]+;

COMMENT : ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip ;
WS      : [ \t\r\n]+ -> skip ;