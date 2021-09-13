package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.model.response.BookResponse;
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

    @Test
    void loanBooks() {
        when(bookService.loanBooks(1L, Set.of(1L, 2L, 3L))).thenReturn(Set.of(BookResponse.of(1, "That book", "John Doe", Set.of("Cat A"))));

        cut.loanBooks(1L, Set.of(1L, 2L, 3L));

        verify(bookService, times(1)).loanBooks(anyLong(), anySet());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void loanBooks_nullUserId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.loanBooks(null, Set.of(1L, 2L, 3L)));

        verifyNoInteractions(bookService);
    }

    @Test
    void loanBooks_nullBooks() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.loanBooks(1L, null));

        verifyNoInteractions(bookService);
    }

    @Test
    void loanBooks_emptyBooks() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.loanBooks(1L, Set.of()));

        verifyNoInteractions(bookService);
    }

    @Test
    void loanBooks_booksOverLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.loanBooks(1L, Set.of(1L, 2L, 3L, 4L)));

        verifyNoInteractions(bookService);
    }

    @Test
    void returnBooks() {
        cut.returnBooks(1L, Set.of(1L, 2L, 3L));

        verify(bookService, times(1)).returnBooks(anyLong(), anySet());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void returnBooks_nullUserId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.returnBooks(null, Set.of(1L, 2L, 3L)));

        verifyNoInteractions(bookService);
    }

    @Test
    void returnBooks_nullBooks() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.returnBooks(1L, null));

        verifyNoInteractions(bookService);
    }

    @Test
    void returnBooks_emptyBooks() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.returnBooks(1L, Set.of()));

        verifyNoInteractions(bookService);
    }
}