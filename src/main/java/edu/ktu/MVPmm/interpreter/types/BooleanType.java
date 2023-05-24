package edu.ktu.MVPmm.interpreter.types;

public class BooleanType extends Type{

    public BaseType baseType;
    public BooleanType(){
        baseType = BaseType.BOOLEAN;
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
