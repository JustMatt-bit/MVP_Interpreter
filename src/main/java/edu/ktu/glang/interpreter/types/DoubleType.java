package edu.ktu.glang.interpreter.types;

public class DoubleType extends Type{
    public BaseType baseType;
    public DoubleType(){
        baseType = BaseType.DOUBLE;
    }

    @Override
    public BaseType getBaseType(){
        return baseType;
    }

    @Override
    public boolean isNumericType(){
        return true;
    }
}

