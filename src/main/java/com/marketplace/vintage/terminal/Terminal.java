package com.marketplace.vintage.terminal;

import com.marketplace.vintage.logging.Logger;

import java.util.Scanner;

public class Terminal {

    private final Scanner scanner;
    private final Logger logger;

    public Terminal(Logger logger) {
        this.logger = logger;
        this.scanner = new Scanner(System.in);
    }

    public String askForInput(String message) {
        logger.print(message);
        return askForInput();
    }

    public String askForInput() {
        return scanner.nextLine();
    }

}
