package com.splendor;

import java.text.MessageFormat;
import java.util.Stack;

/**
 * The `Board` class represents the game board in Splendor and implements the
 * `Displayable` interface.
 * It manages resources, development cards, and provides methods for interacting
 * with the game state.
 */
public class Board implements Displayable {

    /**
     * The resources available on the board.
     */
    private Resources resources = new Resources();

    /**
     * The stack of development cards for each level.
     */
    private Stack<DevCard>[] stackCards;

    /**
     * The development cards visible on the board.
     */
    private DevCard[][] visibleCards;

    /**
     * Retrieves the count of a specific resource on the board.
     *
     * @param resource The resource to check.
     * @return The count of the specified resource.
     */
    public int getNbResource(Resource resource) {
        return resources.getNbResource(resource);
    }

    /**
     * Sets the count of a specific resource on the board.
     *
     * @param resource The resource to update.
     * @param nb       The new count for the specified resource.
     */
    public void setNbResource(Resource resource, int nb) {
        resources.setNbResource(resource, nb);
    }

    /**
     * Updates the count of a specific resource on the board.
     *
     * @param resource The resource to update.
     * @param nb       The value by which to update the count of the specified
     *                 resource.
     */
    public void updateNbResource(Resource resource, int nb) {
        resources.updateNbResource(resource, nb);
    }

    /**
     * Retrieves an array of available resources on the board.
     *
     * @return An array of available resources.
     */
    public Resource[] getAvailableResources() {
        return resources.getAvailableResources();
    }

    /**
     * Retrieves the development card located at the specified tier and column on
     * the board.
     *
     * @param tier   The tier (row) of the development card.
     * @param column The column of the development card.
     * @return The development card at the specified tier and column.
     * @throws ArrayIndexOutOfBoundsException If the specified tier or column is out
     *                                        of bounds.
     */
    public DevCard getCard(int tier, int column) throws ArrayIndexOutOfBoundsException {
        return visibleCards[tier][column];
    }

    public void updateCard(DevCard card, int tier, int column)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (card == null && !stackCards[tier].isEmpty()) {
            throw new NullPointerException("There are still cards in the stack.");
        }

        if (!stackCards[tier].isEmpty()) {
            visibleCards[tier][column] = stackCards[tier].pop();
        } else {
            visibleCards[tier][column] = null;
        }
    }

    /**
     * Draws a development card from the specified tier's stack.
     * If the stack for the specified tier is not empty, the top card is removed and
     * returned.
     * If the stack is empty, null is returned, indicating no card is available to
     * draw.
     *
     * @param tier The tier (row) from which to draw a development card.
     * @return The development card drawn from the specified tier's stack, or null
     *         if the stack is empty.
     * @throws ArrayIndexOutOfBoundsException If the specified tier value is out of
     *                                        bounds.
     */
    public DevCard drawCard(int tier) throws ArrayIndexOutOfBoundsException {
        if (tier < 0 || tier >= stackCards.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "Invalid tier index. Ensure that the specified tier is within the bounds of the stackCards list.");
        }

        if (!stackCards[tier].isEmpty()) {
            return stackCards[tier].pop();
        }
        return null;
    }

    /**
     * Checks if the count of the specified resource allows giving tokens.
     *
     * @param resource The resource for which to check the eligibility to give
     *                 tokens.
     * @return true if the count of the specified resource is equal to or exceeds
     *         the minimum required for giving tokens,
     *         false otherwise.
     */
    public boolean canGiveSameTokens(Resource resource) {
        return resources.getNbResource(resource) >= Constants.MIN_NUMBER_RESOURCES_TO_GIVE_TOKEN;
    }

    /**
     * Checks if the counts of the specified resources allow giving different
     * tokens.
     *
     * @param resourcesArray The array of resources to check for eligibility to give
     *                       tokens.
     * @return true if the count of each specified resource is at least 1,
     *         indicating eligibility to give different tokens,
     *         false if the count of any specified resource is less than 1.
     */
    public boolean canGiveDiffTokens(Resource[] resourcesArray) {
        for (Resource resource : resourcesArray) {
            if (resources.getNbResource(resource) < Constants.DIFF_TOKEN_NUMBER) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts information about the cards in the specified deck tier to a
     * formatted string array.
     * The resulting array represents a visual preview of the deck, including the
     * number of remaining cards,
     * pluralization of "card(s)," and the tier number.
     *
     * @param tier The tier (row) of the deck for which to create the formatted
     *             string array.
     * @return An array of strings representing the formatted preview of the
     *         specified deck tier.
     *         Each element in the array corresponds to a line in the visual
     *         preview.
     */
    public String[] deckToStringArray(int tier) {
        // Convert the deck preview strings array into a single string to format with
        // values
        String deckPreview = String.join("\n", Constants.DECK_PREVIEW);

        // Format the string with values
        int remainingCards = stackCards[tier].size();
        String cards = String.format("%02d", remainingCards);
        String plural = remainingCards > 1 ? "s" : "";
        deckPreview = MessageFormat.format(deckPreview, cards, plural, tier);

        // Return the string split back into an array of strings
        return deckPreview.split("\n");
    }

    /**
     * Converts information about the available resources to a formatted string
     * array.
     * The resulting array represents a visual preview of the available resources,
     * including their counts and symbols.
     *
     * @return An array of strings representing the formatted preview of the
     *         available resources.
     *         The first element in the array provides a label, and subsequent
     *         elements detail the counts and symbols of each resource.
     */
    private String[] resourcesToStringArray() {
        String[] resourceArrayString = { "Resources disponibles : " };
        for (Resource resource : resources.keySet()) {
            resourceArrayString[0] += resources.getNbResource(resource) + resource.toSymbol() + " ";
        }
        return resourceArrayString;
    }

    /**
     * Converts the current state of the game board to a formatted string array.
     * The resulting array represents a visual preview of the game board, including
     * the deck, visible cards, and available resources.
     *
     * @return An array of strings representing the formatted preview of the game
     *         board.
     *         Each element in the array corresponds to a line in the visual
     *         preview.
     */
    private String[] boardToStringArray() {
        String[] resource = Display.emptyStringArray(0, 0);

        // Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for (int i = stackCards.length; i > 0; i--) {
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        // Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for (int i = 0; i < visibleCards.length; i++) {
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for (int j = 0; j < visibleCards[i].length; j++) {
                tierCardsDisplay = Display.concatStringArray(tierCardsDisplay,
                        visibleCards[i][j] != null ? visibleCards[i][j].toStringArray() : Constants.EMPTY_DECK_PREVIEW,
                        false);
            }
            cardDisplay = Display.concatStringArray(cardDisplay, Display.emptyStringArray(1, 40), true);
            cardDisplay = Display.concatStringArray(cardDisplay, tierCardsDisplay, true);
        }

        resource = Display.concatStringArray(deckDisplay, cardDisplay, false);
        resource = Display.concatStringArray(resource, Display.emptyStringArray(1, 52), true);
        resource = Display.concatStringArray(resource, resourcesToStringArray(), true);
        resource = Display.concatStringArray(resource, Display.emptyStringArray(35, 1, " \u250A"), false);
        resource = Display.concatStringArray(resource, Display.emptyStringArray(1, 54, "\u2509"), true);

        return resource;
    }

    /**
     * Overrides the default `toStringArray` method to provide a formatted string
     * array representation of the game state.
     *
     * @return An array of strings representing the formatted preview of the game
     *         board.
     *         Each element in the array corresponds to a line in the visual
     *         preview.
     */
    @Override
    public String[] toStringArray() {
        return boardToStringArray();
    }
}
