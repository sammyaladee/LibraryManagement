package com.management.library.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookRequest {
    private String title;
    private String isbn;
    private String publicationYear;
}
