package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.service.CategoryService;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryController Unit Tests")
class CategoryControllerTest {

    private static final String CATEGORY = "Cat 1";

    private static final Long CATEGORY_ID = 1L;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController cut;

    @Test
    void createCategory() {
        when(categoryService.createCategory(CATEGORY)).thenReturn(CATEGORY_ID);

        final Long categoryId = cut.createCategory(CATEGORY);

        Assertions.assertEquals(CATEGORY_ID, categoryId);
        verify(categoryService, times(1)).createCategory(anyString());
        verifyNoMoreInteractions(categoryService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void createCategory_blankString(final String category) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cut.createCategory(category));

        verifyNoInteractions(categoryService);
    }
}