package com.splendor.display;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import com.splendor.constants.Project;


public final class FontLoader {

    public final static Font load() {
        try {
            File fontFile = new File(Project.DISPLAY_FONT_FILE);
            final Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return font.deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException exception) {
            exception.printStackTrace();
            return new Font(Font.MONOSPACED, Font.PLAIN, 14);
        }
    }
}
