package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.exceptions.BookNotFoundException;
import com.johan.project.libraryservice.exceptions.DuplicateBookException;
import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.rest.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksRepository booksRepository;

    public Long createBook(final BookRequest bookRequest) {
        if (booksRepository.findAll().stream().anyMatch(x -> x.getTitle().equalsIgnoreCase(bookRequest.getTitle())
                && x.getAuthor().equalsIgnoreCase(bookRequest.getAuthor()))) {
            throw new DuplicateBookException("Book Already Exists");
        }
        return booksRepository.createBook(bookRequest);
    }

    public void deleteBook(final Long bookId) {
        final var booksEntity = booksRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book doesn't exist"));
        booksRepository.delete(booksEntity);
    }
}
