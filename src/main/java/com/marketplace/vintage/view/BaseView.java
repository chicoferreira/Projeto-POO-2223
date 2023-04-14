package com.marketplace.vintage.view;

import com.marketplace.vintage.command.CommandRepository;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.terminal.Terminal;

public abstract class BaseView implements View {

    private final Logger logger;
    private final Terminal terminal;
    private final CommandRepository commandRepository;

    public BaseView(Logger logger, Terminal terminal) {
        this.logger = logger;
        this.terminal = terminal;
        this.commandRepository = new CommandRepository();
    }

    public CommandRepository getCommandRepository() {
        return commandRepository;
    }

    public Logger getLogger() {
        return logger;
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
