package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.rest.request.BookRequest;
import com.johan.project.libraryservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@LibraryBaseController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PutMapping(path = "/book")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a book")
    @ApiResponse(responseCode = "200", description = "Success. Book created")
    @ApiResponse(responseCode = "400", description = "Error in creating book, bad data found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Book already exists", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    public Long createBook(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category name")
                           @RequestBody final BookRequest bookRequest) {
        Preconditions.checkArgument(Objects.nonNull(bookRequest), "BooksRequest cannot be null");
        Preconditions.checkArgument(StringUtils.isNotBlank(bookRequest.getTitle()), "Title cannot be blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(bookRequest.getAuthor()), "Author cannot be blank");
        Preconditions.checkArgument(Objects.nonNull(bookRequest.getCategories()) && !bookRequest.getCategories().isEmpty(),
                "Categories cannot be empty");
        return bookService.createBook(bookRequest);
    }
}
