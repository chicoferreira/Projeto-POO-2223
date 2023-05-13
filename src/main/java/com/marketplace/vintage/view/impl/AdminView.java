package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.Vintage;
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
    public AdminView(Logger logger, InputPrompter inputPrompter, Vintage vintage) {
        super(logger, inputPrompter, "admin view");

        getCommandManager().registerCommand(new UserCommandAdminView(vintage));
        getCommandManager().registerCommand(new ParcelCarrierCommand(vintage,
                VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING,
                VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES));
        getCommandManager().registerCommand(new AdminTimeCommand(vintage));
        getCommandManager().registerCommand(new StatsCommand(vintage));
        getCommandManager().registerCommand(new AdminOrderCommand(vintage));
        getCommandManager().registerCommand(new AdminItemCommand(vintage));
    }

    @Override
    public void run() {
        getLogger().info("Admin Control View - Type 'help' to see available commands");
        super.run();
    }
}
