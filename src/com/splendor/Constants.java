package com.splendor;

/**
 * A utility class that holds constant values used throughout the Splendor game
 * application.
 */
public final class Constants {

    /**
     * Maximum number of resources per development card
     */
    public static final int MAX_NUMBER_RESOURCES_PER_CARD = 4;

    /**
     * Maximum number of resources a player can have
     */
    public static final int MAX_NUMBER_RESOURCES_PER_PLAYER = 10;

    /**
     * Minimum number of resources required to give a token
     */
    public static final int MIN_NUMBER_RESOURCES_TO_GIVE_TOKEN = 4;

    /**
     * Points threshold to win the game
     */
    public static final int WIN_THRESHOLD = 15;

    /**
     * Board and console dimensions
     */
    public static final int ROWS_BOARD = 36;
    public static final int ROWS_CONSOLE = 8;
    public static final int COLS = 82;

    /**
     * Display object for handling console output
     */
    public static final Display display = new Display(ROWS_BOARD, ROWS_CONSOLE, COLS);

    /**
     * Deck preview template
     */
    public static final String[] DECK_PREVIEW = {
            "┌────────┐",
            "│        │╲",
            "│ reste: │ │",
            "│   {0}   │ │",
            "│ carte{1}  │ │",
            "│ tier {2} │ │",
            "│        │ │",
            "└────────┘ │",
            " ╲________╲│"
    };

    /**
     * Empty deck preview template
     */
    public static final String[] EMPTY_DECK_PREVIEW = {
            "┌────────┐",
            "│ \\    / │",
            "│  \\  /  │",
            "│   \\/   │",
            "│   /\\   │",
            "│  /  \\  │",
            "│ /    \\ │",
            "└────────┘",
    };

    /**
     * Development card preview template
     */
    public static final String[] DEV_CARD_PREVIEW = {
            "┌────────┐",
            "│{0}     {1}│",
            "│        │",
            "│{8} {9}    │",
            "│{6} {7}    │",
            "│{4} {5}    │",
            "│{2} {3}    │",
            "└────────┘",
    };
}
