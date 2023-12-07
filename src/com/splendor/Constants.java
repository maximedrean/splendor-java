package com.splendor;

public final class Constants {

    private Constants() {
    }

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

    public static final String[] DEV_CARD_PREVIEW = {
            "┌────────┐",
            "│{0}     {1}│",
            "│        │",
            "│{8} {9}     │",
            "│{6} {7}     │",
            "│{4} {5}     │",
            "│{2} {3}     │",
            "└────────┘",
    };
}
