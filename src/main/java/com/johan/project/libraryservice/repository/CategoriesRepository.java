package com.johan.project.libraryservice.repository;

import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> {
}
