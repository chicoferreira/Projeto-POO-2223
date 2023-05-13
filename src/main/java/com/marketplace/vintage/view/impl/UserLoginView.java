package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.commands.user.UserRegisterCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.EmailUtils;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;

public class UserLoginView implements View {

    private final Logger logger;
    private final InputPrompter inputPrompter;
    private final ViewFactory viewFactory;
    private final Vintage vintage;
    private final UserRegisterCommand userRegisterCommand;

    public UserLoginView(Logger logger,
                         InputPrompter inputPrompter,
                         ViewFactory viewFactory,
                         Vintage vintage) {
        this.logger = PrefixLogger.of("USER", logger);
        this.inputPrompter = inputPrompter;
        this.viewFactory = viewFactory;
        this.vintage = vintage;
        this.userRegisterCommand = new UserRegisterCommand(vintage);
    }

    @Override
    public void run() {
        User user = askForLogin();
        if (user == null) { // User cancelled login
            return;
        }

        UserView userView = this.viewFactory.createUserView(user);
        userView.run();
    }

    public User askForLogin() {
        String email = this.inputPrompter.askForInput(this.logger, "Enter the email to login (or 'cancel'):");

        if (email.equalsIgnoreCase("cancel")) {
            return null;
        }

        // Ensure email is valid
        if (!EmailUtils.isValidEmail(email)) {
            this.logger.info("Invalid email pattern.");
            return askForLogin();
        }

        if (!vintage.existsUserWithEmail(email)) {
            this.logger.info("User with email " + email + " does not exist.");
            return askForRegistration(email);
        }

        return vintage.getUserByEmail(email);
    }

    public User askForRegistration(String email) {
        boolean register = this.inputPrompter.askForInput(this.logger, "Do you want to register that email? (y/n)", InputMapper.BOOLEAN);
        if (!register) {
            this.logger.info("Cancelled registration.");
            return askForLogin();
        }

        this.logger.info("Creating user with email " + email + "...");
        return userRegisterCommand.askUserInfoAndRegister(email, this.inputPrompter, this.logger);
    }
}
