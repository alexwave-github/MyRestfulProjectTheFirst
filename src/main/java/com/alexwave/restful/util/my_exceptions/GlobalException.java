package com.alexwave.restful.util.my_exceptions;

import org.springframework.http.HttpStatus;

public abstract class GlobalException extends RuntimeException {

    public GlobalException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
