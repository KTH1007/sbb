package com.example.sbb.global.handler;

import com.example.sbb.global.dto.ErrorMessageDto;
import com.example.sbb.global.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(ex.getMessage());
        return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
    }
}
