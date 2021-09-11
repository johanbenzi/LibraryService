package com.johan.project.libraryservice.bdd.steps;

import com.johan.project.libraryservice.bdd.steps.api.LibraryAPISteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.List;

public class LibrarySteps {

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
        throw new PendingException();
    }

    @Given("Existing categories")
    public void existingCategories(final List<String> categories) {
        throw new PendingException();
    }

    @Given("^A title (.*)$")
    public void saveTitle(final String title) {
        throw new PendingException();
    }

    @And("^An author (.*)$")
    public void saveAuthor(final String author) {
        throw new PendingException();
    }

    @And("^Categories (.*)$")
    @And("Categories")
    public void saveCategories(final List<String> categories) {
        throw new PendingException();
    }

    @When("The book is attempted to be saved")
    public void saveBookToLibrary() {
        throw new PendingException();
    }

    @When("The book is attempted to be deleted")
    public void deleteBookFromLibrary() {
        throw new PendingException();
    }

    @And("Existing Books")
    public void existingBooks() {
        throw new PendingException();
    }

    @Given("A user with id {int}")
    public void saveUserId(final int userId) {
        throw new PendingException();
    }

    @And("Books and Authors")
    public void saveBooksAndAuthor(final DataTable booksAndAuthors) {
        throw new PendingException();
    }

    @When("The book is attempted to be loaned")
    public void loanBooksFromLibrary() {
        throw new PendingException();
    }

    @And("The books are provided")
    public void assertBooks(final DataTable books) {
        throw new PendingException();
    }

    @When("The book is attempted to be returned")
    public void returnBooksToLibrary() {
        throw new PendingException();
    }
}
