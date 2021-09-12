package com.johan.project.libraryservice.rest;

import com.johan.project.libraryservice.rest.request.BookRequest;
import com.johan.project.libraryservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@LibraryBaseController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    public Long createBook(@RequestBody final BookRequest bookRequest) {
        return null;
    }
}
