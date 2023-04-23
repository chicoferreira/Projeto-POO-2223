package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.commands.carrier.ParcelCarrierCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.view.BaseView;

public class AdminView extends BaseView {
    public AdminView(Logger logger, InputPrompter inputPrompter, ParcelCarrierManager parcelCarrierManager) {
        super(logger, inputPrompter);

        getCommandManager().registerCommand(new ParcelCarrierCommand(parcelCarrierManager));
    }

    @Override
    public void run() {
        getLogger().info("Admin Control View - Type 'help' to see available commands");
        super.run();
    }
}
