package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;

public class ItemListCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final ParcelCarrierManager parcelCarrierManager;
    private final VintageTimeManager vintageTimeManager;

    public ItemListCommand(ItemManager itemManager, UserView userView, ParcelCarrierManager parcelCarrierManager, VintageTimeManager vintageTimeManager) {
        super("list", "item list", 0, "Lists all items the user is selling");
        this.itemManager = itemManager;
        this.userView = userView;
        this.parcelCarrierManager = parcelCarrierManager;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        List<String> itemsBeingSold = currentLoggedInUser.getItemsBeingSold();

        if (itemsBeingSold.isEmpty()) {
            logger.info("You are not selling any items. Use 'item create' to create a new item.");
            return;
        }

        int currentYear = vintageTimeManager.getCurrentYear();

        for (String itemId : itemsBeingSold) {
            Item item = itemManager.getItem(itemId);

            ParcelCarrier carrier = parcelCarrierManager.getCarrierById(item.getParcelCarrierUuid());

            String message = VintageConstants.DISPLAY_ITEM_FORMAT.replace("<id>", item.getAlphanumericId())
                                                                 .replace("<itemType>", item.getItemType().getDisplayName())
                                                                 .replace("<description>", item.getDescription())
                                                                 .replace("<brand>", item.getBrand())
                                                                 .replace("<finalPrice>", StringUtils.formatBigDecimal(item.getFinalPrice(currentYear)))
                                                                 .replace("<parcelCarrier>", carrier.getName());

            logger.info(message);
        }
    }
}
