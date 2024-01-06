package com.splendor.exceptions;


/**
 * Exception thrown to indicate that the maximum number of reserved cards 
 * has been reached during a game action.
 * This exception is a subclass of {@code ActionException} and can be 
 * used when there is an issue related to reaching the maximum number 
 * of reserved cards in the game logic.
 */
public class MaxReservedCardsException extends ActionException {

    /**
     * Constructs a {@code MaxReservedCardsException} with no specified 
     * detail message.
     */
    public MaxReservedCardsException() {
        super();
    }

    /**
     * Constructs a {@code MaxReservedCardsException} with the specified 
     * detail message.
     *
     * @param message The detail message (which is saved for later 
     *        retrieval by the {@link #getMessage()} method).
     */
    public MaxReservedCardsException(String message) {
        super(message);
    }
}
