package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrierController;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.Exp4jExpressionSolver;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.input.impl.StdinInputPrompter;
import com.marketplace.vintage.item.ItemController;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.JavaLogger;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.OrderController;
import com.marketplace.vintage.order.OrderFactory;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.persistent.PersistentManager;
import com.marketplace.vintage.scripting.ScriptController;
import com.marketplace.vintage.scripting.exception.ScriptException;
import com.marketplace.vintage.time.TimeManager;
import com.marketplace.vintage.user.UserController;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;
import com.marketplace.vintage.view.ViewType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class VintageApplication {

    private final Logger logger;
    private final StdinInputPrompter inputPrompter;
    private ViewFactory viewFactory;
    private PersistentManager persistentManager;
    private UserManager userManager;
    private ParcelCarrierManager parcelCarrierManager;
    private ItemManager itemManager;
    private OrderManager orderManager;
    private TimeManager timeManager;

    public VintageApplication() {
        this.logger = new JavaLogger();
        this.inputPrompter = new StdinInputPrompter();
    }

    public void init() {
        ExpressionSolver expressionSolver = new Exp4jExpressionSolver();
        ItemFactory itemFactory = new ItemFactory();

        this.persistentManager = new PersistentManager(getPersistentFile());
        loadSavedProgramState(this.persistentManager);

        ParcelCarrierController parcelCarrierController = new ParcelCarrierController(parcelCarrierManager);

        OrderFactory orderFactory = new OrderFactory();
        OrderController orderController = new OrderController(orderManager, orderFactory);

        ScriptController scriptController = new ScriptController();
        UserController userController = new UserController(userManager);

        ItemController itemController = new ItemController(itemManager, itemFactory);

        Vintage vintage = new Vintage(itemController, orderController, timeManager, parcelCarrierController, expressionSolver, userController, scriptController);

        this.viewFactory = new ViewFactory(logger, inputPrompter, vintage);

        try {
            scriptController.initialize(logger, viewFactory, vintage);
            this.logger.info("Loaded " + scriptController.loadScripts() + " scripts.");
        } catch (ScriptException exception) {
            this.logger.warn("Failed to load scripts: " + exception.getMessage());
        }
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
                    shutdown();
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

    public void shutdown() {
        saveProgramState(persistentManager);
        this.inputPrompter.close();
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
        persistentManager.addReferenceToSave("timeManager", this.timeManager);
        persistentManager.save();
    }

    public void loadSavedProgramState(PersistentManager persistentManager) {
        this.logger.info("Loading saved program state...");
        Map<String, Object> loadedData = loadSafely(persistentManager);

        this.userManager = (UserManager) loadedData.getOrDefault("userManager", new UserManager());
        this.parcelCarrierManager = (ParcelCarrierManager) loadedData.getOrDefault("parcelCarrierManager", new ParcelCarrierManager());
        this.itemManager = (ItemManager) loadedData.getOrDefault("itemManager", new ItemManager());
        this.orderManager = (OrderManager) loadedData.getOrDefault("orderManager", new OrderManager());
        this.timeManager = (TimeManager) loadedData.getOrDefault("timeManager", new TimeManager(VintageConstants.VINTAGE_START_DATE));
    }

    private Map<String, Object> loadSafely(PersistentManager persistentManager) {
        try {
            return persistentManager.loadPersistentData();
        } catch (Exception e) {
            this.logger.warn("Could not load " + getPersistentFile().getName() + ". Corrupted data?");
            return new HashMap<>();
        }
    }

    public File getPersistentFile() {
        return new File(VintageConstants.PERSISTENT_PROGRAM_STATE_SAVE_PATH);
    }
}
