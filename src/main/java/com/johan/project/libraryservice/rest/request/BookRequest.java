package com.johan.project.libraryservice.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class BookRequest {
    @Schema(description = "Book Title", example = "Cool Book")
    String title;

    @Schema(description = "Book Author", example = "John Doe")
    String author;

    @Schema(description = "Book Author", example = "[1,2]")
    Set<Long> categories;
}
