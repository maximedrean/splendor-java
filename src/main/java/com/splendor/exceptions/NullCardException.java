package com.splendor.exceptions;


/**
 * Exception class to represent errors related to null cards in the Splendor
 * game application. Extends ActionException.
 */
public class NullCardException extends ActionException {

    /**
     * Constructs a new NullCardException with no detail message.
     */
    public NullCardException() {
        super();
    }

    /**
     * Constructs a new NullCardException with the specified detail message.
     *
     * @param message The detail message.
     */
    public NullCardException(String message) {
        super(message);
    }
}
