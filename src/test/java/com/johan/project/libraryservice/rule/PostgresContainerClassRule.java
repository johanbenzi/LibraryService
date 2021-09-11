package com.johan.project.libraryservice.rule;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerClassRule extends TestWatcher {

    private static PostgreSQLContainer<?> postgresqlContainer;

    @Override
    protected void starting(final Description description) {
        try {
            final Network network = Network.newNetwork();
            postgresqlContainer = new PostgreSQLContainer<>("postgres:12")
                    .withDatabaseName("library")
                    .withUsername("sqladmin")
                    .withPassword("secret");
            postgresqlContainer.start();

            System.setProperty("DB_HOST", postgresqlContainer.getContainerIpAddress());
            System.setProperty("DB_PORT", postgresqlContainer.getMappedPort(5432).toString());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
