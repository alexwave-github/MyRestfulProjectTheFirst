package com.alexwave.restful.util.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {
    ErrorResponse errorResponse = new ErrorResponse();
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(GlobalParentException exception) {
        errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getStatus());



////        if(exception instanceof IdNotFoundException || exception instanceof EmptyListException){
//            errorResponse = new ErrorResponse(exception.getMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
////        }



//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);


    }


}
