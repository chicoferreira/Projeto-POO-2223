package com.marketplace.vintage.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandRepositoryTest {

    @Test
    void executeCommand() {
        CommandRepository commandRepository = new CommandRepository();
        commandRepository.registerCommand(new BaseCommand("test", "") {
            @Override
            public void execute(String[] args) {
                Assertions.assertEquals(2, args.length);
                Assertions.assertEquals("arg1", args[0]);
                Assertions.assertEquals("arg2", args[1]);
            }
        });

        commandRepository.executeCommand("test arg1 arg2");
        commandRepository.executeCommand("test arg1 arg2  ");
        commandRepository.executeCommand("test   arg1  arg2  ");
    }
}