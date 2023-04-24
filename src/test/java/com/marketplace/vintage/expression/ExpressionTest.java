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
        Expression expression = ExpressionBuilder.newBuilder()
                .addVariable("basePrice")
                .addVariable("tax")
                .build(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);

        assertTrue(expression.isValid());
        assertEquals(expression.getVariables().get(0), "basePrice");
        assertEquals(expression.getVariables().get(1), "tax");

        // Assign values to the expression variables
        Map<String, Double> variables = new HashMap<>();
        variables.put("basePrice", 100.0);
        variables.put("tax", 0.1);
        BigDecimal result = expression.solve(variables);

        assertEquals(result, BigDecimal.valueOf(9.9));
    }

}
