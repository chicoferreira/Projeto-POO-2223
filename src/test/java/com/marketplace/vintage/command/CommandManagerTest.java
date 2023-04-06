package com.marketplace.vintage.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandManagerTest {

    @Test
    void executeCommand() {
        CommandManager commandManager = new CommandManager();
        commandManager.registerCommand(new BaseCommand("test", "") {
            @Override
            public void execute(String[] args) {
                Assertions.assertEquals(2, args.length);
                Assertions.assertEquals("arg1", args[0]);
                Assertions.assertEquals("arg2", args[1]);
            }
        });

        commandManager.executeCommand("test arg1 arg2");
        commandManager.executeCommand("test arg1 arg2  ");
        commandManager.executeCommand("test   arg1  arg2  ");
    }
}