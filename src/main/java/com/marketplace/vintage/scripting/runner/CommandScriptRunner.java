package com.marketplace.vintage.scripting.runner;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.CommandManager;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.input.impl.BufferedInputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.OnlyWarnLogger;
import com.marketplace.vintage.logging.PrefixLogger;
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
    private final Vintage vintage;
    private final Logger logger;

    public CommandScriptRunner(Logger logger, ViewFactory viewFactory, Vintage vintage) {
        this.logger = logger;
        this.viewFactory = viewFactory;
        this.vintage = vintage;
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
            command = inputLines.remove(0);
        }

        CommandManager commandManager = commandRunnerView.getCommandManager();
        InputPrompter inputPrompter = new BufferedInputPrompter(inputLines.iterator());

        Logger logger = OnlyWarnLogger.of(PrefixLogger.of(script.getScriptText() + " - WARN", this.logger));

        commandManager.executeRawCommand(logger, inputPrompter, command);
    }

    public View createViewFromIdentifier(String viewIdentifier) {
        viewIdentifier = viewIdentifier.trim();
        if (viewIdentifier.toLowerCase().startsWith("user:")) {
            User userByEmail = vintage.getUserByEmail(viewIdentifier.substring("user:".length()));
            return viewFactory.createUserView(userByEmail);
        }
        if (viewIdentifier.equalsIgnoreCase("admin")) {
            return viewFactory.createView(ViewType.ADMIN);
        }
        throw new ScriptRunException("Unknown view identifier: " + viewIdentifier);
    }
}
