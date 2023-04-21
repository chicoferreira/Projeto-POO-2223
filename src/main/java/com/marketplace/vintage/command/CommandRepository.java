package com.marketplace.vintage.command;

public interface CommandRepository {

    void registerCommand(Command command);

    Command getCommand(String name);

}
