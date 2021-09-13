package com.johan.project.libraryservice.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class BookResponse {

    @Schema(description = "Book Id", example = "123")
    long id;

    @Schema(description = "Book Title", example = "Cool Book")
    String title;

    @Schema(description = "Book Author", example = "John Doe")
    String author;

    @Schema(description = "Book Author", example = "[\"Category A\",\"Category B\"]")
    Set<String> categories;
}
