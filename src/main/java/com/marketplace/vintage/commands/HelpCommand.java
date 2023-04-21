package com.marketplace.vintage.commands;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.command.CommandRepository;
import com.marketplace.vintage.logging.Logger;

public class HelpCommand extends BaseCommand {

    private final CommandRepository commandRepository;

    public HelpCommand(CommandRepository commandRepository) {
        super("help", "help", 0, "Show help");
        this.commandRepository = commandRepository;
    }

    @Override
    public void execute(Logger logger, String[] args) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
