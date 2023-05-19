package edu.ktu.glang.interpreter;

import edu.ktu.glang.GLangParser;

public class Function {
    private final GLangParser.FunctionDeclarationContext context;
    public Function(GLangParser.FunctionDeclarationContext ctx) {
        this.context = ctx;
    }

    public GLangParser.FunctionDeclarationContext getContext(){
        return context;
    }
}
