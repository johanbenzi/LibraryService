package com.johan.project.libraryservice.bdd.steps.api;


import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class LibraryAPISteps {
    @Steps
    private CommonAPISteps commonAPISteps;

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
}
