package com.marketplace.vintage.expression;

import java.util.ArrayList;
import java.util.List;
public class ExpressionBuilder {

    private final List<String> variables;

    public ExpressionBuilder() {
        this.variables = new ArrayList<>();
    }

    public static ExpressionBuilder newBuilder() {
        return new ExpressionBuilder();
    }

    public ExpressionBuilder addVariable(String variable) {
        this.variables.add(variable);
        return this;
    }

    public ExpressionBuilder withVariables(List<String> variables) {
        this.variables.addAll(variables);
        return this;
    }

    public Expression build(String expression) {
        return new Exp4jExpression(expression, this.variables);
    }
}
