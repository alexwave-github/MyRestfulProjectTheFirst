package com.alexwave.restful.util.exception_handling;

public abstract class GlobalParentException extends RuntimeException {


    public GlobalParentException(String message) {
        super(message);
    }

}
