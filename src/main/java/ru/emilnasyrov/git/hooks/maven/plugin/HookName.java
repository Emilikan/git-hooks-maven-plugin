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
    UPDATE("update"),
    POST_COMMIT("post-commit"),
    POST_CHECKOUT("post-checkout"),
    PRE_RECEIVE("pre-receive"),
    PROC_RECEIVE("proc-receive"),
    POST_RECEIVE("post-receive"),
    REFERENCE_TRANSACTION("reference-transaction"),
    PUSH_TO_CHECKOUT("push-to-checkout"), //
    PRE_AUTO_GC("pre-auto-gc"),
    POST_REWRITE("post-rewrite"),
    SENDEMAIL_VALIDATE("sendemail-validate"),
    FSMONITOR_WATCHMAN("fsmonitor-watchman"),
    P4_CHANGELIST("p4-changelist"),
    P4_PREPARE_CHANGELIST("p4-prepare-changelist"),
    P4_POST_CHANGELIST("p4-post-changelist"),
    P4_PRE_SUBMIT("p4-pre-submit"),
    POST_INDEX_CHANGE("post-index-change"),
    POST_APPLY_PATCH("post-applypatch"),
    POST_MERGE("post-merge"),
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
