package com.marketplace.vintage.scripting;

import com.marketplace.vintage.utils.VintageDate;

public class Script {

    private final VintageDate runDate;
    private final String scriptText;

    public Script(VintageDate runDate, String scriptText) {
        this.runDate = runDate;
        this.scriptText = scriptText;
    }

    public VintageDate getRunDate() {
        return runDate;
    }

    public String getScriptText() {
        return scriptText;
    }
}
