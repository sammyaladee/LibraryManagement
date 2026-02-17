package com.management.library.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.management.library.dtos.responses.ApiResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleError(RuntimeException exception){

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException exception){

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(exception.getMessage(), false));
    }
}
