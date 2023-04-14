package com.marketplace.vintage.terminal;

import com.marketplace.vintage.logging.Logger;

import java.util.Scanner;

public class Terminal {

    private final Scanner scanner;

    public Terminal() {
        this.scanner = new Scanner(System.in);
    }

    public String askForInput(Logger logger, String message) {
        logger.print(message + " ");
        return askForInput();
    }

    public String askForInput() {
        return scanner.nextLine();
    }

    public boolean askForConfirmation(Logger logger, String message) {
        String input = askForInput(logger, message);
        return input.equalsIgnoreCase("y")
               || input.equalsIgnoreCase("yes")
               || input.equalsIgnoreCase("true");
    }

}
