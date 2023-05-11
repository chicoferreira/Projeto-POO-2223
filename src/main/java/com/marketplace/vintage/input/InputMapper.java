package com.marketplace.vintage.input;

import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.ItemConditions;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static final Function<String, BigDecimal> BIG_DECIMAL = (String input) -> {
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value must be a decimal number");
        }
    };

    public static Function<String, ItemCondition> ofItemCondition(InputPrompter inputPrompter, Logger logger) {
        return (String input) -> {
            if (input.equalsIgnoreCase("new")) {
                return ItemConditions.NEW;
            } else if (input.equalsIgnoreCase("used")) {
                int conditionLevel = inputPrompter.askForInput(logger, "Insert the condition of the item (1/10, 1 being unusable, 10 perfect condition):", InputMapper.ofIntRange(1, 10));
                int previousOwners = inputPrompter.askForInput(logger, "Insert how many previous owners has the item had:", InputMapper.ofIntRange(0, Integer.MAX_VALUE));
                return ItemConditions.createUsed(conditionLevel, previousOwners);
            } else {
                throw new IllegalArgumentException("Value must be 'new' or 'used'");
            }
        };
    }

    public static Function<String, String> ofExpression(BiFunction<String, List<String>, Boolean> expressionValidator, List<String> variables) {
        return (String input) -> {
            try {
                boolean isValid = expressionValidator.apply(input, variables);
                if (!isValid) {
                    throw new IllegalArgumentException("Expression must be valid");
                }
                return input;
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        };
    }

    public static <T> Function<String, T> ofPossibleValues(Map<String, T> possibleValues) {
        return (String input) -> {
            Map<String, T> possibleValuesLowercase = new HashMap<>();
            for (String s : possibleValues.keySet()) {
                String newKey = s.toLowerCase().replace('_', ' ');
                possibleValuesLowercase.put(newKey, possibleValues.get(s));
            }

            T value = possibleValuesLowercase.get(input.toLowerCase());
            if (value == null) {
                throw new IllegalArgumentException("Value must be one of " + StringUtils.joinQuoted(possibleValuesLowercase.keySet(), ", "));
            }
            return value;
        };
    }

    public static <T extends Enum<T>> Function<String, T> ofEnumValues(Class<T> enumClass) {
        return ofPossibleValues(EnumSet.allOf(enumClass).stream().collect(Collectors.toMap(Enum::name, Function.identity())));
    }
}
