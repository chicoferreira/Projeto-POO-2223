package com.marketplace.vintage.command;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandManager implements CommandRepository { // CommandManager is a CommandRepository to be easier to implement "> help" command

    private static final Function<String, String> KEY_TRANSFORMATION = String::toLowerCase;
    private final Map<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();
    }

    @Override
    public void registerCommand(Command command) {
        this.commands.put(KEY_TRANSFORMATION.apply(command.getName()), command);
    }

    @Override
    public @Nullable Command getCommand(String name) {
        return this.commands.get(KEY_TRANSFORMATION.apply(name));
    }

    @Override
    public List<Command> getRegisteredCommands() {
        return new ArrayList<>(this.commands.values());
    }

    public void executeRawCommand(Logger logger, InputPrompter inputPrompter, String rawCommand) {
        rawCommand = rawCommand.trim();
        String[] parts = rawCommand.split("\\s+");

        if (parts.length == 0) {
            return;
        }

        String commandName = parts[0];

        Command command = getCommand(commandName);
        if (command == null) {
            logger.warn("Unknown command: " + commandName + " (type 'help' for a list of commands)");
            return;
        }

        String[] args = new String[parts.length - 1];

        // TODO: Show usage if not enough arguments are provided
        System.arraycopy(parts, 1, args, 0, args.length);

        try {
            command.execute(logger, inputPrompter, args);
        } catch (Exception e) {
            throw new CommandExecuteException(e);
        }
    }
}
