package com.marketplace.vintage.view;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.AdminView;
import com.marketplace.vintage.view.impl.UserLoginView;
import com.marketplace.vintage.view.impl.UserView;

public class ViewFactory {

    private final Logger logger;
    private final InputPrompter inputPrompter;
    private final Vintage vintage;

    public ViewFactory(Logger logger,
                       InputPrompter inputPrompter,
                       Vintage vintage) {
        this.logger = logger;
        this.inputPrompter = inputPrompter;
        this.vintage = vintage;
    }

    public View createView(ViewType viewType) {
        return switch (viewType) {
            case USER_LOGIN -> new UserLoginView(logger, inputPrompter, this, vintage);
            case ADMIN -> new AdminView(logger, inputPrompter, vintage);
        };
    }

    public UserView createUserView(User user) {
        return new UserView(logger, inputPrompter, vintage, user);
    }
}
