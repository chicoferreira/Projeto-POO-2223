package com.marketplace.vintage.expression;

import com.marketplace.vintage.VintageConstants;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionTest {

    @Test
    void defaultExpression() {
        Expression expression = ExpressionFactory.createExpression(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES);

        assertTrue(expression.isValid());
        assertEquals(expression.getVariables().get(0), "basePrice");
        assertEquals(expression.getVariables().get(1), "tax");

        // Assign values to the expression variables
        Map<String, Double> variables = Map.of("basePrice", 100.0, "tax", 0.1);
        assertEquals(expression.solve(variables), BigDecimal.valueOf(9.9));
    }

}
