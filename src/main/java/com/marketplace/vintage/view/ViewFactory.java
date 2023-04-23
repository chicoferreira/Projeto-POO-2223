package com.marketplace.vintage.view;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.impl.AdminView;
import com.marketplace.vintage.view.impl.UserView;

public class ViewFactory {

    private final Logger logger;
    private final InputPrompter inputPrompter;
    private final UserManager userManager;
    private final ParcelCarrierManager parcelCarrierManager;

    public ViewFactory(Logger logger, InputPrompter inputPrompter, UserManager userManager, ParcelCarrierManager parcelCarrierManager) {
        this.logger = logger;
        this.inputPrompter = inputPrompter;
        this.userManager = userManager;
        this.parcelCarrierManager = parcelCarrierManager;
    }

    public View createView(ViewType viewType) {
        return switch (viewType) {
            case USER -> new UserView(logger, inputPrompter, userManager);
            case ADMIN -> new AdminView(logger, inputPrompter, parcelCarrierManager);
        };
    }

}
