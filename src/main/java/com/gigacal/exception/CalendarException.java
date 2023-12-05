package com.gigacal.exception;

public class CalendarException extends RuntimeException {

    public CalendarException(final String message){
        super(message);
    }

    public static class IncorrectDataProvided extends CalendarException {
        public IncorrectDataProvided(){
            super("incorrect data");
        }
    }
}
