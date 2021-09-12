package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.rest.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksRepository booksRepository;

    public Long createBook(final BookRequest bookRequest) {
        return null;
    }
}
