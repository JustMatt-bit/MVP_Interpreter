package edu.ktu.MVPmm.interpreter.types;

public class Value {
    private Object value;
    private Type type;

    public Value(Type type, Object value){
        this.type = type;
        this.value = value;
    }

    public Value(Value orgValue){
        this.type = Type.createType(orgValue.getType().getBaseType());
        this.value = orgValue.getValue();
    }
    public void setValue(Object newValue){
        value = newValue;
    }
    public Type getType(){ return type;}
    public Object getValue(){ return value;}

    @Override
    public String toString(){
        return value.toString();
    }

    public String typeToString(){
        return type.getBaseType().toString();
    }
}
