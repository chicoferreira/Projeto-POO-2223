package com.marketplace.vintage.expression;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Exp4jExpression implements Expression {

    private final net.objecthunter.exp4j.Expression expression;
    private final List<String> variables;

    public Exp4jExpression(String expression, List<String> variables) {
        ExpressionBuilder builder = new ExpressionBuilder(expression);
        for (String variable : variables) {
            builder.variable(variable);
        }

        this.expression = builder.build();
        this.variables = variables;
    }

    @Override
    public BigDecimal solve(Map<String, Double> variables) {
        expression.setVariables(variables);
        return BigDecimal.valueOf(expression.evaluate());
    }

    @Override
    public List<String> getVariables() {
        return this.variables;
    }

    @Override
    public boolean isValid() {
        return expression.validate(false).isValid();
    }
}
