package com.alexwave.restful.util.my_exceptions;

import org.springframework.http.HttpStatus;

public class AuthorListIsEmptyException extends GlobalException {
    static final String DEFAULT_MESSAGE = "Author's list does not exist yet";

    public AuthorListIsEmptyException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
