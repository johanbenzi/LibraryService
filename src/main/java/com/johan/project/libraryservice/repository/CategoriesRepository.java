package com.johan.project.libraryservice.repository;

import com.johan.project.libraryservice.repository.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> {
    Optional<CategoriesEntity> findById(Long id);
}
