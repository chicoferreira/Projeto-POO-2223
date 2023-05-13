package com.marketplace.vintage.commands.item.stock;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.Optional;

public abstract class ItemStockChangeCommand extends BaseCommand {

    private final UserView userView;
    private final Vintage vintage;

    public ItemStockChangeCommand(UserView userView, Vintage vintage, String name, String usage, String description) {
        super(name, usage, 2, description);
        this.userView = userView;
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String itemId = args[0];

        if (!vintage.existsItem(itemId)) {
            logger.warn("Item '" + itemId + "' does not exist");
            return;
        }

        Item item = vintage.getItem(itemId);
        User user = userView.getCurrentLoggedInUser();

        if (!item.getOwnerUuid().equals(user.getId())) {
            logger.warn("You do not own item '" + itemId + "'");
            return;
        }

        String stockString = args[1];
        Optional<Integer> stockOptional = StringUtils.parseIntSafe(stockString);

        if (stockOptional.isEmpty()) {
            logger.warn("Stock '" + stockString + "' must be a number");
            return;
        }

        int stockChange = stockOptional.get();

        if (stockChange < 0) {
            logger.warn("Stock change '" + stockChange + "' must be greater than 0");
            return;
        }

        operation(logger, item, stockChange);
    }

    protected Vintage getVintage() {
        return vintage;
    }

    /**
     * Performs the operation (add or remove stock) on the item
     * @param logger The logger
     * @param item The item
     * @param stock The stock change
     */
    protected abstract void operation(Logger logger, Item item, int stock);

}
