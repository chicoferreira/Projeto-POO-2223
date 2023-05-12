package com.marketplace.vintage.logging;

public interface Logger {

    /**
     * Prints an empty line to the console
     */
    default void info() {
        info("");
    }

    /**
     * Prints the message with no new line to the console
     * @param message the message to print
     */
    void print(String message);

    /**
     * Prints the message with a new line to the console
     * @param message the message to print
     */
    void info(String message);

    /**
     * Prints the error message with a new line to the console
     * @param message the message to print
     */
    void warn(String message);

}
