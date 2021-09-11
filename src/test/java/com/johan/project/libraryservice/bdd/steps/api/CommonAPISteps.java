package com.johan.project.libraryservice.bdd.steps.api;

import com.johan.project.libraryservice.LibraryServiceApplication;
import lombok.extern.log4j.Log4j2;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = LibraryServiceApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@ContextConfiguration
@Log4j2
public class CommonAPISteps {

    @LocalServerPort
    private int port;

    private String libraryServiceUrl;

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

    private void setLibraryServiceUrl() {
        SerenityRest.clear();
        SerenityRest.enableLoggingOfRequestAndResponseIfValidationFails();
        try {
            libraryServiceUrl = format("http://%s:%s", InetAddress.getLocalHost().getHostAddress(), port);
        } catch (final UnknownHostException e) {
            log.error(e, e);
        }
    }
}
