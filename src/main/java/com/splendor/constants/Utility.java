package com.splendor.constants;

import com.splendor.display.Display;


/**
 * A utility class that holds constant values used throughout the 
 * Splendor game application.
 */
public final class Utility {

    /**
     * Display object for handling console output
     */
    public static final Display display = new Display(
        Values.ROWS_BOARD, 
        Values.ROWS_CONSOLE, 
        Values.COLUMNS_CONSOLE
    );
}
