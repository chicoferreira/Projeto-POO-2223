package com.marketplace.vintage.expression;

import com.marketplace.vintage.VintageConstants;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Exp4jExpressionSolverTest {

    private final ExpressionSolver expressionSolver = new Exp4jExpressionSolver();

    @Test
    void isValid() {
        assertTrue(expressionSolver.isValid(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES));

        List<String> variables = List.of("itemsPrice");
        assertThrows(IllegalArgumentException.class, () -> expressionSolver.isValid("itemsPrice * tax + 0.1", variables));
    }

    @Test
    void defaultExpression() {
        String expression = VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING;
        BigDecimal result = expressionSolver.solve(expression,
                Map.of(VintageConstants.EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE, BigDecimal.valueOf(100.0),
                        VintageConstants.EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE, BigDecimal.valueOf(0.1)));
        assertEquals(BigDecimal.valueOf(99.0).compareTo(result), 0);
    }

}
