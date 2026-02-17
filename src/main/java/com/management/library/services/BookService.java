package com.management.library.services;

import com.management.library.dtos.requests.CreateBookRequest;
import com.management.library.dtos.responses.BookResponse;
import com.management.library.dtos.requests.UpdateBookRequest;
import com.management.library.dtos.requests.DeleteBookRequest;

import java.util.List;

public interface BookService {

    BookResponse createBook(CreateBookRequest request);
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(UpdateBookRequest request);
    void deleteBook(DeleteBookRequest request);
}