package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.input.questionnaire.Questionnaire;
import com.marketplace.vintage.input.questionnaire.QuestionnaireBuilder;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.BaseView;
import com.marketplace.vintage.utils.EmailUtils;

public class UserView extends BaseView {

    private final UserManager userManager;

    public UserView(Logger logger, InputPrompter inputPrompter, UserManager userManager) {
        super(logger, inputPrompter);
        this.userManager = userManager;
    }

    @Override
    public void run() {
        User user = askForLogin();
        if (user == null) { // User cancelled login
            return;
        }

        getLogger().info("Logged in as " + user.getName() + " (" + user.getEmail() + ")");
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

        Questionnaire questionnaire = QuestionnaireBuilder.newBuilder()
                                                          .withQuestion("name", "Enter your name:", InputMapper.STRING)
                                                          .withQuestion("address", "Enter your address:", InputMapper.STRING)
                                                          .withQuestion("taxNumber", "Enter your tax number:", InputMapper.STRING)
                                                          .withInputPrompter(getInputPrompter())
                                                          .withLogger(getLogger())
                                                          .build();

        questionnaire.ask();

        getLogger().info("Creating user with email " + email + "...");

        String name = questionnaire.getAnswer("name", String.class);
        String address = questionnaire.getAnswer("address", String.class);
        String taxNumber = questionnaire.getAnswer("taxNumber", String.class);

        return userManager.createUser(email, name, address, taxNumber);
    }
}
