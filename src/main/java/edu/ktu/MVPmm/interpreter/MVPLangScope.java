package edu.ktu.MVPmm.interpreter;

import edu.ktu.MVPmm.interpreter.types.Value;
import edu.ktu.MVPmm.interpreter.types.ArrayValue;

import java.util.HashMap;
import java.util.Map;

public class MVPLangScope {

    private final MVPLangScope parent;
    private final Map<String, Value> symbols = new HashMap<>();
    private final Map<String, ArrayValue> arrays = new HashMap<>();
    private final Map<String, String> operatorOverload = new HashMap<>();
    public MVPLangScope(){
        this.parent = null;
    }

    public MVPLangScope(MVPLangScope parent){
        this.parent = parent;
    }

    public void declareVariable(String variableName, Value value){
        if(isDeclared(variableName)){
            throw new RuntimeException(String.format("Variable '%s' is already declared.", variableName));
        }
        symbols.put(variableName, value);
    }

    public boolean isDeclared(String variableName){
        if(symbols.containsKey(variableName)){
            return true;
        }
        return parent != null && parent.isDeclared(variableName);
    }

    public void changeVariable(String variableName, Object expValue){
        if(!isDeclared(variableName)){
            throw new RuntimeException("Variable is not declared");
        }
        if(symbols.containsKey(variableName)){
            Value value = symbols.get(variableName);
            value.setValue(expValue);
            symbols.put(variableName, value);
        }
        else{
            assert parent != null;
            parent.changeVariable(variableName, expValue);
        }
    }

    public Value resolveVariable(String variableName){
        if (!isDeclared(variableName)) {
            throw new RuntimeException("Variable is not declared");
        }
        if (symbols.containsKey(variableName)) {
            return symbols.get(variableName);
        } else {
            assert parent != null;
            return parent.resolveVariable(variableName);
        }
    }

    //-------------------------------------
    public void declareArray(String variableName, ArrayValue value){
        if(isDeclared(variableName)){
            throw new RuntimeException(String.format("Variable name '%s' is already used.", variableName));
        }
        if(arrayIsDeclared(variableName)){
            throw new RuntimeException(String.format("Array '%s' is already declared.", variableName));
        }
        arrays.put(variableName, value);
    }

    public boolean arrayIsDeclared(String variableName){
        if(arrays.containsKey(variableName)){
            return true;
        }
        return parent != null && parent.arrayIsDeclared(variableName);
    }

    public void changeArray(String variableName, Object[] expValue){
        if(!isDeclared(variableName)){
            throw new RuntimeException("Array is not declared");
        }
        if(arrays.containsKey(variableName)){
            ArrayValue value = arrays.get(variableName);
            value.setValue(expValue);
            arrays.put(variableName, value);
        }
        else{
            assert parent != null;
            parent.changeArray(variableName, expValue);
        }
    }

    public ArrayValue resolveArray(String variableName){
        if (!arrayIsDeclared(variableName)) {
            throw new RuntimeException("Array is not declared");
        }
        if (arrays.containsKey(variableName)) {
            return arrays.get(variableName);
        } else {
            assert parent != null;
            return parent.resolveArray(variableName);
        }
    }

    // ----------------------------

    private boolean isOverloaded(String operator){
        if(operatorOverload.containsKey(operator)){
            return true;
        }
        return parent != null && parent.isOverloaded(operator);
    }

    public void overLoadOperator(String operator, String newOperator){
        operatorOverload.put(operator, newOperator);
    }

    public String resolveOperator(String operator){
        if (!isOverloaded(operator)) {
            return null;
        }
        if (operatorOverload.containsKey(operator)) {
            return operatorOverload.get(operator);
        } else {
            assert parent != null;
            return parent.resolveOperator(operator);
        }
    }
}
