package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.rest.request.BookRequest;
import com.johan.project.libraryservice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookController Unit Tests")
class BookControllerTest {

    @InjectMocks
    private BookController cut;

    @Mock
    private BookService bookService;

    private BookRequest bookRequest;

    private static final Long BOOK_ID = 123L;

    @Test
    void createBook() {
        bookRequest = BookRequest.of("That Book", "John Doe", Set.of(1L, 2L));
        when(bookService.createBook(bookRequest)).thenReturn(BOOK_ID);

        final Long bookId = cut.createBook(bookRequest);

        Assertions.assertEquals(BOOK_ID, bookId);
    }

    @Test
    void createBook_nullRequest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.createBook(null));

        verifyNoInteractions(bookService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void createBook_blankTitle(final String title) {
        bookRequest = BookRequest.of(title, "John Doe", Set.of(1L, 2L));

        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.createBook(bookRequest));

        verifyNoInteractions(bookService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void createBook_blankAuthor(final String author) {
        bookRequest = BookRequest.of("That Book", author, Set.of(1L, 2L));

        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.createBook(bookRequest));

        verifyNoInteractions(bookService);
    }

    @Test
    void createBook_nullCategories() {
        bookRequest = BookRequest.of("That Book", "John Doe", null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.createBook(bookRequest));

        verifyNoInteractions(bookService);
    }

    @Test
    void createBook_emptyCategories() {
        bookRequest = BookRequest.of("That Book", "John Doe", new HashSet<>());

        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.createBook(bookRequest));

        verifyNoInteractions(bookService);
    }

    @Test
    void deleteBook() {
        cut.deleteBook(BOOK_ID);

        verify(bookService, times(1)).deleteBook(BOOK_ID);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void deleteBook_nullBookId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.deleteBook(null));

        verifyNoInteractions(bookService);
    }
}