package com.johan.project.libraryservice.repository.impl;

import com.johan.project.libraryservice.repository.CustomCategoriesRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomCategoriesRepositoryImpl implements CustomCategoriesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long createCategory(final String category) {
        return null;
    }
}
