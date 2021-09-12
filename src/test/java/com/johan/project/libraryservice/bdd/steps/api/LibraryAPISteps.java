package com.johan.project.libraryservice.bdd.steps.api;


import io.cucumber.java.After;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.List;

public class LibraryAPISteps {
    @Steps
    private CommonAPISteps commonAPISteps;

    @After
    public void afterScenario() {
        commonAPISteps.afterScenario();
    }

    @Step("Save category in serenity session")
    public void saveCategory(final String category) {
        commonAPISteps.saveCategory(category);
    }

    @Step("Save category through API")
    public void saveCategoryToLibrary() {
        commonAPISteps.saveCategoryToLibrary();
    }

    @Step("Assert response code")
    public void assertResponseCode(final int responseCode) {
        commonAPISteps.assertResponseCode(responseCode);
    }

    @Step("Assert the response is non null")
    public void assertNonNullResponse() {
        commonAPISteps.assertNonNullResponse();
    }

    @Step("Saving list of categories to database")
    public void existingCategories(final List<String> categories) {
        commonAPISteps.existingCategories(categories);
    }

    @Step("Save title to serenity session")
    public void saveTitle(final String title) {
        commonAPISteps.saveTitle(title);
    }

    @Step("Save author to serenity session")
    public void saveAuthor(final String author) {
        commonAPISteps.saveAuthor(author);
    }

    @Step("Save categories to serenity session")
    public void saveCategories(final List<String> categories) {
        commonAPISteps.saveCategories(categories);
    }

    @Step("Save book through api")
    public void saveBookToLibrary() {
        commonAPISteps.saveBookToLibrary();
    }
}
