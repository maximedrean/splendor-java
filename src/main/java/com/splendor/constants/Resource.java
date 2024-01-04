package com.splendor.constants;


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
     * Returns a string representation of the resource, including its 
     * name and the associated figure.
     *
     * @return The string representation of the resource.
     */
    public String toString() {
        switch (this) {
            case EMERALD:
                return "ÉMERAUDE " + Symbols.EMERALD;
            case DIAMOND:
                return "DIAMANT " + Symbols.DIAMOND;
            case SAPPHIRE:
                return "SAPHIR " + Symbols.SAPPHIRE;
            case ONYX:
                return "ONYX " + Symbols.ONYX;
            case RUBY:
                return "RUBIS " + Symbols.RUBY;
            case JOKER:
                return "JOKER " + Symbols.JOKER;
            default:
                return "";
        }
    }

    /**
     * Returns a symbol representation of the resource, including its 
     * shortname and the associated figure.
     *
     * @return The symbol representation of the resource.
     */
    public String toSymbol() {
        switch (this) {
            case EMERALD:
                return Symbols.EMERALD;
            case DIAMOND:
                return Symbols.DIAMOND;
            case SAPPHIRE:
                return Symbols.SAPPHIRE;
            case ONYX:
                return Symbols.ONYX;
            case RUBY:
                return Symbols.RUBY;
            case JOKER:
                return Symbols.JOKER;
            default:
                return "";
        }
    }
}
