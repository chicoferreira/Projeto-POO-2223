package com.marketplace.vintage.command;

import com.marketplace.vintage.commands.HelpCommand;
import com.marketplace.vintage.logging.Logger;

import java.util.HashMap;
import java.util.Map;

public class ParentCommand extends BaseCommand implements CommandRepository {

    private final Map<String, Command> subCommands;
    private final String fullName;

    public ParentCommand(ParentCommand parent, String name, String description) {
        this(parent.getFullName() + " " + name, name, description);
    }

    public ParentCommand(String name, String description) {
        this(name, name, description);
    }

    private ParentCommand(String fullName, String name, String description) {
        super(name, name + " <subcommand>", 1, description);
        this.fullName = fullName;
        this.subCommands = new HashMap<>();

        this.registerSubCommand(new HelpCommand(this));
    }

    public void registerSubCommand(Command command) {
        this.subCommands.put(command.getName().toLowerCase(), command);
    }

    @Override
    public void execute(Logger logger, String[] args) {
        if (args.length == 0) {
            logger.warn("No subcommand provided");
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

        subCommand.execute(logger, subCommandArgs);
    }

    @Override
    public void registerCommand(Command command) {
        this.registerSubCommand(command);
    }

    @Override
    public Command getCommand(String name) {
        return this.subCommands.get(name.toLowerCase());
    }

    public String getFullName() {
        return fullName;
    }
}
