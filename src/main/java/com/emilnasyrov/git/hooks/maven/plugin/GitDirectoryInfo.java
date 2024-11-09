package com.emilnasyrov.git.hooks.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitDirectoryInfo {
    private final Path baseGitDir;
    private final Path gitHooksDir;

    private GitDirectoryInfo(Path baseGitDir, Path gitHooksDir) {
        this.baseGitDir = baseGitDir;
        this.gitHooksDir = gitHooksDir;
    }

    public static GitDirectoryInfo build(MavenProject project) throws MojoExecutionException {
        final File baseDir = project.getBasedir();
        final Path baseGitDir = resolveBaseGitDir(baseDir.getAbsolutePath());
        final Path gitHooksDir = resolveGitHooksDir(baseGitDir);

        return new GitDirectoryInfo(baseGitDir, gitHooksDir);
    }

    public Path getBaseGitDir() {
        return baseGitDir;
    }

    public Path getGitHooksDir() {
        return gitHooksDir;
    }

    private static Path resolveBaseGitDir(String baseDir) throws MojoExecutionException {
        Path gitPath = Paths.get(baseDir, ".git");

        if (!Files.exists(gitPath) || !Files.isDirectory(gitPath)) {
            throw new MojoExecutionException(".git directory not found in the project directory: " + baseDir);
        }

        return gitPath;
    }

    private static Path resolveGitHooksDir(Path baseGitDir) throws MojoExecutionException {
        Path hooksPath = baseGitDir.resolve("hooks");
        if (!Files.exists(hooksPath)) {
            try {
                Files.createDirectories(hooksPath);
            } catch (IOException e) {
                throw new MojoExecutionException("Failed to create 'hooks' directory at: " + hooksPath, e);
            }
        }

        return hooksPath;
    }
}
