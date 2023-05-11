package com.marketplace.vintage.view;

import com.marketplace.vintage.command.CommandExecuteException;
import com.marketplace.vintage.command.CommandManager;
import com.marketplace.vintage.commands.HelpCommand;
import com.marketplace.vintage.commands.LogoutCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

public abstract class BaseView implements CommandRunnerView {

    private final InputPrompter inputPrompter;
    private final CommandManager commandManager;
    private Logger logger;
    private boolean shouldExit;

    public BaseView(Logger logger, InputPrompter inputPrompter, String helpTitle) {
        this.logger = logger;
        this.inputPrompter = inputPrompter;
        this.commandManager = new CommandManager();

        this.getCommandManager().registerCommand(new HelpCommand(getCommandManager(), helpTitle));
        this.getCommandManager().registerCommand(new LogoutCommand(this));
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public InputPrompter getInputPrompter() {
        return inputPrompter;
    }

    public void setShouldExit() {
        this.shouldExit = true;
    }

    public void run() {
        this.shouldExit = false;
        while (!shouldExit) {
            String commandName = getInputPrompter().askForInput(getLogger(), ">");
            try {
                commandManager.executeRawCommand(logger, getInputPrompter(), commandName);
            } catch (CommandExecuteException e) {
                logger.warn("An error occurred while executing the command: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
