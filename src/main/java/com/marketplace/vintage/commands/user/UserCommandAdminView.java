package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;

public class UserCommandAdminView extends ParentCommand {

        public UserCommandAdminView(VintageController vintageController) {
            super("user", "User commands");
            registerCommand(new UserListCommand(vintageController));
        }
}
