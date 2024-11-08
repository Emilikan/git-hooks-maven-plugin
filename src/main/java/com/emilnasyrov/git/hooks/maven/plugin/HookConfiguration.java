package com.emilnasyrov.git.hooks.maven.plugin;

import com.emilnasyrov.git.hooks.maven.plugin.exception.ValidateMojoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HookConfiguration {
    private HookName name;
    private List<ScriptName> scripts = new ArrayList<>();

    public HookName getName() {
        return name;
    }

    public void setName(String name) {
        this.name = HookName.valueOfHookName(name);
    }

    public List<ScriptName> getScripts() {
        return new ArrayList<>(scripts);
    }

    public void setScripts(List<String> scripts) {
        this.scripts = scripts.stream()
                .map(ScriptName::valueOfFileName)
                .collect(Collectors.toList());
    }

    public void validate() throws ValidateMojoException {
        if (name == null) {
            throw new ValidateMojoException("Invalid parameters: write hook name");
        }
        if (scripts == null || scripts.isEmpty() || scripts.stream().anyMatch(Objects::isNull)) {
            throw new ValidateMojoException("Invalid parameters: write scripts name");
        }
    }

    @Override
    public String toString() {
        return "HookConfiguration(" + "name=" + name + ", " + "scripts=" + scripts + ")";
    }
}
