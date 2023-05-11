package com.marketplace.vintage.scripting;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.scripting.exception.ScriptParseException;
import com.marketplace.vintage.utils.VintageDate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {

    private final File file;

    public ScriptLoader() {
        this.file = new File(VintageConstants.SCRIPTS_FILE_PATH);
    }

    public Script parseScript(String script) {
        String[] split = script.split(",", 2);
        if (split.length != 2) {
            throw new ScriptParseException("Script must have two parts: date and command");
        }

        String date = split[0];
        String command = split[1];

        VintageDate vintageDate = VintageDate.fromString(date);
        return new Script(vintageDate, command);
    }

    public List<Script> loadScripts() {
        if (!this.file.exists()) {
            return List.of();
        }

        List<Script> scripts = new ArrayList<>();

        try {
            List<String> strings = Files.readAllLines(this.file.toPath());
            for (String string : strings) {
                Script script = parseScript(string);
                scripts.add(script);
            }
        } catch (IOException e) {
            throw new ScriptParseException("Couldn't read scripts file: " + e.getMessage());
        }

        return scripts;
    }
}
