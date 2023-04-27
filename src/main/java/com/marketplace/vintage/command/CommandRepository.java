package com.marketplace.vintage.command;

import java.util.List;

public interface CommandRepository {

    void registerCommand(Command command);

    Command getCommand(String name);

    List<Command> getRegisteredCommands();

}
