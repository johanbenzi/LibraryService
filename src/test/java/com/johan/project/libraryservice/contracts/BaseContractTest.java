package com.johan.project.libraryservice.contracts;

import com.johan.project.libraryservice.exceptions.*;
import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.model.response.BookResponse;
import com.johan.project.libraryservice.service.BookService;
import com.johan.project.libraryservice.service.CategoryService;
import io.restassured.config.EncoderConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
@ActiveProfiles(value = "local")
@DirtiesContext
@Testcontainers
@Disabled
public class BaseContractTest {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:12")
            .withDatabaseName("library")
            .withUsername("sqladmin")
            .withPassword("secret");

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private BookService bookService;

    @BeforeAll
    static void init() {
        System.setProperty("DB_HOST", postgresqlContainer.getContainerIpAddress());
        System.setProperty("DB_PORT", postgresqlContainer.getMappedPort(5432).toString());
    }

    @BeforeEach
    void setup() {
        final EncoderConfig encoderConfig = new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssuredMockMvc.config = new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);
        RestAssuredMockMvc.webAppContextSetup(this.context);

        when(categoryService.createCategory("Category A")).thenReturn(1L);
        when(categoryService.createCategory("Category B")).thenThrow(new DuplicateCategoryException("Category already exists"));

        when(bookService.createBook(BookRequest.of("Awesome book", "John Doe", Set.of(1L)))).thenReturn(1L);
        when(bookService.createBook(BookRequest.of("The other awesome book", "John Doe", Set.of(2L)))).thenThrow(new DuplicateBookException("Book already exists"));
        when(bookService.createBook(BookRequest.of("Great Book", "Jane Doe", Set.of(99L)))).thenThrow(new DuplicateBookException("Category doesn't exist"));

        doThrow(new BookNotFoundException("Book doesn't exist"))
                .when(bookService).deleteBook(2L);

        when(bookService.loanBooks(1L, Set.of(1L))).thenReturn(Set.of(BookResponse.of(1L, "Book 1", "John Doe", Set.of("Travel"))));
        when(bookService.loanBooks(1L, Set.of(2L))).thenThrow(new RequestedBooksNotAvailableException("Book is not available"));

        doThrow(new BooksNotLoanedByUserException("Invalid entry"))
                .when(bookService).returnBooks(1L, Set.of(4L));

    }
}
