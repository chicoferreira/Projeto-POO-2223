package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;

import java.util.List;

public class UserListCommand extends BaseCommand {

    private final Vintage vintage;

    public UserListCommand(Vintage vintage) {
        super("list", "user list", 0, "Lists all registered users");
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        List<User> usersList = vintage.getAllUsers();

        if (usersList.isEmpty()) {
            logger.info("There are no registered users.");
            return;
        }

        for (User user : usersList) {
            String message = VintageConstants.DISPLAY_USER_FORMAT
                    .replace("<id>", String.valueOf(user.getId()))
                    .replace("<name>", user.getName())
                    .replace("<email>", user.getEmail())
                    .replace("<username>", user.getUsername());

            logger.info(message);
        }
    }

}
