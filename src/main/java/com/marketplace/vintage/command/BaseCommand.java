package com.marketplace.vintage.command;

import com.marketplace.vintage.input.InputPrompter;

public abstract class BaseCommand implements Command {

    private final String name;
    private final String usage;
    private final int minArgs;
    private final String description;

    private final InputPrompter inputPrompter;

    public BaseCommand(String name, String usage, int minArgs, String description) {
        this.name = name;
        this.usage = usage;
        this.minArgs = minArgs;
        this.description = description;
        this.inputPrompter = new InputPrompter();
    }

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

    public InputPrompter getInputPrompter() {
        return inputPrompter;
    }
}
