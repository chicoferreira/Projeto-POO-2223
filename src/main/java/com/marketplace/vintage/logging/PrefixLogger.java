package com.marketplace.vintage.logging;

public class PrefixLogger implements Logger {

    public static Logger of(String prefix, Logger logger) {
        return new PrefixLogger(logger, "[" + prefix + "] ");
    }

    private final Logger logger;
    private final String prefix;

    private PrefixLogger(Logger logger, String prefix) {
        this.logger = logger;
        this.prefix = prefix;
    }

    @Override
    public void print(String message) {
        this.logger.print(prefix + message);
    }

    @Override
    public void info(String message) {
        this.logger.info(prefix + message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(prefix + message);
    }
}
