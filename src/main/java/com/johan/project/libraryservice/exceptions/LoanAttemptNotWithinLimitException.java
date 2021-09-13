package com.johan.project.libraryservice.exceptions;

public class LoanAttemptNotWithinLimitException extends RuntimeException {
    public LoanAttemptNotWithinLimitException(final String message) {
        super(message);
    }
}