package com.marketplace.vintage.command;

import com.marketplace.vintage.commands.HelpCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParentCommand implements CommandRepository, Command {

    private final Map<String, Command> subCommands;
    private final String name;
    private final String fullName;
    private final String description;
    private final HelpCommand helpCommand;

    public ParentCommand(ParentCommand parent, String name, String description) {
        this(parent.getFullName() + " " + name, name, description);
    }

    public ParentCommand(String name, String description) {
        this(name, name, description);
    }

    private ParentCommand(String fullName, String name, String description) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.subCommands = new HashMap<>();

        this.helpCommand = new HelpCommand(this, getFullName() + " help", getFullName());
        this.registerSubCommand(helpCommand);
    }

    @Override
    public void execute(Logger logger, InputPrompter inputPrompter, String[] args) {
        if (args.length == 0) {
            helpCommand.execute(logger, inputPrompter, args);
            return;
        }

        String subCommandName = args[0];
        Command subCommand = this.subCommands.get(subCommandName);

        if (subCommand == null) {
            logger.warn("Unknown subcommand: " + subCommandName + " (type '" + this.getFullName() + " help' for a list of commands)");
            return;
        }

        String[] subCommandArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subCommandArgs, 0, subCommandArgs.length);

        subCommand.execute(logger, inputPrompter, subCommandArgs);
    }

    private void registerSubCommand(Command command) {
        this.subCommands.put(command.getName().toLowerCase(), command);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUsage() {
        return getFullName() + " <subcommand>";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void registerCommand(Command command) {
        this.registerSubCommand(command);
    }

    @Override
    public Command getCommand(String name) {
        return this.subCommands.get(name.toLowerCase());
    }

    @Override
    public List<Command> getRegisteredCommands() {
        return new ArrayList<>(this.subCommands.values());
    }

    public String getFullName() {
        return fullName;
    }
}
