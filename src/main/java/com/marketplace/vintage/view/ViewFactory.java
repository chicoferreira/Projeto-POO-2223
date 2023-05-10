package com.marketplace.vintage.view;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.impl.AdminView;
import com.marketplace.vintage.view.impl.UserView;

public class ViewFactory {

    private final Logger logger;
    private final InputPrompter inputPrompter;
    private final UserManager userManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final ExpressionSolver expressionSolver;
    private final VintageController vintageController;
    private final ItemManager itemManager;
    private final OrderManager orderManager;
    private final VintageTimeManager vintageTimeManager;

    public ViewFactory(Logger logger,
                       InputPrompter inputPrompter,
                       UserManager userManager,
                       ParcelCarrierManager parcelCarrierManager,
                       ExpressionSolver expressionSolver,
                       VintageController vintageController,
                       ItemManager itemManager,
                       OrderManager orderManager,
                       VintageTimeManager vintageTimeManager) {
        this.logger = logger;
        this.inputPrompter = inputPrompter;
        this.userManager = userManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
        this.vintageController = vintageController;
        this.itemManager = itemManager;
        this.orderManager = orderManager;
        this.vintageTimeManager = vintageTimeManager;
    }

    public View createView(ViewType viewType) {
        return switch (viewType) {
            case USER -> new UserView(logger, inputPrompter, userManager, parcelCarrierManager, vintageController, itemManager, orderManager, vintageTimeManager);
            case ADMIN -> new AdminView(logger, inputPrompter, vintageController, userManager, parcelCarrierManager, expressionSolver);
        };
    }

}
