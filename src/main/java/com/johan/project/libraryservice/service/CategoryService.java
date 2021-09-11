package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.exceptions.DuplicateCategoryException;
import com.johan.project.libraryservice.repository.CategoriesRepository;
import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoriesRepository categoriesRepository;

    public Long createCategory(final String category) {
        if (categoriesRepository.findAll().stream().map(CategoriesEntity::getCategory).anyMatch(x -> x.equalsIgnoreCase(category))) {
            throw new DuplicateCategoryException("Category already exists");
        }
        return categoriesRepository.save(CategoriesEntity.builder().category(category).build()).getId();
    }
}
