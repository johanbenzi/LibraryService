package com.johan.project.libraryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LibraryServiceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }
}
