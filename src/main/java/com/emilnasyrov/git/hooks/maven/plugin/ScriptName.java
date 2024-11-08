package com.emilnasyrov.git.hooks.maven.plugin;

import java.util.Arrays;

public enum ScriptName {
    KTLINT_CHECK("ktlint-check"),
    KTLINT_FORMAT("ktlint-format"),
    ;

    private final String fileName;

    ScriptName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static ScriptName valueOfFileName(String fileName) {
        return Arrays.stream(ScriptName.values())
                .filter((script) -> script.getFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert " + fileName + " to Enum"));
    }
}
