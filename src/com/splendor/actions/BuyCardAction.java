package com.splendor.actions;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.DevCard;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidColumnException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.InvalidNumberException;
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
    public void displayAction(Player player) {
        Constants.display.outBoard.println("Quelle position ?");
        Constants.display.outBoard.println("Entrez le tier et la colonne séparés par un espace.");
        Constants.display.outBoard.println("Exemple : 1 2 (pour la carte tier 1, colonne 2).");
        displayReservedCards(player);
    }

    private void displayReservedCards(Player player) {
        DevCard[] reservedCards = player.getReservedCards();
        if (player.getReservedCards()[0] != null) {
            Constants.display.outBoard.println("Vous avez déjà réservé les cartes suivantes :");
            for (int i = 0; i < reservedCards.length; i++) {
                if (reservedCards[i] != null) {
                    Constants.display.outBoard.println((i + 1) + " : " + reservedCards[i].toString());
                }
            }
            Constants.display.outBoard.println(
                    "Vous avez la possibilité d'en acheter une en entrant R et le numéro de la carte séparés par un espace.");
        }
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
        if (inputs[0] == "R") {
            checkReservedCards(board, player, inputs);
        } else {
            checkBoardCards(board, player, inputs);
        }

    }

    public void checkReservedCards(Board board, Player player, String[] inputs) throws ActionException {
        int number = parseValue(inputs[1]);
        validateNumberRange(number);
        DevCard card = player.getReservedCards()[number - 1];
        validateCardPresence(card);
        validatePlayerResources(player, card);
    }

    public void checkBoardCards(Board board, Player player, String[] inputs) throws ActionException {
        int tier = parseValue(inputs[0]);
        int column = parseValue(inputs[1]);
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
            throw new InvalidInputException(
                    "Vous devez entrer deux nombres séparés par un espace, ou R suivi du nombre de la carte si vous en avez réservé une.");
        }
        return inputs;
    }

    private int parseValue(String input) {
        return Integer.parseInt(input);
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

    private void validateNumberRange(int column) throws InvalidNumberException {
        if (column > Constants.MAX_RESERVED_CARDS) {
            throw new InvalidNumberException(
                    "Le numéro doit être compris entre 1 et " + Constants.MAX_RESERVED_CARDS + ".");
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
        if (inputs[0] == "R") {
            int number = parseValue(inputs[1]);
            DevCard card = player.getReservedCards()[number - 1];
            player.removeReservedCard(number - 1);
            player.addPurchasedCard(card);
            player.updatePoints(card);
        } else {
            int tier = parseValue(inputs[0]);
            int column = parseValue(inputs[1]);
            DevCard card = board.getCard(tier, column);
            board.updateCard(card, tier, column);
            player.addPurchasedCard(card);
            player.updatePoints(card);
        }
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
