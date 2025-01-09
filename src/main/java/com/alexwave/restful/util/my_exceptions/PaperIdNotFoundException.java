package com.alexwave.restful.util.my_exceptions;

import org.springframework.http.HttpStatus;

public class PaperIdNotFoundException extends GlobalException {
    static final String DEFAULT_MESSAGE = "Paper with this ID not found";

    public PaperIdNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
