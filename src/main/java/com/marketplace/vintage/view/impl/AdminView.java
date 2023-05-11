package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.commands.carrier.ParcelCarrierCommand;
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
    }

    @Override
    public void run() {
        getLogger().info("Admin Control View - Type 'help' to see available commands");
        super.run();
    }
}
