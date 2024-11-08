package ru.emilnasyrov.git.hooks.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.emilnasyrov.git.hooks.maven.plugin.exception.ValidateMojoException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(name = "install-hooks", defaultPhase = LifecyclePhase.VALIDATE)
public final class GitHookInstallerMojo extends AbstractMojo {

    @Parameter
    private List<HookConfiguration> hooks;

    @Parameter(property = "githookinstaller.install.skip", defaultValue = "false")
    private boolean skip;

    private final Path gitDir = Paths.get(System.getProperty("user.dir"), ".git", "hooks");

    private static final String HOOK_PREFIX = "hooks";
    private static final String SCRIPT_PREFIX = "scripts";
    private static final String CONFIG_SUFFIX = "-config";

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skipping Git hook installation as configured.");
            return;
        }

        validateHookParameter();

        for (HookConfiguration hook : hooks) {
            final String hookName = hook.getName().getFileName();
            copyAndSetExecutable(hookName, HOOK_PREFIX);

            final List<String> scriptFileNames = hook.getScripts().stream()
                    .map(ScriptName::getFileName)
                    .collect(Collectors.toList());

            for (String scriptFileName : scriptFileNames) {
                copyAndSetExecutable(scriptFileName, SCRIPT_PREFIX);
            }

            writeConfigFile(hook.getName(), scriptFileNames);

            getLog().info("Git hook '" + hook.getName().getFileName() + "' installed successfully.");
        }

        deleteUnusedConfig();
    }

    private void validateHookParameter() throws ValidateMojoException {
        if (hooks == null || hooks.isEmpty()) {
            throw new ValidateMojoException("No hooks configured");
        }
        for (HookConfiguration hook : hooks) {
            hook.validate();
        }
    }

    private void copyAndSetExecutable(
        String fileName,
        String resourcePrefix
    ) throws MojoExecutionException {
        final Path gitPath = gitDir.resolve(fileName);
        final String resourcePath = "/" + resourcePrefix + "/" + fileName;

        try (InputStream sourceStream = getClass().getResourceAsStream(resourcePath)) {
            if (sourceStream == null) {
                throw new MojoExecutionException("Resource '" + resourcePath + "' not found in plugin");
            }

            Files.copy(sourceStream, gitPath, StandardCopyOption.REPLACE_EXISTING);
            new File(gitPath.toString()).setExecutable(true);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to install Git hook", e);
        }
    }

    private void writeConfigFile(
        HookName hookName,
        List<String> scriptsToRun
    ) throws MojoExecutionException {
        final Path configFile = gitDir.resolve(hookName.getFileName() + CONFIG_SUFFIX);
        try {
            Files.write(
                configFile,
                scriptsToRun,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to install config file " + configFile, e);
        }
    }

    private void deleteUnusedConfig() throws MojoExecutionException {
        final ArrayList<HookName> unusedHookNames = new ArrayList<>(Arrays.asList(HookName.values()));
        final List<HookName> usedHookNames = hooks.stream()
                .map(HookConfiguration::getName)
                .collect(Collectors.toList());

        unusedHookNames.removeAll(usedHookNames);

        for (HookName hookName : unusedHookNames) {
            final Path configFilePath = gitDir.resolve(hookName.getFileName() + CONFIG_SUFFIX);
            deleteFile(configFilePath);
        }
    }

    private void deleteFile(Path filePath) throws MojoExecutionException {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to delete unused config file " + filePath, e);
        }
    }
}
