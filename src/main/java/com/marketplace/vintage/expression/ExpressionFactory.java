package com.marketplace.vintage.expression;

import java.util.List;

public class ExpressionFactory {

    public static Expression createExpression(String expression, List<String> variables) {
        return new Exp4jExpression(expression, variables);
    }

}
