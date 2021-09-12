package com.johan.project.libraryservice.repository.impl;

import com.johan.project.libraryservice.exceptions.UnrecognisedCategoryException;
import com.johan.project.libraryservice.repository.CustomBooksRepository;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import com.johan.project.libraryservice.rest.request.BookRequest;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomBooksRepositoryImpl implements CustomBooksRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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
}
