package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.JavaLogger;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;
import com.marketplace.vintage.view.ViewType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VintageApplication {

    // Default application values
    public static final String DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING = "basePrice * 0.1 * (1 + tax) * 0.9";
    public static final List<String> EXPEDITION_PRICE_EXPRESSION_VARIABLES = new ArrayList<>(Arrays.asList("basePrice", "tax"));

    private final Logger logger;
    private final ViewFactory viewFactory;
    private final InputPrompter inputPrompter;

    public VintageApplication() {
        this.logger = new JavaLogger();
        UserManager userManager = new UserManager();
        ParcelCarrierManager parcelCarrierManager = new ParcelCarrierManager();
        this.inputPrompter = new InputPrompter();
        this.viewFactory = new ViewFactory(logger, inputPrompter, userManager, parcelCarrierManager);
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
                String viewTypeName = inputPrompter.getInput();
                if (viewTypeName.equals("exit")) {
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
