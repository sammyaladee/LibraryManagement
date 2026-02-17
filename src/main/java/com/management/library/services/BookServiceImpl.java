package com.management.library.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.management.library.data.models.Book;
import com.management.library.data.repositories.BookRepository;
import com.management.library.dtos.requests.CreateBookRequest;
import com.management.library.dtos.requests.UpdateBookRequest;
import com.management.library.dtos.responses.BookResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
   public BookResponse createBook(CreateBookRequest request){
        if(request.getTitle() == null || request.getTitle().isBlank()){
            throw new IllegalArgumentException("Book title cannot be null or blank");
        }
        if(request.getIsbn() == null || request.getIsbn().isBlank()){
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        if(request.getPublicationYear() == null || request.getPublicationYear().isBlank()){
            throw new IllegalArgumentException("Publication year cannot be null or blank");
        }
        return modelMapper.map(bookRepository.save
            (modelMapper.map(request, Book.class)), 
            BookResponse.class);

   }
    public BookResponse getBookById(Long id){
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookResponse.class))
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    
    public List<BookResponse> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }
    public BookResponse updateBook(UpdateBookRequest request){
        Book book = bookRepository.findByIsbn(request.getIsbn())
                    .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + request.getIsbn()));
        if(request.getTitle() != null && !request.getTitle().isBlank()){
            book.setTitle(request.getTitle());
        }
        if(request.getPublicationYear() != null && !request.getPublicationYear().isBlank()){
            book.setPublicationYear(request.getPublicationYear());
        }
        return modelMapper.map(bookRepository.save(book), BookResponse.class);

    }
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}
