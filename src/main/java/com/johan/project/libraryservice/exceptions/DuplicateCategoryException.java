package com.johan.project.libraryservice.exceptions;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException(final String message) {
        super(message);
    }
}