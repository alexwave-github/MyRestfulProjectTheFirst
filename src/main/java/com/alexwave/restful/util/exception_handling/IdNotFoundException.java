package com.alexwave.restful.util.exception_handling;

import org.springframework.http.HttpStatus;

public class IdNotFoundException extends GlobalParentException{

    public IdNotFoundException(String message) {
        super(message);
    }

    @Override
    HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
