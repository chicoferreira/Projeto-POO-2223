package com.marketplace.vintage.commands;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.command.Command;
import com.marketplace.vintage.command.CommandRepository;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import java.util.Comparator;
import java.util.List;

public class HelpCommand extends BaseCommand {

    private final CommandRepository commandRepository;
    private final String helpTitle;

    public HelpCommand(CommandRepository commandRepository, String helpTitle) {
        this(commandRepository, "help", helpTitle);
    }

    public HelpCommand(CommandRepository commandRepository, String usage, String helpTitle) {
        super("help", usage, 0, "Show this help");
        this.commandRepository = commandRepository;
        this.helpTitle = helpTitle;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        List<Command> registeredCommands = this.commandRepository.getRegisteredCommands();
        if (registeredCommands.isEmpty()) {
            logger.info("No commands to show in " + this.helpTitle);
            return;
        }

        // Sort alphabetically by usage
        registeredCommands.sort(Comparator.comparing(Command::getUsage));

        logger.info("Available commands in " + this.helpTitle + ":");
        for (Command registeredCommand : registeredCommands) {
            logger.info(" - " + registeredCommand.getUsage() + " - " + registeredCommand.getDescription());
        }
    }

}
