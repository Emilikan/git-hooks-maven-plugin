package com.emilnasyrov.git.hooks.maven.plugin;

import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.io.CleanupMode.ALWAYS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractMojoTest {
    private static final String BASE_DIR = "test-project";
    private static final String GIT_HOOKS = ".git/hooks";

    @TempDir(cleanup = ALWAYS)
    public File tempDir;

    protected GitHookInstallerMojo configureInstallerMojo(GitHookInstallerParameters parameters) throws NoSuchFieldException, IllegalAccessException, IOException {
        createGitDir();

        GitHookInstallerMojo mojo = new GitHookInstallerMojo();
        MavenProject mockProject = mock(MavenProject.class);
        when(mockProject.getBasedir()).thenReturn(new File(tempDir.getAbsolutePath() + "/" + BASE_DIR));
        setField(mojo, "project", mockProject);

        setField(mojo, "hooks", parameters.getHooks());

        setField(mojo, "skip", parameters.getSkip());

        return mojo;
    }

    protected void assertExistGitHookFile(final String fileName) {
        Path path = Paths.get(tempDir.getAbsolutePath(), "/", BASE_DIR, "/", GIT_HOOKS, "/", fileName);
        assertTrue(Files.exists(path));
    }

    protected void assertNotExistGitHookFile(final String fileName) {
        Path path = Paths.get(tempDir.getAbsolutePath(), "/", BASE_DIR, "/", GIT_HOOKS, "/", fileName);
        assertFalse(Files.exists(path));
    }

    private void createGitDir() throws IOException {
        Files.createDirectories(Paths.get(tempDir.getAbsolutePath(), "/", BASE_DIR, "/",  GIT_HOOKS));
    }

    private void setField(final Object target, final String fieldName, final Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
