package com.marketplace.vintage.input;

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
}
