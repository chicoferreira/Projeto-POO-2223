package com.marketplace.vintage.input.impl;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import java.util.function.Function;

public abstract class BaseInputPrompter implements InputPrompter {

    public String askForInput(Logger logger, String message) {
        logger.print(message + " ");
        return getInput();
    }

    public <T> T askForInput(Logger logger, String message, Function<String, T> mapper) {
        String input = askForInput(logger, message);
        try {
            return mapper.apply(input);
        } catch (Exception e) {
            logger.warn("Error: " + e.getMessage());
            return askForInput(logger, message, mapper);
        }
    }

}
