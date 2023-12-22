package com.splendor;

/**
 * The Resource enum represents different types of resources in Splendor.
 * Each resource has a corresponding name, a string representation, and a
 * symbol representation.
 */
public enum Resource {
    /**
     * Represents the Diamond resource.
     */
    DIAMOND,

    /**
     * Represents the Sapphire resource.
     */
    SAPPHIRE,

    /**
     * Represents the Emerald resource.
     */
    EMERALD,

    /**
     * Represents the Onyx resource.
     */
    ONYX,

    /**
     * Represents the Ruby resource.
     */
    RUBY,

    /**
     * Represents the Joker resource.
     */
    JOKER;

    /**
     * Returns a string representation of the resource, including its name and
     * the associated figure.
     *
     * @return The string representation of the resource.
     */
    public String toString() {
        switch (this) {
            case EMERALD:
                return "EMERAUDE ♣";
            case DIAMOND:
                return "DIAMANT ♦";
            case SAPPHIRE:
                return "SAPHIR ♠";
            case ONYX:
                return "ONYX ●";
            case RUBY:
                return "RUBIS ♥";
            case JOKER:
                return "JOKER 🃟";
            default:
                return "";
        }
    }

    /**
     * Returns a symbol representation of the resource, including its shortname
     * and the associated figure.
     *
     * @return The symbol representation of the resource.
     */
    public String toSymbol() {
        switch (this) {
            case EMERALD:
                return "♣E";
            case DIAMOND:
                return "♦D";
            case SAPPHIRE:
                return "♠S";
            case ONYX:
                return "●O";
            case RUBY:
                return "♥R";
            case JOKER:
                return "🃟J";
            default:
                return "";
        }
    }
}
