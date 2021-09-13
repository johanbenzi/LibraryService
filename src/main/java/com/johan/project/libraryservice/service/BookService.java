package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.exceptions.*;
import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.model.response.BookResponse;
import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.repository.UsersRepository;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.johan.project.libraryservice.constants.AppConstants.LOAN_LIMIT;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksRepository booksRepository;

    private final UsersRepository usersRepository;

    @Transactional
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

    @Transactional
    public Set<BookResponse> loanBooks(final Long userId, final Set<Long> requestedBooks) {
        final var usersEntity = usersRepository.findByUserId(userId).orElseGet(() -> usersRepository.save(UsersEntity.builder().userId(userId).build()));

        validateIfLoanedTotalWithRequestIsInLimit(usersEntity.getBooks().size(), requestedBooks.size());

        final var booksThatCanBeLoaned = requestedBooks.stream()
                .map(x -> booksRepository.findById(x).orElse(null))
                .filter(Objects::nonNull)
                .filter(x -> !x.isLoaned())
                .collect(Collectors.toSet());

        validateIfAllRequestedBooksIsAvailable(booksThatCanBeLoaned.size(), requestedBooks.size());

        return booksRepository.loanBooksToUser(usersEntity, booksThatCanBeLoaned).stream().map(BooksEntity::toResponse).collect(Collectors.toSet());
    }

    public void returnBooks(final Long userId, final Set<Long> booksToBeReturned) {
        final var usersEntity = usersRepository.findByUserId(userId).orElseThrow(() -> new NoEntriesForUserException("Books not loaned by user"));

        final var booksUserLoaned = usersEntity.getBooks()
                .stream()
                .filter(x -> booksToBeReturned.contains(x.getId()))
                .collect(Collectors.toSet());

        validateIfUserLoanedAllBooksInReturnRequest(booksUserLoaned.size(), booksToBeReturned.size());

        booksRepository.returnBooksFromUser(booksUserLoaned);
    }

    private void validateIfLoanedTotalWithRequestIsInLimit(final int currentlyLoanedByUserCount, final int requestedByUserCount) {
        if (currentlyLoanedByUserCount + requestedByUserCount > LOAN_LIMIT) {
            throw new LoanAttemptNotWithinLimitException("Loan attempt cannot be processed as user will loan more than limit");
        }
    }

    private void validateIfAllRequestedBooksIsAvailable(final int booksThatCanBeLoanedToUser, final int requestedByUserCount) {
        if (booksThatCanBeLoanedToUser != requestedByUserCount) {
            throw new RequestedBooksNotAvailableException("Not all requested books are available");
        }
    }

    private void validateIfUserLoanedAllBooksInReturnRequest(final int userLoanedBooksCount, final int booksToBeReturnedCount) {
        if (userLoanedBooksCount != booksToBeReturnedCount) {
            throw new BooksNotLoanedByUserException("Books not loaned by user");
        }
    }
}
