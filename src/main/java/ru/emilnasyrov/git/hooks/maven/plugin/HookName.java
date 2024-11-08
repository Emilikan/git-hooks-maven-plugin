package ru.emilnasyrov.git.hooks.maven.plugin;

import java.util.Arrays;

public enum HookName {
    PRE_COMMIT("pre-commit"),
    ;

    private final String fileName;

    HookName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static HookName valueOfHookName(String hookName) {
        return Arrays.stream(HookName.values())
                .filter((hook) -> hook.getFileName().equals(hookName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert " + hookName + " to Enum"));
    }
}
