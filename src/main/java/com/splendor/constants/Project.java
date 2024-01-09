package com.splendor.constants;

import java.nio.file.Paths;


public final class Project {

    /**
     * The file path of the CSV file containing development card statistics.
     */
    public static final String CARD_STATISTIC_FILE =
        Paths.get("..", "resources", "stats.csv").toString(); // BlueJ.
        // Paths.get("build", "resources", "main", "stats.csv").toString(); // Gradle.

    public static final String DISPLAY_FONT_FILE =
        Paths.get("..", "resources", "unifont.otf").toString(); // BlueJ.
        // Paths.get("build", "resources", "main", "unifont.otf").toString(); // Gradle.
}
