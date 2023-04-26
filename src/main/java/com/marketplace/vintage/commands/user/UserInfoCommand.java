package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

public class UserInfoCommand extends BaseCommand {

    private final User user;

    public UserInfoCommand(UserView userView) {
        super("info", "user info", 0, "Displays the current user's information");
        this.user = userView.getCurrentLoggedInUser();
    }
    @Override
    protected void executeSafely(Logger logger, String[] args) {

        if(this.user == null) throw new IllegalStateException("User is not logged in");

        logger.info("Current User Info:");
        logger.info("ID: " + user.getId());
        logger.info("Name: " + user.getName());
        logger.info("E-mail: " + user.getEmail());
        logger.info("Address:  " + user.getAddress());
        logger.info("Tax Number: " + user.getTaxNumber());
    }
}
