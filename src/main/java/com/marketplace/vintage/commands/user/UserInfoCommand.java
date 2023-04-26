package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

public class UserInfoCommand extends BaseCommand {

    private UserView userView;

    public UserInfoCommand(UserView userView) {
        super("user info", "user info", 0, "Displays the current user's information");
        this.userView = userView;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentUser = userView.getCurrentLoggedInUser();
        if(currentUser == null) logger.warn("User not logged in");

        logger.info("Current User Info:");
        logger.info("ID: " + currentUser.getId());
        logger.info("Name: " + currentUser.getName());
        logger.info("E-mail: " + currentUser.getEmail());
        logger.info("Address:  " + currentUser.getAddress());
        logger.info("Tax Number: " + currentUser.getTaxNumber());
    }
}
