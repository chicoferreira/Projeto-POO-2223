package com.marketplace.vintage.expression;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface Expression {

    BigDecimal solve(Map<String, Double> variables);

    List<String> getVariables();

    boolean isValid();

}
