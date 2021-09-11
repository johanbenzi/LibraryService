package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@LibraryBaseController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping(path = "/category")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCategory(@RequestBody final String category) {
        Preconditions.checkArgument(StringUtils.isNotBlank(category), "Category cannot be blank");
        return categoryService.createCategory(category);
    }
}
