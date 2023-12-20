package com.splendor.exceptions;

/**
 * Exception class to represent errors related to invalid tiers in the Splendor
 * game application.
 * Extends ActionException.
 */
public class InvalidTierException extends ActionException {

    /**
     * Constructs a new InvalidTierException with no detail message.
     */
    public InvalidTierException() {
        super();
    }

    /**
     * Constructs a new InvalidTierException with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidTierException(String message) {
        super(message);
    }
}
