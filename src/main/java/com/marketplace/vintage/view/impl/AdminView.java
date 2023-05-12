package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.commands.carrier.ParcelCarrierCommand;
import com.marketplace.vintage.commands.item.AdminItemCommand;
import com.marketplace.vintage.commands.order.AdminOrderCommand;
import com.marketplace.vintage.commands.stats.StatsCommand;
import com.marketplace.vintage.commands.time.AdminTimeCommand;
import com.marketplace.vintage.commands.user.UserCommandAdminView;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.view.BaseView;

public class AdminView extends BaseView {
    public AdminView(Logger logger, InputPrompter inputPrompter, VintageController vintageController) {
        super(logger, inputPrompter, "admin view");

        getCommandManager().registerCommand(new UserCommandAdminView(vintageController));
        getCommandManager().registerCommand(new ParcelCarrierCommand(vintageController,
                VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING,
                VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES));
        getCommandManager().registerCommand(new AdminTimeCommand(vintageController));
        getCommandManager().registerCommand(new StatsCommand(vintageController));
        getCommandManager().registerCommand(new AdminOrderCommand(vintageController));
        getCommandManager().registerCommand(new AdminItemCommand(vintageController));
    }

    @Override
    public void run() {
        getLogger().info("Admin Control View - Type 'help' to see available commands");
        super.run();
    }
}
