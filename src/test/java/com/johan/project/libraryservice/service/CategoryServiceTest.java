package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.repository.CategoriesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
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
        when(categoriesRepository.createCategory(CATEGORY)).thenReturn(CATEGORY_ID);

        final Long categoryId = cut.createCategory(CATEGORY);

        Assertions.assertEquals(CATEGORY_ID, categoryId);
        verify(categoriesRepository, times(1)).createCategory(anyString());
        verifyNoMoreInteractions(categoriesRepository);
    }
}