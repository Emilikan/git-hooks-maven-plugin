package ru.emilnasyrov.git.hooks.maven.plugin;

import java.util.Arrays;

public enum HookName {
    PRE_COMMIT("pre-commit"),
    APPLY_PATCH_MSG("applypatch-msg"),
    COMMIT_MSG("commit-msg"),
    POST_UPDATE("post-update"),
    PRE_APPLY_PATCH("pre-applypatch"),
    PRE_MERGE_COMMIT("pre-merge-commit"),
    PRE_PUSH("pre-push"),
    PRE_REBASE("pre-rebase"),
    PREPARE_COMMIT_MSG("prepare-commit-msg"),
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
