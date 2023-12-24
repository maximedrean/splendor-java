package com.splendor.cards;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.splendor.DevCard;
import com.splendor.Noble;
import com.splendor.Resource;
import com.splendor.Resources;
import com.splendor.exceptions.CardReaderException;


/**
 * The CardReader class is responsible for reading and extracting development
 * card data from a CSV file. It provides methods to access the list of 
 * DevCard objects.
 */
public class CardReader {

    /**
     * The file path of the CSV file containing development card statistics.
     */
    private static final String CARD_FILE = "./resources/stats.csv";

    /**
     * Index constants for different attributes in the CSV file.
     */
    private static final int TIER_INDEX = 0;
    private static final int DIAMOND_INDEX = 1;
    private static final int SAPPHIRE_INDEX = 2;
    private static final int EMERALD_INDEX = 3;
    private static final int RUBY_INDEX = 4;
    private static final int ONYX_INDEX = 5;
    private static final int POINTS_INDEX = 6;
    private static final int TYPE_INDEX = 7;


    /**
     * ArrayList containing DevCard objects after extraction from the CSV file.
     */
    private final ArrayList<DevCard> cards;

    /**
     * Constructs a CardReader object and initializes the list of DevCard
     * objects by extracting data from the CSV file.
     *
     * @throws CardReaderException If an error occurs during the file reading 
     *                             or card extraction process.
     */
    public CardReader() throws CardReaderException {
        this.cards = this.extractCards();
    }

    /**
     * Gets the list of DevCard objects.
     *
     * @return The list of DevCard objects.
     */
    public ArrayList<DevCard> getCards() {
        return this.cards;
    }

    /**
     * Reads the content of the CSV file containing development card statistics.
     *
     * @return A list of strings representing the lines of the CSV file.
     * @throws CardReaderException If the file is not found or cannot be read.
     */
    private List<String> readCardsFile() throws CardReaderException {
        final File file = new File(CARD_FILE);
        if (!file.exists() || !file.canRead()) throw new CardReaderException();
        try {
            final Charset charset = Charset.defaultCharset();
            final Path filePath = file.toPath();
            final List<String> fileContent = Files.readAllLines(filePath, charset);
            return fileContent.subList(1, fileContent.size() - 1);
        } catch (IOException exception) {
            throw new CardReaderException(exception.getMessage());
        }
    }

    /**
     * Extracts DevCard objects from the raw data obtained from the CSV file.
     *
     * @return An ArrayList of DevCard objects.
     * @throws CardReaderException If an error occurs during the card 
     *                             extraction process.
     */
    private ArrayList<DevCard> extractCards() throws CardReaderException {
        final List<String> rawCards = this.readCardsFile();
        return rawCards.stream().map(card -> {
            final String[] values = card.split(",");
            final int tier = Integer.parseInt(values[TIER_INDEX]);
            final int diamondCost = Integer.parseInt(values[DIAMOND_INDEX]);
            final int sapphireCost = Integer.parseInt(values[SAPPHIRE_INDEX]);
            final int emeraldCost = Integer.parseInt(values[EMERALD_INDEX]);
            final int rubyCost = Integer.parseInt(values[RUBY_INDEX]);
            final int onyxCost = Integer.parseInt(values[ONYX_INDEX]);
            final int points = Integer.parseInt(values[POINTS_INDEX]);

            Resource type = null;  // Default state (Noble card).
            try {
                type = Resource.valueOf(values[TYPE_INDEX]);
            } catch (IllegalArgumentException exception) {}

            final Resources resources = new Resources();
            resources.setNbResource(Resource.DIAMOND, diamondCost);
            resources.setNbResource(Resource.SAPPHIRE, sapphireCost);
            resources.setNbResource(Resource.EMERALD, emeraldCost);
            resources.setNbResource(Resource.RUBY, rubyCost);
            resources.setNbResource(Resource.ONYX, onyxCost);

            return type == null
                ? new DevCard(tier, resources, points, type)
                : new Noble(resources, points);
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
