package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.view.impl.UserView;

public class ItemCommand extends ParentCommand {

    public ItemCommand(UserView userView, ParcelCarrierManager parcelCarrierManager, VintageController vintageController, ItemManager itemManager, VintageTimeManager vintageTimeManager) {
        super("item", "Item management commands");
        registerCommand(new ItemCreateCommand(userView, parcelCarrierManager, vintageController));
        registerCommand(new ItemListCommand(itemManager, userView, parcelCarrierManager, vintageTimeManager));
    }

}
