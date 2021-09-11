package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.exceptions.DuplicateCategoryException;
import com.johan.project.libraryservice.repository.CategoriesRepository;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService Unit Tests")
class CategoryServiceTest {

    private static final String CATEGORY = "Cat 1";

    private static final Long CATEGORY_ID = 1L;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoryService cut;

    @Test
    void createCategory() {
        when(categoriesRepository.findAll()).thenReturn(new ArrayList<>());
        when(categoriesRepository.save(CategoriesEntity.builder().category(CATEGORY).build())).thenReturn(CategoriesEntity.builder().id(CATEGORY_ID).build());

        final Long categoryId = cut.createCategory(CATEGORY);

        Assertions.assertEquals(CATEGORY_ID, categoryId);
        verify(categoriesRepository, times(1)).save(any(CategoriesEntity.class));
        verify(categoriesRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoriesRepository);
    }

    @Test
    void createCategory_duplicateCategory() {
        when(categoriesRepository.findAll()).thenReturn(Collections.singletonList(CategoriesEntity.builder().category(CATEGORY.toLowerCase()).build()));

        Assertions.assertThrows(DuplicateCategoryException.class, () -> cut.createCategory(CATEGORY));

        verify(categoriesRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoriesRepository);
    }
}