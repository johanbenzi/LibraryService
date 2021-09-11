package com.johan.project.libraryservice.service;

import com.johan.project.libraryservice.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private CategoriesRepository categoriesRepository;

    public Long createCategory(final String category) {
        return categoriesRepository.createCategory(category);
    }
}
