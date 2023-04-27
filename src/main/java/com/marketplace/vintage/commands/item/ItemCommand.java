package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;

public class ItemCommand extends ParentCommand {

    public ItemCommand(ParcelCarrierManager parcelCarrierManager, ItemFactory itemFactory, ItemManager itemManager) {
        super("item", "Item management commands");
        registerCommand(new ItemCreateCommand(parcelCarrierManager, itemFactory, itemManager));
    }

}
