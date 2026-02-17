package com.management.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.management.library.dtos.requests.CreateBookRequest;
import com.management.library.dtos.requests.UpdateBookRequest;
import com.management.library.services.BookService;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody CreateBookRequest createBookRequest) {
        return ResponseEntity.status(CREATED)
                .body(bookService.createBook(createBookRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.status(OK)
                .body(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.status(OK)
                .body(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id,
                                         @RequestBody UpdateBookRequest updateBookRequest) {
        return ResponseEntity.status(OK)
                .body(bookService.updateBook(updateBookRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(OK)
                .body("Book deleted successfully");
    }
}