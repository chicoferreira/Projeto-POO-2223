package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.Exp4jExpressionSolver;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.JavaLogger;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.OrderController;
import com.marketplace.vintage.order.OrderFactory;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.persistent.PersistentManager;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;
import com.marketplace.vintage.view.ViewType;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class VintageApplication {

    private final Logger logger;
    private final InputPrompter inputPrompter;
    private ViewFactory viewFactory;
    private PersistentManager persistentManager;
    private UserManager userManager;
    private ParcelCarrierManager parcelCarrierManager;
    private ItemManager itemManager;
    private OrderManager orderManager;
    private VintageTimeManager vintageTimeManager;

    public VintageApplication() {
        this.logger = new JavaLogger();
        this.inputPrompter = new InputPrompter();
    }

    public void init() {
        ExpressionSolver expressionSolver = new Exp4jExpressionSolver();
        ItemFactory itemFactory = new ItemFactory();

        this.persistentManager = new PersistentManager(getPersistentFile());
        loadSavedProgramState(this.persistentManager);

        OrderFactory orderFactory = new OrderFactory(vintageTimeManager, parcelCarrierManager, expressionSolver);
        OrderController orderController = new OrderController(orderManager);
        VintageController vintageController = new VintageController(itemManager, itemFactory, orderManager, orderController, vintageTimeManager, parcelCarrierManager, expressionSolver, orderFactory, userManager);

        this.viewFactory = new ViewFactory(logger, inputPrompter, vintageController);

        addShutdownSaveHook();
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

    public void saveProgramState(PersistentManager persistentManager) {
        this.logger.info();
        this.logger.info("Saving program state...");

        persistentManager.addReferenceToSave("userManager", this.userManager);
        persistentManager.addReferenceToSave("parcelCarrierManager", this.parcelCarrierManager);
        persistentManager.addReferenceToSave("itemManager", this.itemManager);
        persistentManager.addReferenceToSave("orderManager", this.orderManager);
        persistentManager.addReferenceToSave("timeManager", this.vintageTimeManager);
        persistentManager.save();
    }

    public void loadSavedProgramState(PersistentManager persistentManager) {
        this.logger.info("Loading saved program state...");
        Map<String, Object> loadedData = loadSafely(persistentManager);

        this.userManager = (UserManager) loadedData.getOrDefault("userManager", new UserManager());
        this.parcelCarrierManager = (ParcelCarrierManager) loadedData.getOrDefault("parcelCarrierManager", new ParcelCarrierManager());
        this.itemManager = (ItemManager) loadedData.getOrDefault("itemManager", new ItemManager());
        this.orderManager = (OrderManager) loadedData.getOrDefault("orderManager", new OrderManager());
        this.vintageTimeManager = (VintageTimeManager) loadedData.getOrDefault("timeManager", new VintageTimeManager(VintageConstants.VINTAGE_START_DATE));
    }

    private Map<String, Object> loadSafely(PersistentManager persistentManager) {
        try {
            return persistentManager.loadPersistentData();
        } catch (Exception e) {
            this.logger.warn("Could not load " + getPersistentFile().getName() + ". Corrupted data?");
            return new HashMap<>();
        }
    }

    private void addShutdownSaveHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveProgramState(persistentManager)));
    }

    public File getPersistentFile() {
        return Paths.get("vintage_persistent_data.bin").toFile();
    }
}
