package com.splendor.display;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


/**
 * {@code CursorActionFilter} is a custom {@code DocumentFilter} that 
 * restricts text modifications within a document based on a specified 
 * start position. It allows control over insertion, removal, and 
 * replacement of text within the document, enforcing a minimum offset 
 * for modifications.
 *
 * The start position is determined by the constant {@code START_POSITION},
 * and any modification attempted before this position will be ignored.
 *
 * This class is intended to be used in conjunction with Swing text 
 * components, providing a convenient way to control user interactions 
 * with the text content.
 *
 * <pre>
 * CursorActionFilter cursorFilter = new CursorActionFilter();
 * someDocument.setDocumentFilter(cursorFilter);
 * </pre>
 * </p>
 */
public class CursorActionFilter extends DocumentFilter {

    private final int START_POSITION = 2;

    /**
     * Inserts the specified string into the document, taking into account
     * the start position restriction.
     *
     * @param filter a {@code FilterBypass} that can be used to mutate 
     *        the document.
     * @param offset the offset at which the string should be inserted.
     * @param string the string to be inserted.
     * @param attribute the set of attributes for the inserted content.
     * @throws BadLocationException if the insertion results in an 
     *         invalid location.
     */
    @Override
    public void insertString(
            FilterBypass filter, int offset, String string, 
            AttributeSet attribute) throws BadLocationException {
        if (offset < START_POSITION) return;
        super.insertString(filter, offset, string, attribute);
    }

    /**
     * Removes the specified amount of content from the document, 
     * taking into account the start position restriction.
     *
     * @param filter a {@code FilterBypass} that can be used to 
     *        mutate the document.
     * @param offset the offset from which to remove content.
     * @param length the number of characters to remove.
     * @throws BadLocationException if the removal results in 
     *         an invalid location.
     */
    @Override
    public void remove(FilterBypass filter, int offset, int length)
            throws BadLocationException {
        if (offset < START_POSITION) return;
        super.remove(filter, offset, length);
    }

    /**
     * Replaces text within the document, taking into account the 
     * start position restriction.
     *
     * @param filter a {@code FilterBypass} that can be used to 
     *        mutate the document
     * @param offset the offset at which to start replacing
     * @param length the number of characters to replace
     * @param text the replacement text
     * @param attribute the set of attributes for the replacement content
     * @throws BadLocationException if the replacement results in an 
     *         invalid location
     */
    @Override
    public void replace(
            FilterBypass filter, int offset, int length, String text,
            AttributeSet attribute) throws BadLocationException {
        if (offset < START_POSITION) return;    
        super.replace(filter, offset, length, text, attribute);
    }
}
