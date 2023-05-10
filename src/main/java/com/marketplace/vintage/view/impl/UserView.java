package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.commands.item.ItemCommand;
import com.marketplace.vintage.commands.order.OrderCommand;
import com.marketplace.vintage.commands.user.UserCommandUserView;
import com.marketplace.vintage.commands.shoppingcart.ShoppingCartCommand;
import com.marketplace.vintage.commands.time.CurrentTimeCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.input.questionnaire.Questionnaire;
import com.marketplace.vintage.input.questionnaire.QuestionnaireAnswers;
import com.marketplace.vintage.input.questionnaire.QuestionnaireBuilder;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.utils.EmailUtils;
import com.marketplace.vintage.view.BaseView;

import org.jetbrains.annotations.NotNull;

public class UserView extends BaseView {

    private final UserManager userManager;
    private final Logger baseLogger;
    private final Questionnaire createUserQuestionnaire;
    private User currentLoggedInUser;

    public UserView(Logger logger,
                    InputPrompter inputPrompter,
                    UserManager userManager,
                    ParcelCarrierManager parcelCarrierManager,
                    VintageController vintageController,
                    ItemManager itemManager,
                    OrderManager orderManager,
                    VintageTimeManager vintageTimeManager) {
        super(PrefixLogger.of("USER", logger), inputPrompter, "user view");
        this.baseLogger = logger;
        this.userManager = userManager;

        createUserQuestionnaire = QuestionnaireBuilder.newBuilder()
                .withQuestion("username", "Enter your username:", username -> {
                    userManager.checkUsername(username);
                    return username;
                })
                .withQuestion("name", "Enter your name:", InputMapper.STRING)
                .withQuestion("address", "Enter your address:", InputMapper.STRING)
                .withQuestion("taxNumber", "Enter your tax number:", InputMapper.STRING)
                .build();


        this.getCommandManager().registerCommand(new UserCommandUserView(this));
        this.getCommandManager().registerCommand(new ItemCommand(this, parcelCarrierManager, vintageController, itemManager, vintageTimeManager));
        this.getCommandManager().registerCommand(new OrderCommand(this, orderManager));
        this.getCommandManager().registerCommand(new ShoppingCartCommand(itemManager, vintageController, this, vintageTimeManager));
        this.getCommandManager().registerCommand(new CurrentTimeCommand(vintageController, "time"));
    }

    @Override
    public void run() {
        User user = askForLogin();
        if (user == null) { // User cancelled login
            return;
        }

        setCurrentLoggedInUser(user);
        setLogger(PrefixLogger.of(user.getName(), baseLogger));
        getLogger().info("Logged in as " + user.getName() + " (" + user.getEmail() + ")");

        super.run();
    }

    public User askForLogin() {
        String email = getInputPrompter().askForInput(getLogger(), "Enter the email to login (or 'cancel'):");

        if (email.equalsIgnoreCase("cancel")) {
            return null;
        }

        // Ensure email is valid
        if (!EmailUtils.isValidEmail(email)) {
            getLogger().info("Invalid email pattern.");
            return askForLogin();
        }

        if (!userManager.existsUserWithEmail(email)) {
            getLogger().info("User with email " + email + " does not exist.");
            return askForRegistration(email);
        }

        return userManager.getUserByEmail(email);
    }

    public User askForRegistration(String email) {
        boolean register = getInputPrompter().askForInput(getLogger(), "Do you want to register that email? (y/n)", InputMapper.BOOLEAN);
        if (!register) {
            getLogger().info("Cancelled registration.");
            return askForLogin();
        }

        QuestionnaireAnswers answers = createUserQuestionnaire.ask(getInputPrompter(), getLogger());

        getLogger().info("Creating user with email " + email + "...");

        String username = answers.getAnswer("username", String.class);
        String name = answers.getAnswer("name", String.class);
        String address = answers.getAnswer("address", String.class);
        String taxNumber = answers.getAnswer("taxNumber", String.class);

        return userManager.createUser(username, email, name, address, taxNumber);
    }

    private void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }

    @NotNull
    public User getCurrentLoggedInUser() {
        if (this.currentLoggedInUser == null) {
            throw new IllegalStateException("User not logged in");
        }
        return this.currentLoggedInUser;
    }
}
