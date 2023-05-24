package edu.ktu.MVPmm.interpreter.exception;

public class MVPReturnStatementException extends RuntimeException{
    public Object object;
    public MVPReturnStatementException(Object oobject){
        super(String.format("Return was called."));
        this.object = oobject;
    }
}
