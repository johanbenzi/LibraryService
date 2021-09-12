package com.johan.project.libraryservice.repository;

import com.johan.project.libraryservice.rest.request.BookRequest;

public interface CustomBooksRepository {
    Long createBook(BookRequest bookRequest);
}
