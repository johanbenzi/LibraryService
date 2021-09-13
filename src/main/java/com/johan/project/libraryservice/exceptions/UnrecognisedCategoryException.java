package com.johan.project.libraryservice.exceptions;

public class UnrecognisedCategoryException extends RuntimeException {
    public UnrecognisedCategoryException(final String message) {
        super(message);
    }
}