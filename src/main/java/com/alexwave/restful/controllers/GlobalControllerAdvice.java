package com.alexwave.restful.controllers;

import com.alexwave.restful.util.my_exceptions.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handleException(GlobalException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }
}
