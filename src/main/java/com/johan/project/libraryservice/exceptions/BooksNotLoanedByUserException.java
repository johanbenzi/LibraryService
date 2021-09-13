package com.johan.project.libraryservice.exceptions;

public class BooksNotLoanedByUserException extends RuntimeException {
    public BooksNotLoanedByUserException(final String message) {
        super(message);
    }
}