package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.exceptions.*;
import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.repository.UsersRepository;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import com.johan.project.libraryservice.repository.entity.UsersEntity;
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

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private BookService cut;

    private static final String TITLE = "Book 1";

    private static final String AUTHOR = "John Doe";

    private static final Long BOOK_ID = 1L;

    private static final Long USER_ID = 1L;

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

    @Test
    void loanBooks() {
        final BooksEntity booksEntity = BooksEntity
                .builder()
                .id(BOOK_ID)
                .isLoaned(false)
                .categories(Set.of(CategoriesEntity.builder().category("Cat A").build()))
                .title("Title A")
                .author("John Doe")
                .build();
        final UsersEntity usersEntity = UsersEntity.builder().userId(USER_ID).books(Set.of(booksEntity)).build();
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.of(usersEntity));
        when(booksRepository.findById(BOOK_ID)).thenReturn(Optional.of(booksEntity));
        when(booksRepository.loanBooksToUser(usersEntity, Set.of(booksEntity))).thenReturn(Set.of(booksEntity));

        cut.loanBooks(USER_ID, Set.of(1L));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verify(booksRepository, times(1)).findById(anyLong());
        verify(booksRepository, times(1)).loanBooksToUser(any(UsersEntity.class), anySet());
        verifyNoMoreInteractions(usersRepository);
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void loanBooks_newUser() {
        final BooksEntity booksEntity = BooksEntity
                .builder()
                .id(BOOK_ID)
                .isLoaned(false)
                .categories(Set.of(CategoriesEntity.builder().category("Cat A").build()))
                .title("Title A")
                .author("John Doe")
                .build();
        final UsersEntity usersEntity = UsersEntity.builder().userId(USER_ID).books(Set.of(booksEntity)).build();
        when(usersRepository.save(UsersEntity.builder().userId(USER_ID).build())).thenReturn(usersEntity);
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(booksRepository.findById(BOOK_ID)).thenReturn(Optional.of(booksEntity));
        when(booksRepository.loanBooksToUser(usersEntity, Set.of(booksEntity))).thenReturn(Set.of(booksEntity));

        cut.loanBooks(USER_ID, Set.of(1L));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verify(usersRepository, times(1)).save(any(UsersEntity.class));
        verify(booksRepository, times(1)).findById(anyLong());
        verify(booksRepository, times(1)).loanBooksToUser(any(UsersEntity.class), anySet());
        verifyNoMoreInteractions(usersRepository);
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void loanBooks_exceedLimit() {
        final BooksEntity booksEntity = BooksEntity
                .builder()
                .id(BOOK_ID)
                .isLoaned(false)
                .categories(Set.of(CategoriesEntity.builder().category("Cat A").build()))
                .title("Title A")
                .author("John Doe")
                .build();
        final UsersEntity usersEntity = UsersEntity.builder().userId(USER_ID).books(Set.of(booksEntity)).build();
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.of(usersEntity));

        Assertions.assertThrows(LoanAttemptNotWithinLimitException.class, () -> cut.loanBooks(USER_ID, Set.of(4L, 2L, 3L)));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verifyNoMoreInteractions(usersRepository);
        verifyNoInteractions(booksRepository);
    }

    @Test
    void loanBooks_notAllBooksAreAvailable() {
        final BooksEntity booksEntity = BooksEntity
                .builder()
                .id(BOOK_ID)
                .isLoaned(true)
                .categories(Set.of(CategoriesEntity.builder().category("Cat A").build()))
                .title("Title A")
                .author("John Doe")
                .build();
        final UsersEntity usersEntity = UsersEntity.builder().userId(USER_ID).books(Set.of(booksEntity)).build();
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.of(usersEntity));

        Assertions.assertThrows(RequestedBooksNotAvailableException.class, () -> cut.loanBooks(USER_ID, Set.of(1L)));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verify(booksRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(usersRepository);
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void returnBooks() {
        final BooksEntity booksEntity = BooksEntity
                .builder()
                .id(BOOK_ID)
                .isLoaned(true)
                .categories(Set.of(CategoriesEntity.builder().category("Cat A").build()))
                .title("Title A")
                .author("John Doe")
                .build();
        final UsersEntity usersEntity = UsersEntity.builder().userId(USER_ID).books(Set.of(booksEntity)).build();
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.of(usersEntity));

        cut.returnBooks(USER_ID, Set.of(USER_ID));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verify(booksRepository, times(1)).returnBooksFromUser(anySet());
        verifyNoMoreInteractions(usersRepository);
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void returnBooks_newUser() {
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoEntriesForUserException.class, () -> cut.returnBooks(USER_ID, Set.of(BOOK_ID)));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verifyNoMoreInteractions(usersRepository);
        verifyNoInteractions(booksRepository);
    }

    @Test
    void returnBooks_booksNotLoaned() {
        final BooksEntity booksEntity = BooksEntity
                .builder()
                .id(BOOK_ID)
                .isLoaned(true)
                .categories(Set.of(CategoriesEntity.builder().category("Cat A").build()))
                .title("Title A")
                .author("John Doe")
                .build();
        final UsersEntity usersEntity = UsersEntity.builder().userId(USER_ID).books(Set.of(booksEntity)).build();
        when(usersRepository.findByUserId(USER_ID)).thenReturn(Optional.of(usersEntity));

        Assertions.assertThrows(BooksNotLoanedByUserException.class, () -> cut.returnBooks(USER_ID, Set.of(3L)));

        verify(usersRepository, times(1)).findByUserId(anyLong());
        verifyNoMoreInteractions(usersRepository);
        verifyNoInteractions(booksRepository);
    }
}