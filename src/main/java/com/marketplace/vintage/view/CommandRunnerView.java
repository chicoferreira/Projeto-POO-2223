package com.marketplace.vintage.view;

import com.marketplace.vintage.command.CommandManager;

/**
 * A view that can run commands
 */
public interface CommandRunnerView extends View {

    CommandManager getCommandManager();

}
