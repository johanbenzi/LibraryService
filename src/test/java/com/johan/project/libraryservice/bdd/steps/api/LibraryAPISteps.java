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

    @Step("Delete book from library")
    public void deleteBookFromLibrary() {
        commonAPISteps.deleteBookFromLibrary();
    }

    @Step("Add books to database")
    public void existingBooks(final List<List<String>> books) {
        commonAPISteps.existingBooks(books);
    }

    @Step("Add userId to session")
    public void saveUserId(final int userId) {
        commonAPISteps.saveUserId(userId);
    }

    @Step("Add bookId to session")
    public void saveBooksAndAuthor(final List<List<String>> collect) {
        commonAPISteps.saveBooksAndAuthor(collect);
    }

    @Step("Loan books from library")
    public void loanBooksFromLibrary() {
        commonAPISteps.loanBooksFromLibrary();
    }

    @Step("Assert loaned books")
    public void assertBooks(final List<List<String>> books) {
        commonAPISteps.assertBooks(books);
    }

    @Step("Return loaned books")
    public void returnBooksToLibrary() {
        commonAPISteps.returnBooksToLibrary();
    }
}
