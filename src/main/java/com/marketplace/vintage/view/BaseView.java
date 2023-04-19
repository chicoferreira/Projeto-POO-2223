package com.marketplace.vintage.view;

import com.marketplace.vintage.command.CommandRepository;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

public abstract class BaseView implements View {

    private final Logger logger;
    private final InputPrompter inputPrompter;
    private final CommandRepository commandRepository;

    public BaseView(Logger logger, InputPrompter inputPrompter) {
        this.logger = logger;
        this.inputPrompter = inputPrompter;
        this.commandRepository = new CommandRepository();
    }

    public CommandRepository getCommandRepository() {
        return commandRepository;
    }

    public Logger getLogger() {
        return logger;
    }

    public InputPrompter getInputPrompter() {
        return inputPrompter;
    }
}
