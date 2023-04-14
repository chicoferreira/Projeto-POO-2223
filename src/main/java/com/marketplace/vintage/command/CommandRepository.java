package com.marketplace.vintage.command;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandRepository {

    private static final Function<String, String> KEY_TRANSFORMATION = String::toLowerCase;
    private final Map<String, Command> commands;

    public CommandRepository() {
        this.commands = new HashMap<>();
    }

    public void registerCommand(Command command) {
        this.commands.put(KEY_TRANSFORMATION.apply(command.getName()), command);
    }

    private @Nullable Command getCommand(String name) {
        return this.commands.get(KEY_TRANSFORMATION.apply(name));
    }

    public void executeCommand(String rawCommand) {
        rawCommand = rawCommand.trim();
        String[] parts = rawCommand.split("\\s+");

        if (parts.length == 0) {
            return;
        }

        String commandName = parts[0];

        Command command = getCommand(commandName);
        if (command == null) {
            // TODO: Log command not found
            return;
        }

        String[] args = new String[parts.length - 1];

        // TODO: Show usage if not enough arguments are provided
        System.arraycopy(parts, 1, args, 0, args.length);

        command.execute(args);
    }
}
