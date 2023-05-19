package edu.ktu.glang.interpreter.types;

import edu.ktu.glang.interpreter.types.Type;

public class IntType extends Type {
    public BaseType baseType;
    public IntType(){
        baseType = BaseType.INTEGER;
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
