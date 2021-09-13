package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.model.response.BookResponse;
import com.johan.project.libraryservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@LibraryBaseController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PutMapping(path = "/book")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a book")
    @ApiResponse(responseCode = "201", description = "Success. Book created")
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

    @DeleteMapping(path = "/book/{book-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Create a book")
    @ApiResponse(responseCode = "204", description = "Success. Book deleted")
    @ApiResponse(responseCode = "400", description = "Error in deleting book, bad data found", content = @Content)
    @ApiResponse(responseCode = "404", description = "Book doesn't exist", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    public void deleteBook(@Parameter(name = "book-id", required = true, example = "1")
                           @PathVariable(value = "book-id") final Long bookId) {
        Preconditions.checkArgument(Objects.nonNull(bookId), "BookId cannot be null");
        bookService.deleteBook(bookId);
    }

    @PostMapping(path = "/user/{user-id}/books")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Loan books for user")
    @ApiResponse(responseCode = "200", description = "Success. Book loaned")
    @ApiResponse(responseCode = "400", description = "Error in loaning book, bad data found", content = @Content)
    @ApiResponse(responseCode = "404", description = "Book doesn't exist", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    public Set<BookResponse> loanBooks(@Parameter(name = "user-id", required = true, example = "1")
                                       @PathVariable(value = "user-id") final Long userId,
                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List of books to be loaned", content = {
                                               @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@ExampleObject(value = "[1, 2, 3]")})})
                                       @RequestBody final Set<Long> books) {
        Preconditions.checkArgument(Objects.nonNull(userId), "UserId cannot be null");
        Preconditions.checkArgument(Objects.nonNull(books) && !books.isEmpty(), "Books cannot be null");
        Preconditions.checkArgument(books.size() <= 3, "Cannot borrow more than 3 books");

        return bookService.loanBooks(userId, books);
    }

    @PatchMapping(path = "/user/{user-id}/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Return loaned books by user")
    @ApiResponse(responseCode = "204", description = "Success. Book returned")
    @ApiResponse(responseCode = "400", description = "Error in returning books, bad data found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    public void returnBooks(@Parameter(name = "user-id", required = true, example = "1")
                            @PathVariable(value = "user-id") final Long userId,
                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List of books to be returned", content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@ExampleObject(value = "[1, 2, 3]")})})
                            @RequestBody final Set<Long> books) {
        Preconditions.checkArgument(Objects.nonNull(userId), "UserId cannot be null");
        Preconditions.checkArgument(Objects.nonNull(books) && !books.isEmpty(), "Books cannot be null or empty");

        bookService.returnBooks(userId, books);
    }
}
