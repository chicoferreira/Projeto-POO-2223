package com.marketplace.vintage.view;

import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.terminal.Terminal;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.impl.UserView;

public class ViewFactory {

    private final Logger logger;
    private final Terminal terminal;
    private final UserManager userManager;

    public ViewFactory(Logger logger, Terminal terminal, UserManager userManager) {
        this.logger = logger;
        this.terminal = terminal;
        this.userManager = userManager;
    }

    public View createView(ViewType viewType) {
        return switch (viewType) {
            case USER -> new UserView(PrefixLogger.of("USER", logger), terminal, userManager);
            case CARRIER -> throw new UnsupportedOperationException("Not implemented yet");
        };
    }

}
