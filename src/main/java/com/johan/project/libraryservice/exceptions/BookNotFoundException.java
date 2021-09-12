package com.johan.project.libraryservice.exceptions;

public class DuplicateBookException extends RuntimeException {
    public DuplicateBookException(final String message) {
        super(message);
    }
}