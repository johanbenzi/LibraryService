package com.johan.project.libraryservice.rest.request;

import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class BookRequest {
    String title;

    String author;

    Set<Long> categories;
}
