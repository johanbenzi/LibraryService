package com.johan.project.libraryservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@LibraryBaseController
@RequiredArgsConstructor
public class CategoryController {

    @PutMapping(path = "/category")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCategory(final String category) {

    }

}
