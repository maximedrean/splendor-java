package com.splendor.exceptions;

/**
 * Exception class to represent errors related to insufficient resources in the
 * Splendor game application.
 * Extends ActionException.
 */
public class NotEnoughResourcesException extends ActionException {

    /**
     * Constructs a new NotEnoughResourcesException with no detail message.
     */
    public NotEnoughResourcesException() {
        super();
    }

    /**
     * Constructs a new NotEnoughResourcesException with the specified detail
     * message.
     *
     * @param message The detail message.
     */
    public NotEnoughResourcesException(String message) {
        super(message);
    }
}
