package com.marketplace.vintage;

import com.marketplace.vintage.logging.JavaLogger;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.terminal.Terminal;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;
import com.marketplace.vintage.view.ViewType;

import java.util.stream.Collectors;

public class VintageApplication {

    private final Terminal terminal;
    private final Logger logger;
    private final ViewFactory viewFactory;

    public VintageApplication() {
        this.logger = new JavaLogger();
        this.terminal = new Terminal();
        UserManager userManager = new UserManager();
        this.viewFactory = new ViewFactory(logger, terminal, userManager);
    }

    public void start() {
        logger.info("Welcome to the Vintage Marketplace!");
        logger.info();

        String availableViews = buildAllViewsString() + " or Exit (exit)";

        while (true) {
            logger.info("Choose which view you want to use.");
            logger.info("Available views: " + availableViews);

            View view = null;
            while (view == null) {
                String viewTypeName = terminal.askForInput();
                if (viewTypeName.equals("exit")) {
                    return;
                }
                ViewType viewType = ViewType.fromCommandName(viewTypeName);

                if (viewType == null) {
                    logger.info("Invalid view name.");
                    logger.info("Please type one of the following: " + availableViews);
                    continue;
                }
                view = viewFactory.createView(viewType);
            }

            view.run();
        }
    }

    public String buildAllViewsString() {
        return ViewType.getAllViewTypes().stream()
                       .map(viewType -> viewType.getDisplayName() + " (" + viewType.getCommandName() + ")")
                       .collect(Collectors.joining(", "));
    }

}
