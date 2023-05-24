package edu.ktu.MVPmm.interpreter;

import edu.ktu.MVPmm.MVPmmParser;

public class Function {
    private final MVPmmParser.FunctionDeclarationContext context;
    public Function(MVPmmParser.FunctionDeclarationContext ctx) {
        this.context = ctx;
    }

    public MVPmmParser.FunctionDeclarationContext getContext(){
        return context;
    }
}
