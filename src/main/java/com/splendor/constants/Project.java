package com.splendor.constants;

import java.nio.file.Paths;


public final class Project {

    /**
     * The file path of the CSV file containing development card statistics.
     */
    public static final String CARD_STATISTIC_FILE =
        Paths.get("build", "resources", "main", "stats.csv").toString();

    public static final String DISPLAY_FONT_FILE =
        Paths.get("build", "resources", "main", "unifont.otf").toString();
}
