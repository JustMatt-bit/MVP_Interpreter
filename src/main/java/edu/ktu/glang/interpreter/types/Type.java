package edu.ktu.glang.interpreter.types;

public abstract class Type {
    public enum BaseType{
        INTEGER,
        STRING,
        BOOLEAN,
        DOUBLE
    }

    public abstract BaseType getBaseType();

    public boolean isNumericType() { return false; }

    public static Type createType(String typeName) {
        return switch (typeName) {
            case "int" -> new IntType();
            case "string" -> new StringType();
            case "bool" -> new BooleanType();
            case "double" -> new DoubleType();
            default -> throw new RuntimeException("Unknown type");
        };
    }

    public static Type createType(BaseType type) {
        return switch (type) {
            case INTEGER -> new IntType();
            case STRING -> new StringType();
            case BOOLEAN -> new BooleanType();
            case DOUBLE -> new DoubleType();
            default -> throw new RuntimeException("Unknown type");
        };
    }

    public static Object castObjectToType(Object obj, String typeName){
        switch(typeName){
            case "int":
                return (Integer)obj;
            case "string":
                return (String)obj;
            case "bool":
                return (Boolean) obj;
            case "double":
                try {
                    return (Double) obj;
                }
                catch (ClassCastException exception){
                    return Double.valueOf((Integer)obj);
                }
            default:
                throw new RuntimeException("Unknown type");
        }
    }

    public static Object castObjectToType(Object obj, BaseType type){
        switch(type){
            case INTEGER:
                return (Integer)obj;
            case STRING:
                return (String)obj;
            case BOOLEAN:
                return (Boolean) obj;
            case DOUBLE:
                try {
                    return (Double) obj;
                }
                catch (ClassCastException exception){
                    return Double.valueOf((Integer)obj);
                }
            default:
                throw new RuntimeException("Unknown type");
        }
    }
}
