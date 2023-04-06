package com.marketplace.vintage.command;

public abstract class BaseCommand implements Command {

    private final String name;
    private final String usage;

    public BaseCommand(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
