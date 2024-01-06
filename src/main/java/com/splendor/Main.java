package com.splendor;

import com.splendor.constants.Messages;
import com.splendor.constants.Utility;
import com.splendor.exceptions.CardReaderException;


public class Main {

    /**
     * Entry point for the Splendor game application.
     * Initializes the game, displays a welcome message, and starts the 
     * gameplay loop. Finally, closes the display after the game is over.
     *
     * @param args Command line arguments (mandatory but not used here).
     */
    public static void main(String[] args) throws CardReaderException {
        Utility.display.outBoard.println(Messages.WELCOME);
        new Game(3, false).play();
        Utility.display.close();
    }
}
