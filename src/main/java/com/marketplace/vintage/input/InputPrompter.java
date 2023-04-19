package com.marketplace.vintage.input;

import com.marketplace.vintage.logging.Logger;

import java.util.Scanner;
import java.util.function.Function;

public class InputPrompter {

    private final Scanner scanner;

    public InputPrompter() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return scanner.nextLine();
    }

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
