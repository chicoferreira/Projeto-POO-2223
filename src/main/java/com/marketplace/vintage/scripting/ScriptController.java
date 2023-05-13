package com.marketplace.vintage.scripting;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.scripting.runner.CommandScriptRunner;
import com.marketplace.vintage.scripting.runner.ScriptRunner;
import com.marketplace.vintage.utils.VintageDate;
import com.marketplace.vintage.view.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class ScriptController {

    private final List<Script> loadedScripts;
    private final ScriptLoader scriptLoader;
    private ScriptRunner scriptRunner;

    public ScriptController() {
        this.loadedScripts = new ArrayList<>();
        this.scriptLoader = new ScriptLoader();
    }

    public void initialize(Logger logger, ViewFactory viewFactory, Vintage vintage) {
        this.scriptRunner = new CommandScriptRunner(logger, viewFactory, vintage);
    }

    public int loadScripts() {
        List<Script> scripts = scriptLoader.loadScripts();
        loadedScripts.clear();
        loadedScripts.addAll(scripts);
        return scripts.size();
    }

    public void notifyTimeChange(Logger logger, VintageDate previousDate, VintageDate currentDate) {
        if (this.scriptRunner == null) {
            throw new IllegalStateException("ScriptController not initialized. Call ScriptController.initialize() first.");
        }

        int scriptsRun = 0;
        for (Script script : loadedScripts) {
            VintageDate runDate = script.getRunDate();
            if (runDate.isAfter(previousDate) && runDate.isBeforeOrSame(currentDate)) {
                try {
                    scriptRunner.runScript(script);
                    scriptsRun++;
                } catch (Exception e) {
                    logger.warn("Failed to run script '" + script.getScriptText() + "': " + e.getMessage());
                }
            }
        }
        if (scriptsRun > 0) {
            logger.info("Ran sucessfully " + scriptsRun + " scripts.");
        }
    }
}
