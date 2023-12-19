package com.gigacal.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(final String msg) {
        super(msg);
    }
}
