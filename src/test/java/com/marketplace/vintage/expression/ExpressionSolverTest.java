package com.marketplace.vintage.expression;

import com.marketplace.vintage.VintageConstants;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


public class ExpressionSolverTest {

    @Test
    void isValid() {
        assertTrue(ExpressionSolver.isValid(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES));

        List<String> variables = List.of("basePrice");
        assertThrowsExactly(IllegalArgumentException.class, () -> ExpressionSolver.isValid("basePrice * tax + 0.1", variables));
    }

    @Test
    void defaultExpression() {
        String expression = VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING;
        BigDecimal result = ExpressionSolver.solve(expression, Map.of("basePrice", 100.0, "tax", 0.1));
        assertEquals(BigDecimal.valueOf(9.9).compareTo(result), 0);
    }

}
