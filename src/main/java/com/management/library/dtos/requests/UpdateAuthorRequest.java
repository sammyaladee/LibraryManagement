package com.management.library.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateAuthorRequest{
    private String name;
    private String email;
    private String password;
}