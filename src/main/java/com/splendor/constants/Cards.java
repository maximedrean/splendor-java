package com.splendor.constants;


/**
 * Constants for defining templates for deck and development 
 * card previews.
 */
public final class Cards {

    /**
     * Deck preview template.
     */
    public static final String[] DECK_PREVIEW = {
        "\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  ",
        "\u2502        \u2502\u2572 ",
        "\u2502 reste: \u2502 \u2502 ",
        "\u2502   {0}   \u2502 \u2502 ",
        "\u2502 carte{1} \u2502 \u2502 ",
        "\u2502 tier {2} \u2502 \u2502 ",
        "\u2502        \u2502 \u2502 ",
        "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 \u2502 ",
        " \u2572________\u2572\u2502 "
    };

    /**
     * Empty deck preview template.
     */
    public static final String[] EMPTY_DECK_PREVIEW = {
        "\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510 ",
        "\u2502 \\    / \u2502 ",
        "\u2502  \\  /  \u2502 ",
        "\u2502   \\/   \u2502 ",
        "\u2502   /\\   \u2502 ",
        "\u2502  /  \\  \u2502 ",
        "\u2502 /    \\ \u2502 ",
        "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 "
    };

    /**
     * Development card preview template.
     */
    public static final String[] DEV_CARD_PREVIEW = {
        "\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510 ",
        "\u2502{0}    {1}\u2502 ",
        "\u2502        \u2502 ",
        "\u2502{8} {9}    \u2502 ",
        "\u2502{6} {7}    \u2502 ",
        "\u2502{4} {5}    \u2502 ",
        "\u2502{2} {3}    \u2502 ",
        "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 ",
    };
}
