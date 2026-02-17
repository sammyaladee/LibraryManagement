package com.management.library.services;

import com.management.library.dtos.requests.CreateAuthorRequest;
import com.management.library.dtos.requests.UpdateAuthorRequest;
import com.management.library.dtos.responses.AuthorResponse;

import lombok.AllArgsConstructor;

import com.management.library.data.models.Author;
import com.management.library.data.repositories.AuthorRepository;

import java.util.List;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorServices{

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private AuthorRepository authorRepository;

    private final ModelMapper modelMapper;


    public AuthorResponse createAuthor(CreateAuthorRequest request){
        if(request.getName() == null || request.getName().isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(!EMAIL_PATTERN.matcher(request.getEmail()).matches()){
            throw new IllegalArgumentException("Invalid email format");
        }
        
        Author author = modelMapper.map(request, Author.class);
        return modelMapper.map(authorRepository.save(author), AuthorResponse.class);
    }
    public AuthorResponse getAuthorById(Long id){
        return authorRepository.findById(id)
                .map(author -> modelMapper.map(author, AuthorResponse.class))
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }
    public AuthorResponse getAuthorByEmail(String email){
        return authorRepository.findAll().stream()
                .filter(author -> author.getEmail().equals(email))
                .findFirst()
                .map(author -> modelMapper.map(author, AuthorResponse.class))
                .orElseThrow(() -> new RuntimeException("Author not found with email: " + email));
    }
    public List<AuthorResponse> getAllAuthor(){
        return authorRepository.findAll().stream()
                .map(author -> modelMapper.map(author, AuthorResponse.class))
                .toList();
    }
    public AuthorResponse updateAuthor(UpdateAuthorRequest request){
        Author author = authorRepository.findByEmail(request.getEmail()
                ).orElseThrow(() -> new RuntimeException("Author not found with email: " + request.getEmail()));
        if(request.getName() != null && !request.getName().isBlank()){
            author.setName(request.getName());
        }
        return modelMapper.map(authorRepository.save(author), AuthorResponse.class);
    }
    
}
