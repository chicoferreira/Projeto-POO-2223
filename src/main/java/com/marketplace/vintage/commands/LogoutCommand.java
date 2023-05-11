package com.marketplace.vintage.commands;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.view.BaseView;

public class LogoutCommand extends BaseCommand {

    private final BaseView targetView;

    public LogoutCommand(BaseView targetView) {
        super("logout", "logout", 0, "Logs out of the current view");
        this.targetView = targetView;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        this.targetView.setShouldExit();
        logger.info("Logging out...");
        logger.info();
    }
}
