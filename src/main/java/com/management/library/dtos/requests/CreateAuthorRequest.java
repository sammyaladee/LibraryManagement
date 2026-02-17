package com.management.library.dtos.requests;

import lombok.Data;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Data
public class CreateAuthorRequest{
    private String name;
    private String email;
    private String password;
}