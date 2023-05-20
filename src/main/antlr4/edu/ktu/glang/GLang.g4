grammar GLang;

program : line+ EOF ;

line
 : functionDeclaration
 | statement
 ;

statement
    : variableDeclaration ';'
    | assignment ';'
    | functionCall
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

expression
    : INT                               #intExpression
    | ID                                #idExpression
    | STRING                            #stringExpression
    | '(' expression ')'                #parenthesesExpression
    | expression intMultiOp expression  #intMultiOpExpression
    | expression intAddOp expression    #intAddOpExpression
    | functionCall                      #functionExpression
    ;

intMultiOp : '*' | '/' | '%' ;
intAddOp : '+' | '-' ;


ifStatement : 'if' '(' expression relationOp expression ')' block ('else' block )? ;

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
        ;

VOID : 'void' ;
PRINT   : 'print';
ID      : [a-zA-Z]+ ;
INT     : [0-9]+ ;
STRING : '"' (~["\\r\\n] | '\\' ["\\r\\n])* '"';


COMMENT : ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip ;
WS      : [ \t\r\n]+ -> skip ;