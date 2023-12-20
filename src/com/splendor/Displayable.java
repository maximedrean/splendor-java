package com.splendor;

/**
 * The Displayable interface is implemented by classes that can be displayed
 * in the console.
 */
public interface Displayable {

    /**
     * Returns an array of strings representing the formatted preview of the
     * object. Each element in the array corresponds to a line in the visual
     * preview.
     *
     * @return An array of strings representing the formatted preview.
     */
    public String[] toStringArray();
}
