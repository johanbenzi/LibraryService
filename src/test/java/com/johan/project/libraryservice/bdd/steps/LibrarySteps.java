package com.johan.project.libraryservice.bdd.steps;

import com.johan.project.libraryservice.bdd.steps.api.LibraryAPISteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LibrarySteps {

    @After
    public void afterScenario() {
        steps.afterScenario();
    }

    @Steps
    private LibraryAPISteps steps;

    @Given("A category {word}")
    public void saveCategory(final String category) {
        steps.saveCategory(category);
    }

    @When("The category is saved")
    public void saveCategoryToLibrary() {
        steps.saveCategoryToLibrary();
    }

    @Then("A response code of {int} is obtained")
    public void assertResponseCode(final int responseCode) {
        steps.assertResponseCode(responseCode);
    }

    @And("An ID is returned")
    public void assertNonNullResponse() {
        steps.assertNonNullResponse();
    }

    @Given("Existing categories")
    public void existingCategories(final List<String> categories) {
        steps.existingCategories(categories);
    }

    @Given("^A title (.*)$")
    public void saveTitle(final String title) {
        steps.saveTitle(title);
    }

    @And("^An author (.*)$")
    public void saveAuthor(final String author) {
        steps.saveAuthor(author);
    }

    @And("^Categories (.*)$")
    public void saveCategoriesDelimited(final String categories) {
        steps.saveCategories(Arrays.asList(categories.trim().split("\\s*,\\s*")));
    }

    @And("Categories")
    public void saveCategories(final List<String> categories) {
        steps.saveCategories(categories);
    }

    @When("The book is attempted to be saved")
    public void saveBookToLibrary() {
        steps.saveBookToLibrary();
    }

    @When("The book is attempted to be deleted")
    public void deleteBookFromLibrary() {
        steps.deleteBookFromLibrary();
    }

    @And("Existing Books")
    public void existingBooks(final DataTable books) {
        steps.existingBooks(books.cells().stream().skip(1).collect(Collectors.toList()));
    }

    @Given("A user with id {int}")
    public void saveUserId(final int userId) {
        steps.saveUserId(userId);
    }

    @And("Books and Authors")
    public void saveBooksAndAuthor(final DataTable booksAndAuthors) {
        steps.saveBooksAndAuthor(booksAndAuthors.cells().stream().skip(1).collect(Collectors.toList()));
    }

    @When("The book is attempted to be loaned")
    public void loanBooksFromLibrary() {
        steps.loanBooksFromLibrary();
    }

    @And("The books are provided")
    public void assertBooks(final DataTable books) {
        steps.assertBooks(books.cells().stream().skip(1).collect(Collectors.toList()));
    }

    @When("The book is attempted to be returned")
    public void returnBooksToLibrary() {
        steps.returnBooksToLibrary();
    }
}
