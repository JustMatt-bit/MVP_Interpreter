package edu.ktu.glang.interpreter;

import edu.ktu.glang.GLangBaseVisitor;
import edu.ktu.glang.GLangParser;
import edu.ktu.glang.interpreter.exception.MVPReturnStatementException;
import edu.ktu.glang.interpreter.types.Type;
import edu.ktu.glang.interpreter.types.Value;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class InterpreterVisitor extends GLangBaseVisitor<Object> {

    private final StringBuilder SYSTEM_OUT = new StringBuilder();

    private final Stack<MVPLangScope> scopeStack = new Stack<>();
    private MVPLangScope currentScope = new MVPLangScope();

    private final Map<String, Function> functions = new HashMap<>();
    private final Map<String, String> operatorOverload = new HashMap<>();
    private final IfStatementVisitor ifStatementVisitor;
    private final ArithmeticsVisitor arithmeticsVisitor;

    public InterpreterVisitor(MVPLangScope scope) {
        this.currentScope = scope;
        this.ifStatementVisitor = new IfStatementVisitor(this);
        this.arithmeticsVisitor = new ArithmeticsVisitor(this);
    }

    @Override
    public Object visitProgram(GLangParser.ProgramContext ctx) {
        super.visitProgram(ctx);
        return SYSTEM_OUT.toString();
    }

    @Override
    public Object visitVariableDeclaration(GLangParser.VariableDeclarationContext ctx) {
        String varType = ctx.TYPE().getText();
        String varName = ctx.ID().getText();
        Object expValue = visit(ctx.expression());
        expValue = Type.castObjectToType(expValue, varType);
        Value value = new Value(Type.createType(varType),expValue);
        this.currentScope.declareVariable(varName, value);
        return null;
    }

    @Override
    public Object visitAssignment(GLangParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();
        Object expValue = visit(ctx.expression());
        Value value = this.currentScope.resolveVariable(varName);
        expValue = Type.castObjectToType(expValue, value.getType().getBaseType());
        this.currentScope.changeVariable(varName, expValue);
        return null;
    }

    @Override
    public Object visitIntExpression(GLangParser.IntExpressionContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }

    @Override
    public Object visitStringExpression(GLangParser.StringExpressionContext ctx) {
        String str = ctx.STRING().getText();
        // Remove the leading and trailing quotation marks
        str = str.substring(1, str.length() - 1);
        // Return the modified string
        return str;
    }

    @Override
    public Object visitDoubleExpression(GLangParser.DoubleExpressionContext ctx) {
        return Double.parseDouble(ctx.DOUBLE().getText());
    }

    @Override
    public Object visitIdExpression(GLangParser.IdExpressionContext ctx) {
        String varName = ctx.ID().getText();
        Value value = this.currentScope.resolveVariable(varName);
        return value.getValue();
    }

    @Override
    public Object visitBooleanExpression(GLangParser.BooleanExpressionContext ctx) {
        return Boolean.parseBoolean(ctx.BOOLEAN().getText());
    }

    @Override
    public Object visitPrintStatement(GLangParser.PrintStatementContext ctx) {
        Object text = visit(ctx.expression()).toString();
        //System.out.println(text);
        SYSTEM_OUT.append(text).append("\n");
        return null;
    }

    @Override
    public Object visitParenthesesExpression(GLangParser.ParenthesesExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitAddOpExpression(GLangParser.AddOpExpressionContext ctx) {
        return this.arithmeticsVisitor.visitAddOpExpression(ctx);
    }

    @Override
    public Object visitMultiOpExpression(GLangParser.MultiOpExpressionContext ctx) {
        return this.arithmeticsVisitor.visitMultiOpExpression(ctx);
    }

    @Override
    public Object visitIfStatement(GLangParser.IfStatementContext ctx) {
        return this.ifStatementVisitor.visitIfStatement(ctx);
    }

    @Override
    public Object visitSwitchStatement(GLangParser.SwitchStatementContext ctx) {
        Object expValue = visit(ctx.expression());
        boolean caseFound = false;
        var cases = ctx.cases();
        for(GLangParser.CasesContext caseCtx : cases){
            Object caseExpValue = visit(caseCtx.expression());
            if(expValue.equals(caseExpValue)){
                caseFound = true;
                visitBlock(caseCtx.block());
                break;
            }
        }
        if(!caseFound && ctx.block() != null){
            visitBlock(ctx.block());
        }
        return null;
    }

    @Override
    public Object visitBlock(GLangParser.BlockContext ctx) {
        scopeStack.push(currentScope);
        currentScope = new MVPLangScope(currentScope);
        Object value = null;
        try {
            value = super.visitBlock(ctx);
        }
        catch (MVPReturnStatementException returned){
            value = returned.object;
            currentScope = scopeStack.pop();
            throw new MVPReturnStatementException(value);
        }
        currentScope = scopeStack.pop();
        return value;
    }

    @Override
    public Object visitFunctionDeclaration(GLangParser.FunctionDeclarationContext ctx) {
        String funName = ctx.ID().getText();
        if(!functions.containsKey(funName)){
            Function function = new Function(ctx);
            functions.put(funName, function);
        }
        else{
            throw new RuntimeException("Function is already declared");
        }
        return null;
    }

    @Override
    public Object visitFunctionCall(GLangParser.FunctionCallContext ctx) {
        String funName = ctx.ID().getText();
        if(!functions.containsKey(funName)){
            throw new RuntimeException("This function does not exist");
        }
        GLangParser.FunctionDeclarationContext funCtx = functions.get(funName).getContext();

        MVPLangScope functionScope = new MVPLangScope();
        // Validating argument count, type and then copying them to the new scope
        if(funCtx.paramList() != null){
            var funParams = funCtx.paramList().param();
            var expressions = ctx.argumentsList().expression();

            if(funParams.size() != expressions.size()){
                throw new RuntimeException("Invalid number of arguments");
            }
            else{
                for(int i = 0; i < funParams.size(); i++){
                    var param = funParams.get(i);
                    var arg = expressions.get(i);
                    Value argValue = currentScope.resolveVariable(arg.getText());
                    Type.BaseType argType = argValue.getType().getBaseType();
                    Type reqType = Type.createType(param.typeParam().TYPE().getText());
                    if(argType != reqType.getBaseType()){
                        throw new RuntimeException("Function argument type mismatch");
                    }
                }
            }
            // Saving function call arguments into the function scope
            for(int i = 0; i < expressions.size(); i++){
                var param = funParams.get(i);
                var arg = expressions.get(i);
                Value value = currentScope.resolveVariable(expressions.get(i).getText());
                Value copyValue = new Value(value);
                functionScope.declareVariable(param.typeParam().ID().getText(), value);
            }
        }

        // Saving function call operator overloads into the function scope, if there is any
        if(ctx.argumentsList() != null) {
            var opOverloads = ctx.argumentsList().operatorOverload();
            for (int i = 0; i < opOverloads.size(); i++) {
                if (opOverloads.get(i).relationOp(0) != null)
                    functionScope.overLoadOperator(opOverloads.get(i).relationOp(0).getText(), opOverloads.get(i).relationOp(1).getText());
                else
                    functionScope.overLoadOperator(opOverloads.get(i).arithmeticOp(0).getText(), opOverloads.get(i).arithmeticOp(1).getText());
            }
        }

        // switch scopes and after completing function body switching scopes back
        scopeStack.push(currentScope);
        currentScope = functionScope;
        Object returnValue = this.visitFunctionBody(funCtx.functionBody());
        currentScope = scopeStack.pop();
        if(funCtx.TYPE() != null){
            if(returnValue == null) throw new RuntimeException("Return statement is empty");
            returnValue = Type.castObjectToType(returnValue, funCtx.TYPE().getText());
            return returnValue;
        }
        else{
            return null;
        }
    }

    @Override
    public Object visitFunctionBody(GLangParser.FunctionBodyContext ctx) {
        Object value = null;
        try {
            value = super.visitFunctionBody(ctx);
        }
        catch (MVPReturnStatementException returned){
            value = returned.object;
        }
        return value;
    }

    @Override
    public Object visitReturnStatement(GLangParser.ReturnStatementContext ctx) {
        if(ctx.expression() != null){
            Object value = visit(ctx.expression());
            throw new MVPReturnStatementException(value);
        }
        else{
            throw new MVPReturnStatementException(null);
        }
    }

    public String getOperatorOverload(String oldOp){
        String operator = currentScope.resolveOperator(oldOp);
        if(operator == null){
            return oldOp;
        }
        else {
            return operator;
        }
    }
}
