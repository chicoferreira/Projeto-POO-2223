package com.marketplace.vintage.expression;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ExpressionSolver {

    public static BigDecimal solve(String expression, Map<String, Double> variables) {
        List<String> variablesList = variables.keySet().stream().toList();
        return BigDecimal.valueOf(build(expression, variablesList).setVariables(variables).evaluate());
    }

    public static boolean isValid(String expression, List<String> possibleVariables) {
        return build(expression, possibleVariables).validate(false).isValid();
    }

    private static net.objecthunter.exp4j.Expression build(String expression, List<String> variables) {
        try {
            ExpressionBuilder builder = new ExpressionBuilder(expression);
            for (String variable : variables) {
                builder.variable(variable);
            }

            return builder.build();
        } catch (Exception e) {
            throw  new IllegalArgumentException("Expression variables must be in: " + variables);
        }
    }

}
