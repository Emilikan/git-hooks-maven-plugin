package com.emilnasyrov.git.hooks.maven.plugin;

import java.util.Collections;
import java.util.List;

public final class GitHookInstallerParameters {
    private List<HookConfiguration> hooks;

    private boolean skip;

    public List<HookConfiguration> getHooks() {
        return hooks;
    }

    public boolean getSkip() {
        return skip;
    }

    public void setHooks(List<HookConfiguration> hooks) {
        this.hooks = hooks;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public GitHookInstallerParameters() {}

    public GitHookInstallerParameters(HookConfiguration hookConfiguration, boolean skip) {
        this.hooks = Collections.singletonList(hookConfiguration);
        this.skip = skip;
    }
}
