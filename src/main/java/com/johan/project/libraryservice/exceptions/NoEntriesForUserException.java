package com.johan.project.libraryservice.exceptions;

public class NoEntriesForUserException extends RuntimeException {
    public NoEntriesForUserException(final String message) {
        super(message);
    }
}