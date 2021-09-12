package com.johan.project.libraryservice.bdd.steps.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johan.project.libraryservice.LibraryServiceApplication;
import com.johan.project.libraryservice.repository.BooksRepository;
import com.johan.project.libraryservice.repository.CategoriesRepository;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import com.johan.project.libraryservice.rest.request.BookRequest;
import io.cucumber.java.After;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
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
