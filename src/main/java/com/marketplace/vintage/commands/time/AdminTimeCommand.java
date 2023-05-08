package com.marketplace.vintage.commands.time;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;

public class AdminTimeCommand extends ParentCommand {

    public AdminTimeCommand(VintageController vintageController) {
        super("time", "Time related commands");
        registerCommand(new AdminTimeJumpCommand(vintageController));
        registerCommand(new CurrentTimeCommand(vintageController, "time time"));
    }
}
