package com.alexwave.restful.util.exception_handling;

import org.springframework.http.HttpStatus;

public class AuthorIdNotFoundException extends GlobalParentException{
    static final String DEFAULT_MESSAGE = "Author's ID not found";

    public AuthorIdNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
