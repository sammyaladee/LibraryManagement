package com.management.library.controller;

import org.springframework.http.ResponseEntity;
import com.management.library.dtos.requests.CreateAuthorRequest;
import com.management.library.dtos.requests.UpdateAuthorRequest;
import com.management.library.services.AuthorService;

import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody CreateAuthorRequest createAuthorRequest) {
        return ResponseEntity.status(CREATED)
                .body(authorService.createAuthor(createAuthorRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") Long id) {
        return ResponseEntity.status(OK)
                .body(authorService.getAuthorById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAuthorByEmail(@RequestParam("email") String email) {
        return ResponseEntity.status(OK)
                .body(authorService.getAuthorByEmail(email));
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return ResponseEntity.status(OK)
                .body(authorService.getAllAuthor());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable @RequestBody UpdateAuthorRequest updateAuthorRequest) {
        return ResponseEntity.status(OK)
                .body(authorService.updateAuthor(updateAuthorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.status(OK)
                .body("Author deleted successfully");
    }
}