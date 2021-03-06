package com.johan.project.libraryservice.repository.impl;

import com.johan.project.libraryservice.exceptions.UnrecognisedCategoryException;
import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.repository.CustomBooksRepository;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import com.johan.project.libraryservice.repository.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CustomBooksRepositoryImpl implements CustomBooksRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Long createBook(final BookRequest bookRequest) {
        final Set<CategoriesEntity> categoriesEntities = bookRequest.getCategories().stream()
                .map(x -> entityManager.find(CategoriesEntity.class, x)).collect(Collectors.toSet());

        if (categoriesEntities.stream().anyMatch(Objects::isNull)) {
            throw new UnrecognisedCategoryException("Category doesn't exist");
        }
        final var booksEntity = BooksEntity
                .builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .categories(categoriesEntities)
                .build();

        entityManager.persist(booksEntity);
        entityManager.flush();
        return booksEntity.getId();
    }

    @Override
    public Set<BooksEntity> loanBooksToUser(final UsersEntity usersEntity, final Set<BooksEntity> booksThatCanBeLoaned) {
        booksThatCanBeLoaned.forEach(book -> {
            book.setLoaned(true);
            book.setUser(usersEntity);
            entityManager.merge(book);
        });
        entityManager.flush();
        return booksThatCanBeLoaned;
    }

    @Override
    @Transactional
    public void returnBooksFromUser(final Set<BooksEntity> booksToBeReturned) {
        booksToBeReturned.forEach(book -> {
            book.setLoaned(false);
            book.setUser(null);
            entityManager.merge(book);
        });
        entityManager.flush();
    }
}
