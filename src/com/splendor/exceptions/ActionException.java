package com.splendor.exceptions;

/**
 * Exception class to represent errors related to game actions in the Splendor
 * game application.
 */
public class ActionException extends Exception {

    /**
     * Constructs a new ActionException with no detail message.
     */
    public ActionException() {
        super();
    }

    /**
     * Constructs a new ActionException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ActionException(String message) {
        super(message);
    }
}
