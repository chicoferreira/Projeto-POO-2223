package com.marketplace.vintage.logging;

public class OnlyWarnLogger implements Logger {

    public static Logger of(Logger logger) {
        return new OnlyWarnLogger(logger);
    }

    private final Logger logger;

    public OnlyWarnLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void print(String message) {
    }

    @Override
    public void info(String message) {
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }
}
