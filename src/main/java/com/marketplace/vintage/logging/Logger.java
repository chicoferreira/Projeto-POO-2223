package com.marketplace.vintage.logging;

public interface Logger {

    default void info() {
        info("");
    }

    void print(String message);

    void info(String message);

    void warn(String message);

}
