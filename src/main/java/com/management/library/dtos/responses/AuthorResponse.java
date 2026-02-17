package com.management.library.dtos.responses;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AuthorResponse{
    private Long id;
    private String name;
    private String email;

}