package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

public class UserInfoCommand extends BaseCommand {

    private final UserView userView;

    public UserInfoCommand(UserView userView) {
        super("userinfo", "userinfo", 0, "Displays the current user's information");
        this.userView = userView;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentUser = userView.getCurrentLoggedInUser();

        logger.info(currentUser.getName() + "'s information");
        logger.info(" - Email: " + currentUser.getEmail());
        logger.info(" - Address: " + currentUser.getAddress());
        logger.info(" - Tax Number: " + currentUser.getTaxNumber());
        logger.info(" - Internal identifier: " + currentUser.getId());
    }
}
