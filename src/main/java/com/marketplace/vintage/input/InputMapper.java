package com.marketplace.vintage.input;

import com.marketplace.vintage.expression.ExpressionSolver;

import java.util.List;
import java.util.function.Function;

public final class InputMapper {

    private InputMapper() {
    }

    public static final Function<String, String> STRING = Function.identity();

    public static Function<String, Integer> ofIntRange(int min, int max) {
        return (String input) -> {
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    throw new IllegalArgumentException("Value must be between " + min + " and " + max);
                }
                return value;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value must be an integer");
            }
        };
    }

    public static Function<String, Boolean> BOOLEAN = (String input) -> {
        if (input.equalsIgnoreCase("y")) {
            return true;
        } else if (input.equalsIgnoreCase("n")) {
            return false;
        } else {
            throw new IllegalArgumentException("Value must be 'y' or 'n'");
        }
    };

    public static Function<String, String> ofExpression(List<String> variables) {
        return (String input) -> {
            try {
                boolean isValid = ExpressionSolver.isValid(input, variables);
                if (!isValid) {
                    throw new IllegalArgumentException("Expression must be valid");
                }
                return input;
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        };
    }

}
