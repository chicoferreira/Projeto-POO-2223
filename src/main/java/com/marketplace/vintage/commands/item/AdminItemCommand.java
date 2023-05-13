package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;

public class AdminItemCommand extends ParentCommand {

    public AdminItemCommand(Vintage vintage) {
        super("item", "Item management commands");
        registerCommand(new ItemInspectCommand(vintage));
        registerCommand(new ItemListAllCommand(vintage));
    }

}
