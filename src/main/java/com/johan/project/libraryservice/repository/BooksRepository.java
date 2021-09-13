package com.johan.project.libraryservice.repository;

import com.johan.project.libraryservice.repository.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long>, CustomBooksRepository {
    Optional<BooksEntity> findById(Long id);
}
