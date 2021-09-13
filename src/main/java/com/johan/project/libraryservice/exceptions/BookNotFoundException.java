package com.johan.project.libraryservice.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(final String message) {
        super(message);
    }
}