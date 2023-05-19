package edu.ktu.glang.interpreter.types;

public abstract class Type {
    public enum BaseType{
        INTEGER,
        STRING
    }

    public abstract BaseType getBaseType();

    public boolean isNumericType() { return false; }

    public static Type createType(String typeName) {
        switch(typeName){
            case "int":
                return new IntType();
            case "string":
                return new StringType();
            default:
                throw new RuntimeException("Unknown type");
        }
    }

    public static Type createType(BaseType type) {
        switch(type){
            case INTEGER:
                return new IntType();
            case STRING:
                return new StringType();
            default:
                throw new RuntimeException("Unknown type");
        }
    }
}
