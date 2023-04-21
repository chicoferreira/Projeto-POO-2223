package com.marketplace.vintage.command;

import com.marketplace.vintage.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandManagerTest {

    @Test
    void executeCommand() {
        CommandManager commandRepository = new CommandManager();
        commandRepository.registerCommand(new BaseCommand("test", "", 0, "Test command") {
            @Override
            public void execute(Logger logger, String[] args) {
                Assertions.assertEquals(2, args.length);
                Assertions.assertEquals("arg1", args[0]);
                Assertions.assertEquals("arg2", args[1]);
            }
        });

        commandRepository.executeRawCommand(null, "test arg1 arg2");
        commandRepository.executeRawCommand(null, "test arg1 arg2  ");
        commandRepository.executeRawCommand(null, "test   arg1  arg2  ");
    }
}