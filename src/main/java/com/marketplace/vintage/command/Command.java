package com.marketplace.vintage.command;

public interface Command {

    String getName();

    String getUsage();

    void execute(String[] args);

}
