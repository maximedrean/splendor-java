package com.splendor.display;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import com.splendor.constants.Project;


/**
 * Utility class for loading a custom font for display purposes.
 */
public final class FontLoader {

    /**
     * Loads a custom font specified by the DISPLAY_FONT_FILE path.
     *
     * @return The loaded font or a default monospaced font if loading fails.
     */
    public final static Font load() {
        try {
            File fontFile = new File(Project.DISPLAY_FONT_FILE);
            final Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return font.deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException exception) {
            // Print the exception details for debugging purposes.
            exception.printStackTrace();
            // Return a default monospaced font if loading fails.
            return new Font(Font.MONOSPACED, Font.PLAIN, 14);
        }
    }
}
