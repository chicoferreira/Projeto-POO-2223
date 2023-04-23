package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.command.ParentCommand;

public class ItemCommand extends ParentCommand {

    public ItemCommand() {
        super("item", "Item management commands");
        registerCommand(new ItemCreateCommand());
    }

}
