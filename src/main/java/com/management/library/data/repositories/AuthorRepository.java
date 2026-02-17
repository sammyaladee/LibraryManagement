package com.management.library.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.library.data.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
    public Optional<Author> findByEmail(String email);
}