package edu.ktu.MVPmm.interpreter;

import edu.ktu.MVPmm.interpreter.types.Value;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Value> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    public void put(String name, Value value) {
        table.put(name, value);
    }

    public Value get(String name) {
        return table.get(name);
    }

    public boolean contains(String name) {
        return table.containsKey(name);
    }
}
