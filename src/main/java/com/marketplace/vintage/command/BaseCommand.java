package com.marketplace.vintage.command;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

public abstract class BaseCommand implements Command {

    private final String name;
    private final String usage;
    private final int minArgs;
    private final String description;

    public BaseCommand(String name, String usage, int minArgs, String description) {
        if (name.contains(" "))
            throw new IllegalArgumentException("Command name cannot contain spaces");

        this.name = name;
        this.usage = usage;
        this.minArgs = minArgs;
        this.description = description;
    }

    @Override
    public void execute(Logger logger, InputPrompter inputPrompter, String[] args) {
        if (args.length < minArgs) {
            logger.warn("Usage: " + usage);
            return;
        }

        executeSafely(logger, inputPrompter, args);
    }

    /**
     * Execute the command, assuming that the arguments are valid.
     */
    protected abstract void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args); //  maybe find a better name for safely

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public int getMinArgs() {
        return minArgs;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
