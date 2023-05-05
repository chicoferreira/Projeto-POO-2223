package com.marketplace.vintage.expression;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpressionSolver {

    BigDecimal solve(String expression, Map<String, BigDecimal> variables);

    boolean isValid(String expression, List<String> possibleVariables);

}
