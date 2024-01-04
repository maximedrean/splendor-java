package com.splendor.board;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Stack;

import com.splendor.Resources;
import com.splendor.cards.CardReader;
import com.splendor.cards.DevCard;
import com.splendor.cards.Noble;
import com.splendor.constants.Cards;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Symbols;
import com.splendor.constants.Values;
import com.splendor.display.Display;
import com.splendor.display.Displayable;
import com.splendor.exceptions.CardReaderException;


/**
 * The `Board` class represents the game board in Splendor and implements the
 * `Displayable` interface. It manages resources, development cards, and 
 * provides methods for interacting with the game state.
 */
public class Board implements Displayable {

    /**
     * The resources available on the board.
     */
    private Resources resources = new Resources();

    /**
     * The stack of Noble / development cards for each level.
     */
    @SuppressWarnings("unchecked")
    private Stack<DevCard>[] cards = new Stack[Values.TIER_NUMBER + 1];

    /**
     * The visible development / Noble cards on the board.
     */
    private DevCard[][] visibleCards;

    /**
     * Constructs a new Board by initializing it with development 
     * and noble cards.
     *
     * @throws CardReaderException If there is an issue reading 
     *         cards from the CardReader.
     */
    public Board() throws CardReaderException {
        CardReader cardReader = new CardReader();
        this.cards = cardReader.getDevCards();
        this.cards[0] = cardReader.getNobleCards();
        int length = this.cards.length; // Dimension of the board.
        this.visibleCards = new DevCard[length][length];
        this.initializeBoard(length);
    }

    /**
     * Initializes the board by populating the visible cards array.
     *
     * @param length The dimension of the board.
     */
    private void initializeBoard(int length) {
        for (int row = 0; row < length; row++)
            for (int column = 0; column < length; column++)
                this.updateCard(this.getCard(row, column), row, column);
    }

    /**
     * Retrieves the cards currently visible in the game.
     *
     * @return An array containing the visible cards.
     */
    public DevCard[] getVisibleCards() {
        return Arrays.stream(this.visibleCards)
            .flatMap(Arrays::stream).toArray(DevCard[]::new);
    }

    /**
     * Retrieves the noble cards currently available in the game.
     *
     * @return An array containing the noble cards.
     */
    public Noble[] getNobles() {
        return Arrays.stream(this.visibleCards[0])
            .toArray(Noble[]::new);
    }

    /**
     * Removes the noble card at the specified index from the game.
     *
     * @param index The index of the noble card to be removed.
     * @throws ArrayIndexOutOfBoundsException If the specified 
     *         index is out of bounds.
     */
    public void removeNoble(int index) {
        this.getNobles()[index] = null;
    }

    /**
     * Retrieves the count of a specific resource on the board.
     *
     * @param resource The resource to check.
     * @return The count of the specified resource.
     */
    public int getNbResource(Resource resource) {
        return this.resources.getNbResource(resource);
    }

    /**
     * Sets the count of a specific resource on the board.
     *
     * @param resource The resource to update.
     * @param number The new count for the specified resource.
     */
    public void setNbResource(Resource resource, int number) {
        this.resources.setNbResource(resource, number);
    }

    /**
     * Updates the count of a specific resource on the board.
     *
     * @param resource The resource to update.
     * @param number The value by which to update the count of 
     *        the specified resource.
     */
    public void updateNbResource(Resource resource, int number) {
        this.resources.updateNbResource(resource, number);
    }

    /**
     * Retrieves an array of available resources on the board.
     *
     * @return An array of available resources.
     */
    public Resource[] getAvailableResources() {
        return this.resources.getAvailableResources();
    }

    /**
     * Retrieves the development card located at the specified tier 
     * and column on the board.
     *
     * @param tier The tier (row) of the development card.
     * @param column The column of the development card.
     * @return The development card at the specified tier and column.
     * @throws ArrayIndexOutOfBoundsException If the specified tier 
     *         or column is out of bounds.
     */
    public DevCard getCard(int tier, int column) 
            throws ArrayIndexOutOfBoundsException {
        return this.visibleCards[tier][column];
    }

    /**
     * Updates the development card at the specified tier (row) and column 
     * on the board with the provided card.
     *
     * @param card The new development card to be placed on the board.
     * @param tier The tier (row) of the development card.
     * @param column The column of the development card.
     * @throws NullPointerException If the specified card is null and the
     *         corresponding stack at the specified tier is not empty.
     * @throws ArrayIndexOutOfBoundsException If the specified tier or 
     *         column is out of bounds.
     */
    public void updateCard(DevCard card, int tier, int column)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (card == null && this.cards[tier].isEmpty())
            throw new NullPointerException(Messages.SLOT_NOT_EMPTY);
        this.visibleCards[tier][column] = this.cards[tier].isEmpty() 
            ? null : this.cards[tier].pop();
    }

    /**
     * Draws a development card from the specified tier's stack.
     * If the stack for the specified tier is not empty, the top card is 
     * removed and returned. If the stack is empty, null is returned, 
     * indicating no card is available to draw.
     *
     * @param tier The tier (row) from which to draw a development card.
     * @return The development card drawn from the specified tier's stack, 
     *         or nul if the stack is empty.
     * @throws ArrayIndexOutOfBoundsException If the specified tier 
     *         value is out of bounds.
     */
    public DevCard drawCard(int tier) throws ArrayIndexOutOfBoundsException {
        if (0 <= tier && tier < this.cards.length) {
            Stack<DevCard> row = this.cards[tier];
            return row.isEmpty() ? null : row.pop();
        }
        throw new ArrayIndexOutOfBoundsException(Messages.INVALID_TIER);
    }

    /**
     * Checks if the count of the specified resource allows giving tokens.
     *
     * @param resource The resource for which to check the eligibility 
     *        to give tokens.
     * @return {@code true} if the count of the specified resource is equal 
     *         to or exceeds the minimum required for giving tokens, 
     *         {@code false} otherwise.
     */
    public boolean canGiveSameTokens(Resource resource) {
        return resources.getNbResource(resource) >= 
            Values.REQUIRED_RESOURCES;
    }

    /**
     * Checks if the counts of the specified resources allow giving 
     * different tokens.
     *
     * @param resourcesArray The array of resources to check for 
     *        eligibility to give tokens.
     * @return {@code true} if the count of each specified resource is at 
     *         least 1, indicating eligibility to give different tokens,
     *         {@code false} if the count of any specified resource is 
     *         less than 1.
     */
    public boolean canGiveDiffTokens(Resource[] resourcesArray) {
        for (Resource resource : resourcesArray) {
            final int resourceCount = resources.getNbResource(resource);
            if (resourceCount < Values.DIFF_TOKEN_NUMBER) return false;
        }
        return true;
    }

    /**
     * Converts information about the cards in the specified deck tier to a
     * formatted string array. The resulting array represents a visual preview 
     * of the deck, including the number of remaining cards, pluralization of 
     * "card(s)", and the tier number.
     *
     * @param tier The tier (row) of the deck for which to create the 
     *        formatted string array.
     * @return An array of strings representing the formatted preview of the
     *         specified deck tier. Each element in the array corresponds to 
     *         a line in the visual preview.
     */
    public String[] deckToStringArray(int tier) {
        final int remainingCards = this.cards[tier].size();
        final String cards = String.format("%02d", remainingCards);
        final String plural = remainingCards > 1 ? "s" : "";
        String[] deck = tier > 0 ? Cards.DECK_PREVIEW : Cards.NOBLE_PREVIEW;
        String preview = String.join("\n", deck);
        preview = MessageFormat.format(preview, cards, plural, tier);
        return preview.split("\n");
    }

    /**
     * Converts information about the available resources to a formatted 
     * string array. The resulting array represents a visual preview of the
     * available resources, including their counts and symbols.
     *
     * @return An array of strings representing the formatted preview of the
     *         available resources. The first element in the array provides a 
     *         label, and subsequent elements detail the counts and symbols of 
     *         each resource.
     */
    private String[] resourcesToStringArray() {
        final String[] resourceArrayString = { Messages.AVAILABLE_RESOURCES };
        for (Resource resource : this.resources.keySet()) {
            final int resourceCount = this.resources.getNbResource(resource);
            final String resourceSymbol = resource.toSymbol();
            resourceArrayString[0] += resourceCount + resourceSymbol + " ";
        }
        return resourceArrayString;
    }

    /**
     * Converts the current state of the game board to a formatted string 
     * array. The resulting array represents a visual preview of the game 
     * board, including the deck, visible cards, and available resources.
     *
     * @return An array of strings representing the formatted preview of the 
     *         game board. Each element in the array corresponds to a line in
     *         the visual preview.
     */
    private String[] boardToStringArray() {
        String[] resource = Display.emptyStringArray(0, 0);
        String[] deck = Display.emptyStringArray(0, 0);
        // First of all, add the Noble deck at the left of the board.
        final String[] noble = this.deckToStringArray(0);
        deck = Display.concatStringArray(deck, noble, true);
        // Then, add the other stacks below, at the left of the board.
        for (int index = this.cards.length - 1; index > 0; index--) {
            final String[] stack = this.deckToStringArray(index);
            deck = Display.concatStringArray(deck, stack, true);
        }
        String[] card = Display.emptyStringArray(0, 0);
        for (int row = 0; row < this.visibleCards.length; row++) {
            String[] tierCards = Display.emptyStringArray(8, 0);
            final int limit = this.visibleCards[row].length;
            for (int column = 0; column < limit; column++) {
                // Retrieve the card from the visible cards and display it.
                final DevCard current = this.visibleCards[row][column];
                String[] displayed = current != null  // Default: empty deck.
                    ? current.toStringArray() : Cards.EMPTY_DECK_PREVIEW;
                tierCards = Display.concatStringArray(tierCards, displayed);
            }
            final String[] emptySlot = Display.emptyStringArray(1, 40);
            card = Display.concatStringArray(card, emptySlot, true);
            card = Display.concatStringArray(card, tierCards, true);
        }
        // Add a vertical empty space to separate the board from players.
        String[] empty = Display.emptyStringArray(1, 52);
        String[] resourcePreview = this.resourcesToStringArray();
        String[] vertical = Display.emptyStringArray(
            35, 1, Symbols.VERTICAL_DELIMITER);
        String[] horizontal = Display.emptyStringArray(
            1, 54, Symbols.DELIMITER);
        // Combine all the string arrays and return them for display.
        resource = Display.concatStringArray(deck, card);
        resource = Display.concatStringArray(resource, empty, true);
        resource = Display.concatStringArray(resource, resourcePreview, true);
        resource = Display.concatStringArray(resource, vertical, false);
        return Display.concatStringArray(resource, horizontal, true);
    }

    /**
     * Overrides the default `toStringArray` method to provide a 
     * formatted string array representation of the game state.
     *
     * @return An array of strings representing the formatted preview of 
     *         the game board. Each element in the array corresponds to a 
     *         line in the visual preview.
     */
    @Override
    public String[] toStringArray() {
        return this.boardToStringArray();
    }
}
