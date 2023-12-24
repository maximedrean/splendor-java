package com.splendor.exceptions;


public class CardReaderException extends Exception {
    
    public CardReaderException() {
        super("Cannot read the card resources file.");
    }

    public CardReaderException(String error) {
        super(error);
    }
}
