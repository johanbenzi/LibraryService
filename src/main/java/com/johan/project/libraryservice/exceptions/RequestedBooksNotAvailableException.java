package com.johan.project.libraryservice.exceptions;

public class RequestedBooksNotAvailableException extends RuntimeException {
    public RequestedBooksNotAvailableException(final String message) {
        super(message);
    }
}