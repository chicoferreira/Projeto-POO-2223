package com.marketplace.vintage.expression;

import com.marketplace.vintage.VintageConstants;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class Exp4jExpressionSolverTest {

    private final ExpressionSolver expressionSolver = new Exp4jExpressionSolver();

    @Test
    void isValid() {
        assertTrue(expressionSolver.isValid(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES));

        List<String> variables = List.of("basePrice");
        assertThrows(IllegalArgumentException.class, () -> expressionSolver.isValid("basePrice * tax + 0.1", variables));
    }

    @Test
    void defaultExpression() {
        String expression = VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING;
        BigDecimal result = expressionSolver.solve(expression, Map.of("basePrice", 100.0, "tax", 0.1));
        assertEquals(BigDecimal.valueOf(9.9).compareTo(result), 0);
    }

}
