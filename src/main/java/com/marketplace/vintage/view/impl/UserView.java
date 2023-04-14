package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.terminal.Terminal;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.view.BaseView;
import com.marketplace.vintage.utils.EmailUtils;

public class UserView extends BaseView {

    private final UserManager userManager;

    public UserView(Logger logger, Terminal terminal, UserManager userManager) {
        super(logger, terminal);
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
        String email = getTerminal().askForInput(getLogger(), "Enter the email to login (or 'cancel'): ");

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
        boolean register = getTerminal().askForConfirmation(getLogger(), "Do you want to register that email? (y/n)");
        if (!register) {
            getLogger().info("Cancelled registration.");
            return askForLogin();
        }

        String name = getTerminal().askForInput(getLogger(), "Enter your name: ");
        String address = getTerminal().askForInput(getLogger(), "Enter your address: ");
        String taxNumber = getTerminal().askForInput(getLogger(), "Enter your tax number: ");

        getLogger().info("Creating user with email " + email + "...");

        return userManager.createUser(email, name, address, taxNumber);
    }
}
