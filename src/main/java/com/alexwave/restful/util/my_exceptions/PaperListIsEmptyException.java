package com.alexwave.restful.util.my_exceptions;

import org.springframework.http.HttpStatus;

public class PaperListIsEmptyException extends GlobalException {
    static final String DEFAULT_MESSAGE = "Paper's list does not exist yet";

    public PaperListIsEmptyException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
