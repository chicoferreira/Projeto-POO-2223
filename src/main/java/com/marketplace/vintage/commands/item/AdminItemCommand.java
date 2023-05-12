package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;

public class AdminItemCommand extends ParentCommand {

    public AdminItemCommand(VintageController vintageController) {
        super("item", "Item management commands");
        registerCommand(new ItemInspectCommand(vintageController));
        registerCommand(new ItemListAllCommand(vintageController));
    }

}
