package com.splendor.exceptions;


/**
 * Exception thrown to indicate that an invalid number has been 
 * encountered during a game action.
 * This exception is a subclass of {@code ActionException} and can be 
 * used when there is an issue related to an invalid number in the game logic.
 */
public class InvalidNumberException extends ActionException {

    /**
     * Constructs an {@code InvalidNumberException} with no specified 
     * detail message.
     */
    public InvalidNumberException() {
        super();
    }

    /**
     * Constructs an {@code InvalidNumberException} with the specified 
     * detail message.
     *
     * @param message The detail message (which is saved for later 
     *        retrieval by the {@link #getMessage()} method).
     */
    public InvalidNumberException(String message) {
        super(message);
    }
}
