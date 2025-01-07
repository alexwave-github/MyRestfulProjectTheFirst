package com.alexwave.restful.util.my_exceptions;

import org.springframework.http.HttpStatus;

public class AuthorIdNotFoundException extends GlobalException {
    static final String DEFAULT_MESSAGE = "Author's ID not found";

    public AuthorIdNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
