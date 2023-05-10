package edu.ktu.glang.interpreter;

import edu.ktu.glang.GLangBaseVisitor;
import edu.ktu.glang.GLangParser;
import org.antlr.v4.runtime.tree.TerminalNode;

public class IfStatementVisitor extends GLangBaseVisitor<Object> {

    private final InterpreterVisitor parent;

    public IfStatementVisitor(InterpreterVisitor parent) {
        this.parent = parent;
    }

    @Override
    public Object visitIfStatement(GLangParser.IfStatementContext ctx) {
        // Get the left and right expressions from the context
        Object left = parent.visit(ctx.expression(0));
        Object right = parent.visit(ctx.expression(1));

        // Get the relation operator from the context
        TerminalNode relOpNode = (TerminalNode) ctx.relationOp().getChild(0);
        String relOp = relOpNode.getText();

        System.out.println(left.getClass().toString());

        // Resolve the condition and execute the appropriate statement
        if (resolveCondition(left, right, relOp)) {
            return parent.visit(ctx.statement(0));
        } else if (ctx.statement(1) != null) {
            return parent.visit(ctx.statement(1));
        } else {
            return null; // or return a default value or another appropriate action
        }
    }

    private boolean resolveCondition(Object left, Object right, String relOp) {
        switch (relOp) {
            case "==" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return (Integer)left == (Integer)right;
                } else if (left instanceof String && right instanceof String) {
                    return ((String) left).equals(right);
                } else {
                    throw new RuntimeException("Incompatible types.");
                }
            }
            case "!=" -> {
                if(left instanceof Integer && right instanceof Integer) {
                    return (Integer)left != (Integer)right;
                } else if (left instanceof String && right instanceof String) {
                    return !((String) left).equals(right);
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