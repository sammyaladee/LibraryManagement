package com.management.library.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteAuthorRequest {
    private String name;
    private String email;
    private String password;
}
