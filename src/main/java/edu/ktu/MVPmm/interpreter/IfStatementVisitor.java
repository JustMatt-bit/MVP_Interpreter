package edu.ktu.MVPmm.interpreter;

import edu.ktu.MVPmm.MVPmmBaseVisitor;
import edu.ktu.MVPmm.MVPmmParser;

public class IfStatementVisitor extends MVPmmBaseVisitor<Object> {

    private final InterpreterVisitor parent;

    public IfStatementVisitor(InterpreterVisitor parent) {
        this.parent = parent;
    }

    @Override
    public Object visitIfStatement(MVPmmParser.IfStatementContext ctx) {
        Boolean ifCondition = null;
        if(ctx.condition().relationOp() != null){
            // Get the left and right expressions from the context
            Object left = parent.visit(ctx.condition().expression(0));
            Object right = parent.visit(ctx.condition().expression(1));

            // Get the relation operator from the context
            //TerminalNode relOpNode = (TerminalNode) ctx.condition().relationOp().getChild(0);
            String operator = parent.getOperatorOverload(ctx.condition().relationOp().getText());
            ifCondition = resolveCondition(left, right, operator);
        }
        else{
            ifCondition = (Boolean) parent.visit(ctx.condition().expression(0));
        }

        // Resolve the condition and execute the appropriate statement
        if (ifCondition) {
            return parent.visit(ctx.block(0));
        } else if (ctx.block(1) != null) {
            return parent.visit(ctx.block(1));
        } else {
            return null; // or return a default value or another appropriate action
        }
    }

    private boolean resolveCondition(Object left, Object right, String relOp) {
        switch (relOp) {
            case "==" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return left == right;
                } else if (left instanceof String && right instanceof String) {
                    return (left).equals(right);
                } else {
                    throw new RuntimeException("Incompatible types.");
                }
            }
            case "!=" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return left != right;
                } else if (left instanceof String && right instanceof String) {
                    return !left.equals(right);
                } else {
                    throw new RuntimeException("Incompatible types.");
                }
            }
            case ">" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return (Integer)left > (Integer)right;
                } else {
                    throw new RuntimeException("Incompatible types or unsupported operator for these types.");
                }
            }
            case "<" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return (Integer)left < (Integer)right;
                } else {
                    throw new RuntimeException("Incompatible types or unsupported operator for these types.");
                }
            }
            case ">=" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return (Integer)left >= (Integer)right;
                } else {
                    throw new RuntimeException("Incompatible types or unsupported operator for these types.");
                }
            }
            case "<=" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return (Integer)left <= (Integer)right;
                } else {
                    throw new RuntimeException("Incompatible types or unsupported operator for these types.");
                }
            }
            default -> throw new RuntimeException("Unsupported operator.");
        }
    }
}