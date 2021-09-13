package com.johan.project.libraryservice.bdd.steps.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johan.project.libraryservice.LibraryServiceApplication;
import com.johan.project.libraryservice.model.request.BookRequest;
import com.johan.project.libraryservice.model.response.BookResponse;
import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.repository.CategoriesRepository;
import com.johan.project.libraryservice.repository.entity.BooksEntity;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import io.cucumber.java.After;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.log4j.Log4j2;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = LibraryServiceApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@ContextConfiguration
@Log4j2
public class CommonAPISteps {

    @LocalServerPort
    private int port;

    private String libraryServiceUrl;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @After
    public void afterScenario() {
        categoriesRepository.deleteAll();
        booksRepository.deleteAll();
    }

    public void saveCategory(final String category) {
        Serenity.getCurrentSession().put("Category", category);
    }

    public void saveCategoryToLibrary() {
        setLibraryServiceUrl();
        SerenityRest.given()
                .when()
                .body(Serenity.getCurrentSession().get("Category"))
                .put(libraryServiceUrl + "/library/category");
    }

    public void assertResponseCode(final int responseCode) {
        assertThat(SerenityRest.then().extract().statusCode(), is(responseCode));
    }

    public void assertNonNullResponse() {
        Serenity.getCurrentSession().put("ResponseID", SerenityRest.then().extract().response().body().as(Long.class));
        assertThat(SerenityRest.then().extract().response().body().as(Long.class), notNullValue());
    }

    public void existingCategories(final List<String> categories) {
        Serenity.getCurrentSession().put("CategoryMap", categories.stream()
                .map(x -> categoriesRepository.save(CategoriesEntity.builder().category(x).build()))
                .collect(Collectors.toMap(CategoriesEntity::getCategory, CategoriesEntity::getId)));
    }

    public void saveTitle(final String title) {
        Serenity.getCurrentSession().put("Title", title);
    }

    public void saveAuthor(final String author) {
        Serenity.getCurrentSession().put("Author", author);
    }

    public void saveCategories(final List<String> categories) {
        Serenity.getCurrentSession().put("Categories", categories);
    }

    public void saveBookToLibrary() {
        setLibraryServiceUrl();
        SerenityRest.given()
                .when()
                .body(aBookRequestFromSession())
                .contentType("application/json")
                .put(libraryServiceUrl + "/library/book");
    }

    public void deleteBookFromLibrary() {
        setLibraryServiceUrl();
        SerenityRest.given()
                .when()
                .delete(libraryServiceUrl + "/library/book/" + Serenity.getCurrentSession().get("ResponseID"));
    }

    public void existingBooks(final List<List<String>> books) {
        final Map<String, CategoriesEntity> categoryMap = categoriesRepository.findAll().stream().collect(Collectors.toMap(CategoriesEntity::getCategory, x -> x));
        final var booksEntities = books.stream().map(x ->
                BooksEntity.builder().title(x.get(0)).author(x.get(1)).categories(Arrays.stream(x.get(2)
                        .split("\\s*,\\s*")).map(categoryMap::get).collect(Collectors.toSet())).build()).collect(Collectors.toList());

        booksRepository.saveAllAndFlush(booksEntities);
    }

    public void saveUserId(final int userId) {
        Serenity.getCurrentSession().put("UserId", userId);
    }

    public void saveBooksAndAuthor(final List<List<String>> booksAndAuthors) {
        final List<Long> bookIds = new ArrayList<>();
        for (final var book : booksRepository.findAll()) {
            if (booksAndAuthors.stream().anyMatch(x -> x.get(0).equalsIgnoreCase(book.getTitle()) && x.get(1).equalsIgnoreCase(book.getAuthor()))) {
                bookIds.add(book.getId());
            }
        }
        Serenity.getCurrentSession().put("BookIds", bookIds);
    }

    public void loanBooksFromLibrary() {
        setLibraryServiceUrl();
        SerenityRest.given()
                .when()
                .contentType("application/json")
                .accept("application/json")
                .body(Serenity.getCurrentSession().get("BookIds"))
                .post(libraryServiceUrl + "/library/user/" + Serenity.getCurrentSession().get("UserId") + "/books");
    }

    public void assertBooks(final List<List<String>> books) {
        Serenity.getCurrentSession().get("ResponseID");
        final var bookMap = SerenityRest.then().extract().response().body().as(new TypeRef<List<BookResponse>>() {
        }).stream().collect(Collectors.toMap(x -> format("%s-%s", x.getTitle(), x.getAuthor()), x -> x));

        books.forEach(b -> {
            final var key = b.get(0) + "-" + b.get(1);
            assertTrue(bookMap.containsKey(key));
            assertTrue(bookMap.get(key).getCategories().containsAll(Arrays.stream(b.get(2).split("\\s*,\\s*")).collect(Collectors.toSet())));
        });
    }

    public void returnBooksToLibrary() {
        setLibraryServiceUrl();
        SerenityRest.given()
                .when()
                .contentType("application/json")
                .accept("application/json")
                .body(Serenity.getCurrentSession().get("BookIds"))
                .patch(libraryServiceUrl + "/library/user/" + Serenity.getCurrentSession().get("UserId") + "/books");
    }

    private void setLibraryServiceUrl() {
        SerenityRest.clear();
        SerenityRest.enableLoggingOfRequestAndResponseIfValidationFails();
        try {
            libraryServiceUrl = format("http://%s:%s", InetAddress.getLocalHost().getHostAddress(), port);
        } catch (final UnknownHostException e) {
            log.error(e, e);
        }
    }

    private BookRequest aBookRequestFromSession() {

        final Map<String, Long> categoryMap = (Map<String, Long>) Serenity.getCurrentSession().get("CategoryMap");
        final Set<Long> categories = ((List<String>) Serenity.getCurrentSession().get("Categories")).stream()
                .map(x -> categoryMap.containsKey(x) ? categoryMap.get(x) : Long.MAX_VALUE).collect(Collectors.toSet());
        return BookRequest.of(String.valueOf(Serenity.getCurrentSession().get("Title")),
                String.valueOf(Serenity.getCurrentSession().get("Author")), categories);
    }
}
