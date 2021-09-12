package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.exceptions.BookNotFoundException;
import com.johan.project.libraryservice.exceptions.DuplicateBookException;
import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.rest.request.BookRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService Unit Tests")
class BookServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BookService cut;

    private static final String TITLE = "Book 1";

    private static final String AUTHOR = "John Doe";

    private static final Long BOOK_ID = 1L;

    @Test
    void createBook() {
        when(booksRepository.findAll()).thenReturn(Collections.emptyList());
        when(booksRepository.createBook(BookRequest.of(TITLE, AUTHOR, Set.of(1L, 2L)))).thenReturn(BOOK_ID);

        final Long bookId = cut.createBook(BookRequest.of(TITLE, AUTHOR, Set.of(1L, 2L)));

        Assertions.assertEquals(BOOK_ID, bookId);
        verify(booksRepository, times(1)).createBook(any(BookRequest.class));
        verify(booksRepository, times(1)).findAll();
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void createBook_duplicateBook() {
        when(booksRepository.findAll()).thenReturn(Collections.singletonList(BooksEntity.builder().title(TITLE.toLowerCase()).author(AUTHOR.toLowerCase()).build()));

        Assertions.assertThrows(DuplicateBookException.class, () -> cut.createBook(BookRequest.of(TITLE, AUTHOR, Set.of(1L, 2L))));

        verify(booksRepository, times(1)).findAll();
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void deleteBook() {
        when(booksRepository.findById(BOOK_ID)).thenReturn(Optional.of(BooksEntity.builder().build()));

        cut.deleteBook(BOOK_ID);

        verify(booksRepository, times(1)).findById(BOOK_ID);
        verify(booksRepository, times(1)).delete(any(BooksEntity.class));
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void deleteBook_bookDoesntExist() {
        when(booksRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFoundException.class, () -> cut.deleteBook(BOOK_ID));

        verify(booksRepository, times(1)).findById(BOOK_ID);
        verifyNoMoreInteractions(booksRepository);
    }
}