package com.management.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.library.dtos.requests.CreateBookRequest;
import com.management.library.dtos.requests.DeleteBookRequest;
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
    public ResponseEntity<?> createBook(@ModelAttribute CreateBookRequest createBookRequest){
        return ResponseEntity.status(CREATED)
                .body(bookService.createBook(createBookRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetBookById(@PathVariable("id") Long id){
        return ResponseEntity.status(OK)
                .body(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<?> GetAllBooks(){
        return ResponseEntity.status(OK)
                .body(bookService.getAllBooks());
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@ModelAttribute UpdateBookRequest updateBookRequest){
        return ResponseEntity.status(OK)
                .body(bookService.updateBook(updateBookRequest));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBook(@ModelAttribute DeleteBookRequest deleteBookRequest){
        bookService.deleteBook(deleteBookRequest);
        return ResponseEntity.status(OK)
                .body("Book deleted successfully");
    }

    
}
