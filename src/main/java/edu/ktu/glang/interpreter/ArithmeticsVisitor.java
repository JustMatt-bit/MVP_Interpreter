package edu.ktu.glang.interpreter;

import edu.ktu.glang.GLangBaseVisitor;
import edu.ktu.glang.GLangParser;

public class ArithmeticsVisitor extends GLangBaseVisitor<Object> {
    private final InterpreterVisitor parent;
    public ArithmeticsVisitor(InterpreterVisitor parent) {
        this.parent = parent;
    }

    @Override
    public Object visitAddOpExpression(GLangParser.AddOpExpressionContext ctx) {
        Object val1 = parent.visit(ctx.expression(0));
        Object val2 = parent.visit(ctx.expression(1));
        String operator = parent.getOperatorOverload(ctx.addOp().getText());
        if(val1 instanceof Integer && val2 instanceof Integer) {
            return switch (operator) {
                case "+" -> (Integer) val1 + (Integer) val2;
                case "-" -> (Integer) val1 - (Integer) val2;
                case "*" -> (Integer) val1 * (Integer) val2;
                case "/" -> (Integer) val1 / (Integer) val2;
                case "%" -> (Integer) val1 % (Integer) val2;
                default -> null;
            };
        }
        else if (val1 instanceof Double && val2 instanceof Double) {
            return switch (operator) {
                case "+" -> (Double) val1 + (Double) val2;
                case "-" -> (Double) val1 - (Double) val2;
                case "*" -> (Double) val1 * (Double) val2;
                case "/" -> (Double) val1 / (Double) val2;
                case "%" -> (Double) val1 % (Double) val2;
                default -> null;
            };
        }
        else if(val1 instanceof Double && val2 instanceof Integer) {
            return switch (operator) {
                case "+" -> (Double) val1 + (Integer) val2;
                case "-" -> (Double) val1 - (Integer) val2;
                case "*" -> (Double) val1 * (Integer) val2;
                case "/" -> (Double) val1 / (Integer) val2;
                case "%" -> (Double) val1 % (Integer) val2;
                default -> null;
            };
        }
        else {
            return switch (operator) {
                case "+" -> (Integer) val1 + (Double) val2;
                case "-" -> (Integer) val1 - (Double) val2;
                case "*" -> (Integer) val1 * (Double) val2;
                case "/" -> (Integer) val1 / (Double) val2;
                case "%" -> (Integer) val1 % (Double) val2;
                default -> null;
            };
        }
    }

    @Override
    public Object visitMultiOpExpression(GLangParser.MultiOpExpressionContext ctx) {
        Object val1 = parent.visit(ctx.expression(0));
        Object val2 = parent.visit(ctx.expression(1));
        //TODO - validation etc
        String operator = parent.getOperatorOverload(ctx.multiOp().getText());
        if(val1 instanceof Integer && val2 instanceof Integer) {
            return switch (operator) {
                case "*" -> (Integer) val1 * (Integer) val2;
                case "/" -> (Integer) val1 / (Integer) val2;
                case "%" -> (Integer) val1 % (Integer) val2;
                case "+" -> (Integer) val1 + (Integer) val2;
                case "-" -> (Integer) val1 - (Integer) val2;
                default -> null;
            };
        }
        else if (val1 instanceof Double && val2 instanceof Double) {
            return switch (operator) {
                case "*" -> (Double) val1 * (Double) val2;
                case "/" -> (Double) val1 / (Double) val2;
                case "%" -> (Double) val1 % (Double) val2;
                case "+" -> (Double) val1 + (Double) val2;
                case "-" -> (Double) val1 - (Double) val2;
                default -> null;
            };
        }
        else if(val1 instanceof Double && val2 instanceof Integer) {
            return switch (operator) {
                case "*" -> (Double) val1 * (Integer) val2;
                case "/" -> (Double) val1 / (Integer) val2;
                case "%" -> (Double) val1 % (Integer) val2;
                case "+" -> (Double) val1 + (Integer) val2;
                case "-" -> (Double) val1 - (Integer) val2;
                default -> null;
            };
        }
        else {
            return switch (operator) {
                case "*" -> (Integer) val1 * (Double) val2;
                case "/" -> (Integer) val1 / (Double) val2;
                case "%" -> (Integer) val1 % (Double) val2;
                case "+" -> (Integer) val1 + (Double) val2;
                case "-" -> (Integer) val1 - (Double) val2;
                default -> null;
            };
        }
    }
}
