package com.splendor.actions;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.DevCard;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidColumnException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.InvalidTierException;
import com.splendor.exceptions.NotEnoughResourcesException;
import com.splendor.exceptions.NullCardException;
import com.splendor.player.Player;

/**
 * The BuyCardAction class represents the action of buying a card from the
 * board. It extends the InputAction class.
 */
public class BuyCardAction extends InputAction {

    /**
     * Displays instructions for the player to input the tier and column of the card
     * they want to buy.
     */
    @Override
    public void displayAction() {
        Constants.display.outBoard.println("Quelle position ?");
        Constants.display.outBoard.println("Entrez le tier et la colonne séparés par un espace.");
        Constants.display.outBoard.println("Exemple : 1 2 (pour la carte tier 1, colonne 2).");
    }

    /**
     * Checks the validity of the input for buying a card, including the format,
     * tier, column, card presence, and player's resources.
     *
     * @param board  The game board.
     * @param player The player performing the action.
     * @param input  The input provided by the player.
     * @throws ActionException If the input is invalid or the action cannot be
     *                         performed.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) throws ActionException {
        String[] inputs = validateInputFormat(input);
        int tier = extractTier(inputs[0]);
        int column = extractColumn(inputs[1]);
        validateTierRange(tier);
        validateColumnRange(column);
        DevCard card = board.getCard(tier, column);
        validateCardPresence(card);
        validatePlayerResources(player, card);
    }

    /**
     * Validates the format of the input for buying a card.
     *
     * @param input The input provided by the player.
     * @return An array containing the tier and column values.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input) throws InvalidInputException {
        String[] inputs = input.split(" ");
        if (inputs.length != 2) {
            throw new InvalidInputException("Vous devez entrer deux nombres séparés par un espace.");
        }
        return inputs;
    }

    /**
     * Extracts the tier value from the input.
     *
     * @param tierInput The tier value as a string.
     * @return The tier value as an integer.
     */
    private int extractTier(String tierInput) {
        return Integer.parseInt(tierInput);
    }

    /**
     * Extracts the column value from the input.
     *
     * @param columnInput The column value as a string.
     * @return The column value as an integer.
     */
    private int extractColumn(String columnInput) {
        return Integer.parseInt(columnInput);
    }

    /**
     * Validates that the tier value is within the allowed range.
     *
     * @param tier The tier value.
     * @throws InvalidTierException If the tier is outside the allowed range.
     */
    private void validateTierRange(int tier) throws InvalidTierException {
        if (tier < 1 || tier > Constants.TIER_NUMBER) {
            throw new InvalidTierException("Le tier doit être compris entre 1 et " + Constants.TIER_NUMBER + ".");
        }
    }

    /**
     * Validates that the column value is within the allowed range.
     *
     * @param column The column value.
     * @throws InvalidColumnException If the column is outside the allowed range.
     */
    private void validateColumnRange(int column) throws InvalidColumnException {
        if (column < 1 || column > Constants.COLUMN_NUMBER) {
            throw new InvalidColumnException(
                    "La colonne doit être comprise entre 1 et " + Constants.COLUMN_NUMBER + ".");
        }
    }

    /**
     * Validates that a card is present at the specified position on the board.
     *
     * @param card The DevCard object to validate.
     * @throws NullCardException If there is no card at the specified position.
     */
    private void validateCardPresence(DevCard card) throws NullCardException {
        if (card == null) {
            throw new NullCardException("Il n'y a pas de carte à cette position.");
        }
    }

    /**
     * Validates that the player has enough resources to buy the specified card.
     *
     * @param player The player performing the action.
     * @param card   The DevCard object to be purchased.
     * @throws NotEnoughResourcesException If the player does not have enough
     *                                     resources to buy the card.
     */
    private void validatePlayerResources(Player player, DevCard card) throws NotEnoughResourcesException {
        if (!player.canBuyCard(card)) {
            throw new NotEnoughResourcesException("Vous n'avez pas assez de ressources pour acheter cette carte.");
        }
    }

    /**
     * Processes the valid input and updates the player's purchased cards, points,
     * and the board.
     *
     * @param board  The game board.
     * @param player The player performing the action.
     * @param input  The valid input provided by the player.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        String[] inputs = input.split(" ");
        int tier = extractTier(inputs[0]);
        int column = extractColumn(inputs[1]);
        DevCard card = board.getCard(tier, column);
        player.addPurchasedCard(card);
        player.updatePoints(card);
        board.updateCard(card, tier, column);
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return "C: Acheter une carte";
    }
}
