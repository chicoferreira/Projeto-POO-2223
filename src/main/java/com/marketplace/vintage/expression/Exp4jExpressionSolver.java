package com.marketplace.vintage.expression;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exp4jExpressionSolver implements ExpressionSolver {

    @Override
    public BigDecimal solve(String expression, Map<String, BigDecimal> variables) {
        List<String> variablesList = variables.keySet().stream().toList();

        Map<String, Double> transformedVariables = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : variables.entrySet()) {
            transformedVariables.put(entry.getKey(), entry.getValue().doubleValue());
        }

        return BigDecimal.valueOf(build(expression, variablesList).setVariables(transformedVariables).evaluate())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean isValid(String expression, List<String> possibleVariables) {
        return build(expression, possibleVariables).validate(false).isValid();
    }

    private static net.objecthunter.exp4j.Expression build(String expression, List<String> variables) {
        ExpressionBuilder builder = new ExpressionBuilder(expression);
        for (String variable : variables) {
            builder.variable(variable);
        }

        return builder.build();
    }

}
