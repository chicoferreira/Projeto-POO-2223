package com.marketplace.vintage.input;

import com.marketplace.vintage.logging.Logger;

import java.util.function.Function;

public interface InputPrompter {

    String getInput();

    String askForInput(Logger logger, String message);

    <T> T askForInput(Logger logger, String message, Function<String, T> mapper);

}
