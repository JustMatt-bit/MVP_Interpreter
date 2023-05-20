package edu.ktu.glang.interpreter.exception;

public class MVPReturnStatementException extends RuntimeException{
    public Object object;
    public MVPReturnStatementException(Object oobject){
        super(String.format("Return was called."));
        this.object = oobject;
    }
}
