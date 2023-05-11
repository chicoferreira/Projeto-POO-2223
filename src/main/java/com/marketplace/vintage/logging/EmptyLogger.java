package com.marketplace.vintage.logging;

public class EmptyLogger implements Logger {

    public static final EmptyLogger INSTANCE = new EmptyLogger();

    @Override
    public void print(String message) {
    }

    @Override
    public void info(String message) {
    }

    @Override
    public void warn(String message) {
    }
}
