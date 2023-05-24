package edu.ktu.MVPmm.interpreter.types;

public class StringType extends Type {
    public BaseType baseType;

    public StringType(){
        baseType = BaseType.STRING;
    }

    @Override
    public BaseType getBaseType(){
        return baseType;
    }

    @Override
    public boolean isNumericType(){
        return false;
    }
}
