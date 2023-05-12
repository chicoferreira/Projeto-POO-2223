package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;

import java.util.List;

public class AdminOrderListSellerCommand extends BaseCommand {
    private final VintageController vintageController;

    public AdminOrderListSellerCommand(VintageController vintageController) {
        super("listseller", "order listseller <seller>", 1, "Lists all orders containing the given seller");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String sellerName = args[0];

        if (!vintageController.existsUserWithUsername(sellerName)) {
            logger.warn("User " + sellerName + " does not exist.");
            return;
        }

        User seller = vintageController.getUserByUsername(sellerName);

        List<String> completedSellsOrderIdsList = seller.getCompletedSellsOrderIdsList();

        if (completedSellsOrderIdsList.isEmpty()) {
            logger.info("The seller " + seller.getName() + " still hasn't sell anything.");
            return;
        }

        logger.info("Orders containing seller " + seller.getName() + ":");
        for (String orderId : completedSellsOrderIdsList) {
            logger.info(" - " + orderId);
        }
    }
}
