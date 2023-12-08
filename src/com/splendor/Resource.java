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
    RUBY;

    /**
     * Returns a string representation of the resource, including its name and
     * the associated figure.
     *
     * @return The string representation of the resource.
     */
    public String toString() {
        switch (this) {
            case EMERALD:
                // EMERAUDE ♣
                return "EMERAUDE \u2663";
            case DIAMOND:
                // DIAMANT ♦
                return "DIAMANT \u2666";
            case SAPPHIRE:
                // SAPHIR ♠
                return "SAPHIR \u2660";
            case ONYX:
                // ONYX ●
                return "ONYX \u25CF";
            case RUBY:
                // RUBIS ♥
                return "RUBIS \u2665";
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
                // ♣E
                return "\u2663E";
            case DIAMOND:
                // ♦D
                return "\u2666D";
            case SAPPHIRE:
                // ♠S
                return "\u2660S";
            case ONYX:
                // ●O
                return "\u25CFO";
            case RUBY:
                // ♥R
                return "\u2665R";
            default:
                return "";
        }
    }
}
