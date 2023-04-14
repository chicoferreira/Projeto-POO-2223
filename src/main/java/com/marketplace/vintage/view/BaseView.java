package com.marketplace.vintage.view;

import com.marketplace.vintage.command.CommandRepository;

public abstract class BaseView implements View {

    private final CommandRepository commandRepository;

    public BaseView() {
        this.commandRepository = new CommandRepository();
    }

    public CommandRepository getCommandRepository() {
        return commandRepository;
    }
}
