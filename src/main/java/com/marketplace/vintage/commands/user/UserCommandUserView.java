package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class UserCommandUserView extends ParentCommand {

    public UserCommandUserView(UserView userView) {
        super("user", "User related commands");
        registerCommand(new UserInfoCommand(userView));
    }

}
