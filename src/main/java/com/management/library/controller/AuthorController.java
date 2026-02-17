package com.management.library.controller;

import org.springframework.http.ResponseEntity;

import com.management.library.dtos.requests.CreateAuthorRequest;
import com.management.library.dtos.requests.UpdateAuthorRequest;
import com.management.library.services.AuthorServiceImpl;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorServiceImpl authorService;


    @PostMapping
    public ResponseEntity<?> createAuthor(@ModelAttribute CreateAuthorRequest createAuthorRequest){ 
        return ResponseEntity.status(CREATED)
                .body(authorService.createAuthor(createAuthorRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetAuthorById(@PathVariable("id") Long id){ 
        return ResponseEntity.status(OK)
                .body(authorService.getAuthorById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> GetAuthorByEmail(@PathVariable("email") String email){ 
        return ResponseEntity.status(OK)
                .body(authorService.getAuthorByEmail(email));
    }

    @GetMapping
    public ResponseEntity<?> GetAllAuthor(){ 
        return ResponseEntity.status(OK)
                .body(authorService.getAllAuthor());
    }

    @PutMapping
    public ResponseEntity<?> updateAuthor(@ModelAttribute UpdateAuthorRequest updateAuthorRequest){ 
        return ResponseEntity.status(OK)
                .body(authorService.updateAuthor(updateAuthorRequest));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAuthor(@ModelAttribute UpdateAuthorRequest updateAuthorRequest){ 
        authorService.updateAuthor(updateAuthorRequest);
        return ResponseEntity.status(OK)
                .body("Author deleted successfully");
    }
    
    
}
