package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@LibraryBaseController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping(path = "/category")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCategory(final String category) {
        return categoryService.createCategory(category);
    }
}
