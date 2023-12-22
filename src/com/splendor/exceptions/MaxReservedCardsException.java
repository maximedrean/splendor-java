package com.splendor.exceptions;

public class MaxReservedCardsException extends ActionException {
    public MaxReservedCardsException() {
        super();
    }

    public MaxReservedCardsException(String message) {
        super(message);
    }
}
