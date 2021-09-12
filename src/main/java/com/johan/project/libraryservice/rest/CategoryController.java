package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@LibraryBaseController
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "Handles requests to do CRUD operations on categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping(path = "/category")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a category")
    @ApiResponse(responseCode = "201", description = "Success. Category created", content = {
            @Content(examples = {@ExampleObject(value = "123")})})
    @ApiResponse(responseCode = "400", description = "Error in creating category, bad data found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    public Long createCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category name", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@ExampleObject(value = "Category A")})})
                               @RequestBody final String category) {
        Preconditions.checkArgument(StringUtils.isNotBlank(category), "Category cannot be blank");
        return categoryService.createCategory(category);
    }
}
