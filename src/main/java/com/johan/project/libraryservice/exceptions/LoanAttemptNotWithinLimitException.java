package com.johan.project.libraryservice.exceptions;

public class IsLoanAttemptWithinLimit extends RuntimeException {
    public IsLoanAttemptWithinLimit(final String message) {
        super(message);
    }
}