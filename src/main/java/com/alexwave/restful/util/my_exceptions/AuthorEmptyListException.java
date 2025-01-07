package com.alexwave.restful.util.my_exceptions;

import org.springframework.http.HttpStatus;

public class AuthorEmptyListException extends GlobalException {

    public AuthorEmptyListException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
