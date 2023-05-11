package com.marketplace.vintage.command;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

public interface Command {

    String getName();

    String getUsage();

    int getMinArgs();

    String getDescription();

    void execute(Logger logger, InputPrompter inputPrompter, String[] args);

}
