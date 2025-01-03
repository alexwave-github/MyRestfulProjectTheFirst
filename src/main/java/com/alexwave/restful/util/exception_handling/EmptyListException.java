package com.alexwave.restful.util.exception_handling;

import org.springframework.http.HttpStatus;

public class EmptyListException extends GlobalParentException {

    public EmptyListException(String message) {
        super(message);
    }

    @Override
    HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
