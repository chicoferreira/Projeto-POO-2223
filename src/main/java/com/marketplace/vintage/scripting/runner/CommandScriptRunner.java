package com.marketplace.vintage.scripting.runner;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.CommandManager;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.input.impl.BufferedInputPrompter;
import com.marketplace.vintage.logging.EmptyLogger;
import com.marketplace.vintage.scripting.Script;
import com.marketplace.vintage.scripting.exception.ScriptRunException;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.CommandRunnerView;
import com.marketplace.vintage.view.View;
import com.marketplace.vintage.view.ViewFactory;
import com.marketplace.vintage.view.ViewType;

import java.util.ArrayList;
import java.util.List;

public class CommandScriptRunner implements ScriptRunner {

    private final ViewFactory viewFactory;
    private final VintageController vintageController;

    public CommandScriptRunner(ViewFactory viewFactory, VintageController vintageController) {
        this.viewFactory = viewFactory;
        this.vintageController = vintageController;
    }

    @Override
    public void runScript(Script script) {
        String[] split = script.getScriptText().split(",", 2);
        if (split.length != 2) {
            throw new ScriptRunException("Couldn't split view and command from script: " + script.getScriptText());
        }

        String viewIdentifier = split[0];
        String command = split[1];

        View view = createViewFromIdentifier(viewIdentifier);
        if (!(view instanceof CommandRunnerView commandRunnerView)) {
            throw new ScriptRunException("View " + viewIdentifier + " cannot run commands");
        }

        List<String> inputLines = new ArrayList<>();
        if (command.contains("<")) {
            for (String inputLine : command.split("<")) {
                inputLines.add(inputLine.trim());
            }
        }

        CommandManager commandManager = commandRunnerView.getCommandManager();
        InputPrompter inputPrompter = new BufferedInputPrompter(inputLines.iterator());

        commandManager.executeRawCommand(EmptyLogger.INSTANCE, inputPrompter, command);
    }

    public View createViewFromIdentifier(String viewIdentifier) {
        if (viewIdentifier.toLowerCase().startsWith("user:")) {
            User userByEmail = vintageController.getUserByEmail(viewIdentifier.substring("user:".length()));
            return viewFactory.createUserView(userByEmail);
        }
        if (viewIdentifier.equalsIgnoreCase("admin")) {
            return viewFactory.createView(ViewType.ADMIN);
        }
        throw new ScriptRunException("Unknown view identifier: " + viewIdentifier);
    }
}
