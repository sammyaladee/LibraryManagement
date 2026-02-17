package com.management.library.services;

import com.management.library.dtos.requests.CreateAuthorRequest;
import com.management.library.dtos.responses.AuthorResponse;
import com.management.library.dtos.requests.UpdateAuthorRequest;
import java.util.List;

public interface AuthorServices {

    AuthorResponse createAuthor(CreateAuthorRequest request);
    AuthorResponse getAuthorById(Long id);
    AuthorResponse getAuthorByEmail(String email);
    List<AuthorResponse> getAllAuthor();
    AuthorResponse updateAuthor(UpdateAuthorRequest request);
}