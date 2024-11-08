package com.emilnasyrov.git.hooks.maven.plugin.exception;

import org.apache.maven.plugin.MojoExecutionException;

public class ValidateMojoException extends MojoExecutionException {
    public ValidateMojoException(String message) {
        super(message);
    }
}
