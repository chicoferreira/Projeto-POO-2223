package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;

import java.util.List;

public class UserListCommand extends BaseCommand {

    private final VintageController vintageController;

    public UserListCommand(VintageController vintageController) {
        super("list", "user list", 0, "Lists all registered users");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        List<User> usersList = vintageController.getAllUsers();

        if (usersList.isEmpty()) {
            logger.info("There are no registered users.");
            return;
        }

        for (User user : usersList) {
            String message = VintageConstants.DISPLAY_USER_FORMAT
                    .replace("<id>", String.valueOf(user.getId()))
                    .replace("<name>", user.getName())
                    .replace("<email>", user.getEmail());

            logger.info(message);
        }
    }

}
