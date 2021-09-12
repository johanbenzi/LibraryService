package com.johan.project.libraryservice.repository.impl;

import com.johan.project.libraryservice.exceptions.UnrecognisedCategoryException;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import com.johan.project.libraryservice.rest.request.BookRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomBooksRepositoryImplTest {

    @InjectMocks
    private CustomBooksRepositoryImpl cut;

    @Mock
    private EntityManager entityManager;

    private static BookRequest bookRequest;

    private static Set<Long> categories;

    @Test
    void createBook() {
        categories = Set.of(2L, 3L);
        bookRequest = BookRequest.of("Book A", "Author A", categories);
        categories.forEach(category -> when(entityManager.find(CategoriesEntity.class, category)).thenReturn(CategoriesEntity.builder().id(category).category("Cat " + category).build()));

        cut.createBook(bookRequest);

        verify(entityManager, times(categories.size())).find(eq(CategoriesEntity.class), any(Long.class));
        verify(entityManager, times(1)).persist(any(BooksEntity.class));
        verify(entityManager, times(1)).flush();
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    void createBook_unrecognizedCategory() {
        categories = Set.of(2L, 3L);
        bookRequest = BookRequest.of("Book A", "Author A", categories);
        categories.forEach(category -> {
            when(entityManager.find(CategoriesEntity.class, category)).thenReturn(null);
        });

        Assertions.assertThrows(UnrecognisedCategoryException.class, () -> cut.createBook(bookRequest));

        verify(entityManager, times(categories.size())).find(eq(CategoriesEntity.class), any(Long.class));
        verify(entityManager, times(0)).persist(any(BooksEntity.class));
        verify(entityManager, times(0)).flush();
        verifyNoMoreInteractions(entityManager);
    }
}