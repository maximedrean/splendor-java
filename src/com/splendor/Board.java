package com.splendor;

import java.text.MessageFormat;
import java.util.Stack;
import java.util.List;

/**
 * The Board class represents the game board in Splendor, and implements the
 * Displayable interface.
 * The board contains the resources and development cards.
 */
public class Board implements Displayable {

    /*
     * The resources on the board.
     */
    private Resources resources = new Resources();

    /*
     * The stack of development cards for each level.
     */
    private List<Stack<DevCard>> stackCards;

    /*
     * The development cards visible on the board.
     */
    private DevCard[][] visibleCards;

    /**
     * Retrieves the development card located at the specified tier and column in
     * the visible cards array.
     *
     * @param tier   The tier (row) of the development card to retrieve.
     * @param column The column of the development card to retrieve.
     * @return The development card at the specified tier and column in the visible
     *         cards array.
     * @throws ArrayIndexOutOfBoundsException If the provided tier or column values
     *                                        are out of bounds for the visible
     *                                        cards array.
     *                                        The visible cards array should be a 2D
     *                                        array representing the game board with
     *                                        valid indices.
     *                                        Ensure that the specified tier and
     *                                        column values are within the bounds of
     *                                        the array.
     */
    public DevCard getCard(int tier, int column) throws ArrayIndexOutOfBoundsException {
        return visibleCards[tier][column];
    }

    /**
     * Updates the development card at the specified tier and column with the
     * provided card.
     * If the stack of cards for the specified tier is not empty, the top card is
     * removed and placed at the specified position.
     * If the stack is empty, the specified position is set to {@code null}.
     *
     * @param card   The development card to be placed at the specified tier and
     *               column.
     * @param tier   The tier (row) where the card should be updated.
     * @param column The column where the card should be updated.
     * @throws NullPointerException           If the provided card is {@code null}.
     *                                        Ensure that the updated card is not
     *                                        null.
     * @throws ArrayIndexOutOfBoundsException If the provided tier or column values
     *                                        are out of bounds for the visible
     *                                        cards array.
     *                                        The visible cards array should be a 2D
     *                                        array representing the game board with
     *                                        valid indices.
     *                                        Ensure that the specified tier and
     *                                        column values are within the bounds of
     *                                        the array.
     */
    public void updateCard(DevCard card, int tier, int column)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (card == null && visibleCards[tier][column] != null) {
            throw new NullPointerException("Cannot update a null card.");
        }

        if (!stackCards.get(tier).isEmpty()) {
            visibleCards[tier][column] = stackCards.get(tier).pop();
        } else {
            visibleCards[tier][column] = null;
        }
    }

    /**
     * Draws a development card from the specified tier's stack.
     * If the stack for the specified tier is not empty, the top card is removed and
     * returned.
     * If the stack is empty, {@code null} is returned, indicating no card is
     * available to draw.
     *
     * @param tier The tier (row) from which to draw a development card.
     * @return The development card drawn from the specified tier's stack, or
     *         {@code null} if the stack is empty.
     * @throws ArrayIndexOutOfBoundsException If the provided tier value is out of
     *                                        bounds for the stackCards list.
     *                                        The stackCards list should have valid
     *                                        indices corresponding to the game
     *                                        board tiers.
     *                                        Ensure that the specified tier value
     *                                        is within the bounds of the list.
     */
    public DevCard drawCard(int tier) throws ArrayIndexOutOfBoundsException {
        if (tier < 0 || tier >= stackCards.size()) {
            throw new ArrayIndexOutOfBoundsException(
                    "Invalid tier index. Ensure that the specified tier is within the bounds of the stackCards list.");
        }

        if (!stackCards.get(tier).isEmpty()) {
            return stackCards.get(tier).pop();
        }
        return null;
    }

    /**
     * Checks if the current count of the specified resource allows giving tokens.
     * Tokens can be given if the count of the specified resource is equal to or
     * exceeds a predefined minimum.
     *
     * @param resource The resource for which to check the eligibility to give
     *                 tokens.
     * @return {@code true} if the count of the specified resource is equal to or
     *         exceeds the minimum required for giving tokens,
     *         {@code false} otherwise.
     */
    public boolean canGiveSameTokens(Resource resource) {
        return resources.getNbResource(resource) >= Constants.MIN_NUMBER_RESOURCES_TO_GIVE_TOKEN;
    }

    /**
     * Checks if the current counts of the specified resources allow giving
     * different tokens.
     * Tokens can be given if the count of each specified resource is at least 1.
     *
     * @param resourcesList The list of resources to check for eligibility to give
     *                      tokens.
     * @return {@code true} if the count of each specified resource is at least 1,
     *         indicating eligibility to give different tokens,
     *         {@code false} if the count of any specified resource is less than 1.
     */
    public boolean canGiveDiffTokens(List<Resource> resourcesList) {
        for (Resource resource : resourcesList) {
            if (resources.getNbResource(resource) < 1) {
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
     * the pluralization of "card(s)," and the tier number.
     *
     * @param tier The tier (row) of the deck for which to create the formatted
     *             string array.
     * @return An array of strings representing the formatted preview of the
     *         specified deck tier.
     *         The array includes information about the number of remaining cards,
     *         pluralization, and tier number.
     *         Each element in the array corresponds to a line in the visual
     *         preview.
     */
    public String[] deckToStringArray(int tier) {
        // Convert the deck preview strings array into a single string in order to
        // format with values
        String deckPreview = String.join("\n", Constants.DECK_PREVIEW);

        // Format the string with values
        int remainingCards = stackCards.get(tier).size();
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
        for (int i = stackCards.size(); i > 0; i--) {
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        // Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for (int i = 0; i < visibleCards.length; i++) { // parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for (int j = 0; j < visibleCards[i].length; j++) { // parcourir les 4 cartes faces visibles pour un niveau
                                                               // donné (j)
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
     * Overrides the default {@code toStringArray} method to provide a formatted
     * string array representation of the game state.
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