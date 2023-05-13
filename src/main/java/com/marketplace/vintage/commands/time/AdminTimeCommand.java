package com.marketplace.vintage.commands.time;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;

public class AdminTimeCommand extends ParentCommand {

    public AdminTimeCommand(Vintage vintage) {
        super("time", "Time related commands");
        registerCommand(new AdminTimeJumpCommand(vintage));
        registerCommand(new CurrentTimeCommand(vintage, "time time"));
    }
}
