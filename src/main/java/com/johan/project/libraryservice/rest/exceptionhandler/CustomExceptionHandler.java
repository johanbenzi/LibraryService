package com.johan.project.libraryservice.rest.exceptionhandler;

import com.johan.project.libraryservice.exceptions.BookNotFoundException;
import com.johan.project.libraryservice.exceptions.DuplicateBookException;
import com.johan.project.libraryservice.exceptions.DuplicateCategoryException;
import com.johan.project.libraryservice.exceptions.UnrecognisedCategoryException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(final IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final DuplicateCategoryException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final DuplicateBookException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final UnrecognisedCategoryException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final BookNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("Unexpected error in performing operation", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
