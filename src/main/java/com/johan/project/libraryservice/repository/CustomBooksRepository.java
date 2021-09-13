package com.johan.project.libraryservice.repository;

import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.UsersEntity;

import java.util.Set;

public interface CustomBooksRepository {
    Long createBook(BookRequest bookRequest);

    Set<BooksEntity> loanBooksToUser(UsersEntity usersEntity, Set<BooksEntity> booksThatCanBeLoaned);

    void returnBooksFromUser(Set<BooksEntity> booksToBeReturned);
}
