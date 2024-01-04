package com.splendor.exceptions;


/**
 * Exception class to represent errors related to invalid input 
 * in the Splendor game application. Extends ActionException.
 */
public class InvalidInputException extends ActionException {

    /**
     * Constructs a new InvalidInputException with no detail message.
     */
    public InvalidInputException() {
        super();
    }

    /**
     * Constructs a new InvalidInputException with the specified 
     * detail message.
     *
     * @param message The detail message.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
