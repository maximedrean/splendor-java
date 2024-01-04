package com.splendor.exceptions;


/**
 * Exception thrown to indicate an issue related to reading cards.
 * This exception is a subclass of {@code Exception} and can be used 
 * when there is a problem encountered while reading or processing 
 * cards in the application.
 */
public class CardReaderException extends Exception {

    /**
     * Constructs a {@code CardReaderException} with no specified 
     * detail message.
     */
    public CardReaderException() {
        super();
    }

    /**
     * Constructs a {@code CardReaderException} with the specified 
     * detail message.
     *
     * @param message The detail message (which is saved for later 
     *        retrieval by the {@link #getMessage()} method).
     */
    public CardReaderException(String message) {
        super(message);
    }
}
