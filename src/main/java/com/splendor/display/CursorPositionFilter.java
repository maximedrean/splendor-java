package com.splendor.display;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position.Bias;
import javax.swing.SwingConstants;


/**
 * {@code CursorPositionFilter} is a custom {@code NavigationFilter} that
 * restricts the movement of the cursor within a {@code JTextComponent} based
 * on a specified start position. It allows control over navigation to the
 * left (WEST) direction, preventing movement to the left if the cursor is
 * before the start position.
 *
 * The start position is determined by the constant {@code START_POSITION},
 * and any attempt to navigate to the left before this position will be
 * restricted.
 *
 * This class is intended to be used in conjunction with Swing text 
 * components, providing a convenient way to control cursor navigation 
 * behavior.
 *
 * <pre>
 * CursorPositionFilter cursorFilter = new CursorPositionFilter();
 * someTextComponent.setNavigationFilter(cursorFilter);
 * </pre>
 */
public class CursorPositionFilter extends NavigationFilter {
    
    /**
     * The start position for allowed cursor navigation.
     */
    private final int START_POSITION = 2;

    /**
     * Returns the next visual position for cursor navigation within the
     * text component, taking into account the start position restriction.
     *
     * @param component the text component.
     * @param position the current cursor position.
     * @param bias the bias for the navigation.
     * @param direction the direction of navigation.
     * @param newBias an array to store the bias after navigation.
     * @return the next visual position for cursor navigation.
     * @throws BadLocationException if an invalid location is encountered.
     */
    @Override
    public int getNextVisualPositionFrom(
            JTextComponent component, int position, Bias bias, 
            int direction, Bias[] newBias) throws BadLocationException {
        final boolean notWest = direction != SwingConstants.WEST;
        final boolean toRight = position > this.START_POSITION;
        if (!notWest && !toRight) return position;
        return super.getNextVisualPositionFrom(
            component, position, bias, direction, newBias);
    }
}
