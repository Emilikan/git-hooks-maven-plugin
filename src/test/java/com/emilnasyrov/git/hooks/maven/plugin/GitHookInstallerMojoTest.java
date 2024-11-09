package com.emilnasyrov.git.hooks.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class GitHookInstallerMojoTest extends AbstractMojoTest {

    @Test
    public void testDefaultSuccessfulRun() throws MojoExecutionException, IOException, NoSuchFieldException, IllegalAccessException {
        HookConfiguration hookConfig = new HookConfiguration();
        hookConfig.setName("pre-commit");
        hookConfig.setScripts(Arrays.asList("ktlint-check", "ktlint-format"));
        GitHookInstallerMojo mojo = configureInstallerMojo(new GitHookInstallerParameters(hookConfig, false));

        assertNotExistGitHookFile("pre-commit");
        assertNotExistGitHookFile("ktlint-check");
        assertNotExistGitHookFile("ktlint-format");
        assertNotExistGitHookFile("pre-commit-config");

        mojo.execute();

        assertExistGitHookFile("pre-commit");
        assertExistGitHookFile("ktlint-check");
        assertExistGitHookFile("ktlint-format");
        assertExistGitHookFile("pre-commit-config");
    }

    @Test
    public void shouldSuccessSkip() throws IOException, NoSuchFieldException, IllegalAccessException, MojoExecutionException {
        HookConfiguration hookConfig = new HookConfiguration();
        hookConfig.setName("pre-commit");
        hookConfig.setScripts(Arrays.asList("ktlint-check", "ktlint-format"));
        GitHookInstallerMojo mojo = configureInstallerMojo(new GitHookInstallerParameters(hookConfig, true));

        assertNotExistGitHookFile("pre-commit");
        assertNotExistGitHookFile("ktlint-check");
        assertNotExistGitHookFile("ktlint-format");
        assertNotExistGitHookFile("pre-commit-config");

        mojo.execute();

        assertNotExistGitHookFile("pre-commit");
        assertNotExistGitHookFile("ktlint-check");
        assertNotExistGitHookFile("ktlint-format");
        assertNotExistGitHookFile("pre-commit-config");
    }

    @Test
    public void shouldDeleteUnusedConfig() throws IOException, NoSuchFieldException, IllegalAccessException, MojoExecutionException {
        HookConfiguration hookConfig1 = new HookConfiguration();
        hookConfig1.setName("pre-commit");
        hookConfig1.setScripts(Arrays.asList("ktlint-check", "ktlint-format"));
        HookConfiguration hookConfig2 = new HookConfiguration();
        hookConfig2.setName("post-commit");
        hookConfig2.setScripts(Arrays.asList("ktlint-check", "ktlint-format"));
        GitHookInstallerParameters parameters1 = new GitHookInstallerParameters();
        parameters1.setHooks(Arrays.asList(hookConfig1, hookConfig2));
        parameters1.setSkip(false);

        GitHookInstallerMojo mojo1 = configureInstallerMojo(parameters1);

        assertNotExistGitHookFile("pre-commit");
        assertNotExistGitHookFile("ktlint-check");
        assertNotExistGitHookFile("ktlint-format");
        assertNotExistGitHookFile("pre-commit-config");

        mojo1.execute();

        assertExistGitHookFile("pre-commit");
        assertExistGitHookFile("ktlint-check");
        assertExistGitHookFile("ktlint-format");
        assertExistGitHookFile("pre-commit-config");

        assertExistGitHookFile("post-commit");
        assertExistGitHookFile("post-commit-config");

        // Mojo 2

        GitHookInstallerMojo mojo2 = configureInstallerMojo(new GitHookInstallerParameters(hookConfig1, false));

        mojo2.execute();

        assertExistGitHookFile("pre-commit");
        assertExistGitHookFile("ktlint-check");
        assertExistGitHookFile("ktlint-format");
        assertExistGitHookFile("pre-commit-config");

        assertExistGitHookFile("post-commit");
        assertNotExistGitHookFile("post-commit-config");
    }

}
