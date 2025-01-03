package com.alexwave.restful.util.exception_handling;

import org.springframework.http.HttpStatus;

public abstract class GlobalParentException extends RuntimeException {

    public GlobalParentException(String message) {
        super(message);
    }

    HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
