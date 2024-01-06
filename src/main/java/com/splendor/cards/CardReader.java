package com.splendor.cards;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.splendor.board.Resources;
import com.splendor.constants.Messages;
import com.splendor.constants.Project;
import com.splendor.constants.Resource;
import com.splendor.exceptions.CardReaderException;


/**
 * The CardReader class is responsible for reading and extracting development
 * card data from a CSV file. It provides methods to access the list of 
 * DevCard objects.
 */
public class CardReader {

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
    private final HashMap<Integer, ArrayList<DevCard>> devCards;
    private final ArrayList<Noble> nobleCards;

    /**
     * Constructs a CardReader object and initializes the list of DevCard
     * objects by extracting data from the CSV file.
     *
     * @throws CardReaderException If an error occurs during the file 
     *         reading or card extraction process.
     */
    public CardReader() throws CardReaderException {
        this.devCards = new HashMap<Integer, ArrayList<DevCard>>();
        this.nobleCards = new ArrayList<Noble>();
        this.extractCards();
    }

    /**
     * Gets the list of DevCard objects.
     *
     * @return The list of DevCard objects.
     */
    @SuppressWarnings("unchecked")
    public Stack<DevCard>[] getDevCards() {
        Stack<DevCard>[] stackArray = new Stack[this.devCards.size()];
        this.devCards.forEach((key, list) -> {
            Stack<DevCard> stack = new Stack<DevCard>();
            list.stream().filter(DevCard.class::isInstance)
                .map(DevCard.class::cast).forEach(stack::push);
            stackArray[key - 1] = stack;
        });
        return stackArray;
    }

    /**
     * Gets the list of (playersCount + 1) Noble cards.
     * 
     * @see https://www.regledujeu.fr/splendor/ (I.b).
     * @return The list of Noble cards.
     */
    public Noble[] getNobleCards(int playersCount) {
        return this.nobleCards.stream()
            .distinct().limit(playersCount).toArray(Noble[]::new);
    }

    /**
     * Reads the content of the CSV file containing development card statistics.
     *
     * @return A list of strings representing the lines of the CSV file.
     * @throws CardReaderException If the file is not found or cannot be read.
     */
    private List<String> readCardsFile() throws CardReaderException {
        final File file = new File(Project.CARD_STATISTIC_FILE);
        if (!file.exists() || !file.canRead()) 
            throw new CardReaderException(Messages.CARD_READER_ERROR);
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
     *         extraction process.
     */
    private void extractCards() throws CardReaderException {
        final List<String> rawCards = this.readCardsFile();
        Collections.shuffle(rawCards);  // Randomly shuffle cards.
        rawCards.forEach(card -> {
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

            if (type != null) {
                DevCard devCard = new DevCard(tier, resources, points, type);
                devCards.computeIfAbsent(tier, t -> new ArrayList<DevCard>());
                devCards.get(tier).add(devCard);
            } else nobleCards.add(new Noble(tier, resources, points));
        });
    }
}
