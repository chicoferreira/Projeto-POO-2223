package com.marketplace.vintage.command;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommandManagerTest {

    @Test
    void executeCommand() {
        CommandManager commandRepository = new CommandManager();
        commandRepository.registerCommand(new BaseCommand("test", "", 0, "Test command") {
            @Override
            public void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
                Assertions.assertEquals(2, args.length);
                Assertions.assertEquals("arg1", args[0]);
                Assertions.assertEquals("arg2", args[1]);
            }
        });

        commandRepository.executeRawCommand(null, null, "test arg1 arg2");
        commandRepository.executeRawCommand(null, null, "test arg1 arg2  ");
        commandRepository.executeRawCommand(null, null, "test   arg1  arg2  ");
    }

    @Test
    void executeCommandWithNotEnoughArgs() {
        Logger logger = Mockito.mock(Logger.class);

        CommandManager commandRepository = new CommandManager();
        commandRepository.registerCommand(new BaseCommand("test", "test <arg1> <arg2>", 2, "Test command") {
            @Override
            public void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
                Assertions.fail("Should not be called");
            }
        });

        commandRepository.executeRawCommand(logger, null, "test");
        commandRepository.executeRawCommand(logger, null, "test arg1");
        Mockito.verify(logger, Mockito.times(2)).warn("Usage: test <arg1> <arg2>");
    }
}