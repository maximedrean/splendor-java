package com.splendor.exceptions;


/**
 * Exception class to represent errors related to invalid columns 
 * in the Splendor game application. Extends ActionException.
 */
public class InvalidColumnException extends ActionException {

    /**
     * Constructs a new InvalidColumnException with no detail message.
     */
    public InvalidColumnException() {
        super();
    }

    /**
     * Constructs a new InvalidColumnException with the specified 
     * detail message.
     *
     * @param message The detail message.
     */
    public InvalidColumnException(String message) {
        super(message);
    }
}
