package com.gigacal.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(final String msg) {
        super(msg);
    }
}
