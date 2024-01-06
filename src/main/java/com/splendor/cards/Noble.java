package com.splendor.cards;

import com.splendor.board.Resources;


/**
 * Represents a noble development card, a subclass of the {@code DevCard}.
 * Nobles are special development cards with specific characteristics.
 */
public class Noble extends DevCard {

    /**
     * Constructs a new instance of the {@code Noble} development card.
     *
     * @param level The level of the noble card.
     * @param cost The resource cost required to acquire the noble card.
     * @param points The victory points associated with the noble card.
     */
    public Noble(int level, Resources cost, int points) {
        super(level, cost, points, null);
    }
}
