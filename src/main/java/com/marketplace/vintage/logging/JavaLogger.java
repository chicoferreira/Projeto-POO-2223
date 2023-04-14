package com.marketplace.vintage.logging;

public class JavaLogger implements Logger {

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void warn(String message) {
        System.err.println(message);
    }
}
