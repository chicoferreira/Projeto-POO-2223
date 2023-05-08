package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.user.UserManager;

public class UserCommandAdminView extends ParentCommand {

        public UserCommandAdminView(UserManager userManager) {
            super("user", "User commands");
            registerCommand(new UserListCommand(userManager));
        }
}
