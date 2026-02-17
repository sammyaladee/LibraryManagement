package com.management.library.dtos.responses;

import com.management.library.data.models.Author;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private String publicationYear;
    private Author author;
}
