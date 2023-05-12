package com.marketplace.vintage.input.impl;

import java.util.Scanner;

public class StdinInputPrompter extends BaseInputPrompter {

    private final Scanner scanner;

    public StdinInputPrompter() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

}
