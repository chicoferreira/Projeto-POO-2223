package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.Exp4jExpressionSolver;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.JavaLogger;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;
import com.marketplace.vintage.view.ViewType;

import java.util.stream.Collectors;

public class VintageApplication {

    private final Logger logger;
    private final ViewFactory viewFactory;
    private final InputPrompter inputPrompter;

    public VintageApplication() {
        this.logger = new JavaLogger();
        UserManager userManager = new UserManager();
        ParcelCarrierManager parcelCarrierManager = new ParcelCarrierManager();
        this.inputPrompter = new InputPrompter();
        ExpressionSolver expressionSolver = new Exp4jExpressionSolver();
        ItemManager itemManager = new ItemManager();
        ItemFactory itemFactory = new ItemFactory();
        OrderManager orderManager = new OrderManager();

        VintageController vintageController = new VintageController(itemManager, itemFactory);

        VintageTimeManager vintageTimeManager = new VintageTimeManager();

        this.viewFactory = new ViewFactory(logger, inputPrompter, userManager, parcelCarrierManager, expressionSolver, vintageController, itemManager, orderManager, vintageTimeManager);
    }

    public void start() {
        logger.info("Welcome to the Vintage Marketplace!");
        logger.info();

        String availableViews = buildAllViewsString() + " or Exit (exit)";

        while (true) {
            logger.info("Choose which view you want to use.");
            logger.info("Available views: " + availableViews);

            View view = null;
            while (view == null) {
                String viewTypeName = inputPrompter.askForInput(logger, ">").trim();
                if (viewTypeName.equalsIgnoreCase("exit")) {
                    return;
                }
                ViewType viewType = ViewType.fromCommandName(viewTypeName);

                if (viewType == null) {
                    logger.info("Invalid view name.");
                    logger.info("Please type one of the following: " + availableViews);
                    continue;
                }
                view = viewFactory.createView(viewType);
            }

            view.run();
        }
    }

    public String buildAllViewsString() {
        return ViewType.getAllViewTypes().stream()
                       .map(viewType -> viewType.getDisplayName() + " (" + viewType.getCommandName() + ")")
                       .collect(Collectors.joining(", "));
    }

}
