package com.johan.project.libraryservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith({ SpringExtension.class })
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureWebTestClient
@Testcontainers
class AppContextIT {

  @Container
  private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:12")
          .withDatabaseName("library")
          .withUsername("sqladmin")
          .withPassword("secret");

  @BeforeAll
  static void init() {
    System.setProperty("DB_HOST", postgresqlContainer.getContainerIpAddress());
    System.setProperty("DB_PORT", postgresqlContainer.getMappedPort(5432).toString());
  }

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void applicationIsHealthyAfterStart() {
    webTestClient.get().uri("/health").exchange().expectStatus().isOk();
  }
}
