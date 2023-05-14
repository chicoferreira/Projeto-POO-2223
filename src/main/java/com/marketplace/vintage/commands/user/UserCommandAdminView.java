package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;

public class UserCommandAdminView extends ParentCommand {

    public UserCommandAdminView(Vintage vintage) {
        super("user", "User related commands");
        registerCommand(new UserListCommand(vintage));
        registerCommand(new UserRegisterCommand(vintage));
    }
}
