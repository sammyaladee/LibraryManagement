package com.management.library.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBookRequest {
    
    private String title;
    private String isbn;
    private String publicationYear;

}
