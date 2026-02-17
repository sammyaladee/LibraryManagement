package com.management.library.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.library.data.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    public Optional<Book> findByIsbn(String isbn);
}