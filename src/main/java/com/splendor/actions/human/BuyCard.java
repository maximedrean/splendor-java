package com.splendor.actions.human;

import com.splendor.Resources;
import com.splendor.actions.HumanAction;
import com.splendor.board.Board;
import com.splendor.cards.DevCard;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;
import com.splendor.constants.Values;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidColumnException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.InvalidNumberException;
import com.splendor.exceptions.InvalidTierException;
import com.splendor.exceptions.NotEnoughResourcesException;
import com.splendor.exceptions.NullCardException;
import com.splendor.player.Player;


/**
 * The BuyCard class represents the action of buying a card from the
 * board. It extends the InputAction class.
 */
public class BuyCard extends HumanAction {

    /**
     * Displays instructions for the player to input the tier and column 
     * of the card they want to buy.
     */
    @Override
    public void displayAction(Player player) {
        Utility.display.out.println(Messages.POSITION_SELECTION);
        Utility.display.out.println(Messages.BUY_CARD_MESSAGE);
        this.displayReservedCards(player);
    }

    /**
     * Displays the player's reserved cards along with their indexes 
     * for selection.
     *
     * @param player The player whose reserved cards are to be displayed.
     */
    private void displayReservedCards(Player player) {
        // Retrieve the array of reserved cards from the player.
        final DevCard[] reservedCards = player.getReservedCards();
        if (player.getReservedCards()[0] == null) return;
        Utility.display.out.println(Messages.RESERVED_CARDS);
        // Iterate through the reserved cards array of the player.
        for (int index = 0; index < reservedCards.length; index++) {
            // Skip if the current reserved card slot is empty.
            if (reservedCards[index] == null) continue;
            final String cardPreview = reservedCards[index].toString();
            Utility.display.out.println((index + 1) + " : " + cardPreview);
        }
        // Display a message prompting the player to buy a reserved card.
        Utility.display.out.println(Messages.BUY_RESERVED_CARD);
    }

    /**
     * Checks the validity of the input for buying a card, including the 
     * format, tier, column, card presence, and player's resources.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The input provided by the player.
     * @throws ActionException If the input is invalid or the action 
     *         cannot be performed.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) 
            throws ActionException {
        final String[] inputs = validateInputFormat(input);
        if ("R".equals(inputs[0])) this.checkReserved(board, player, inputs);
        else this.checkBoard(board, player, inputs);
    }

    public void checkReserved(Board board, Player player, String[] inputs)
            throws ActionException {
        final int number = Integer.parseInt(inputs[1]);
        this.validateNumber(number);
        final DevCard card = player.getReservedCards()[number - 1];
        this.validateCardPresence(card);
        this.validatePlayerResources(player, card);
    }

    public void checkBoard(Board board, Player player, String[] inputs)
            throws ActionException {
        final int tier = Integer.parseInt(inputs[0]);
        final int column = Integer.parseInt(inputs[1]);
        this.validateTier(tier);
        this.validateColumn(column);
        final DevCard card = board.getCard(tier, column);
        this.validateCardPresence(card);
        this.validatePlayerResources(player, card);
    }

    /**
     * Validates the format of the input for buying a card.
     *
     * @param input The input provided by the player.
     * @return An array containing the tier and column values.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input)
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        if (inputs.length == 2) return inputs;
        throw new InvalidInputException(Messages.BUY_CARD_INVALID_INPUT);
    }

    /**
     * Validates that the tier value is within the allowed range.
     *
     * @param tier The tier value.
     * @throws InvalidTierException If the tier is outside the allowed range.
     */
    private void validateTier(int tier) throws InvalidTierException {
        if (1 <= tier && tier <= Values.TIER_NUMBER) return;
        throw new InvalidTierException(Messages.CARD_INVALID_TIER);
    }

    /**
     * Validates that the column value is within the allowed range.
     *
     * @param column The column value.
     * @throws InvalidColumnException If the column is outside the 
     *         allowed range.
     */
    private void validateColumn(int column) throws InvalidColumnException {
        if (1 <= column && column <= Values.COLUMN_NUMBER) return;
        throw new InvalidColumnException(Messages.CARD_INVALID_COLUMN);
    }

    /**
     * Validates that the number value is within the allowed range.
     *
     * @param number The column value.
     * @throws InvalidColumnException If the number is outside the 
     *         allowed range.
     */
    private void validateNumber(int number) throws InvalidNumberException {
        if (number <= Values.MAX_RESERVED_CARDS) return;
        throw new InvalidNumberException(Messages.CARD_INVALID_NUMBER);
    }

    /**
     * Validates that a card is present at the specified position on 
     * the board.
     *
     * @param card The DevCard object to validate.
     * @throws NullCardException If there is no card at the specified 
     *         position.
     */
    private void validateCardPresence(DevCard card) throws NullCardException {
        if (card != null) return;
        throw new NullCardException(Messages.CARD_EMPTY_SLOT);
    }

    /**
     * Validates that the player has enough resources to buy the 
     * specified card.
     *
     * @param player The player performing the action.
     * @param card The DevCard object to be purchased.
     * @throws NotEnoughResourcesException If the player does not have 
     *         enough resources to buy the card.
     */
    private void validatePlayerResources(Player player, DevCard card) 
            throws NotEnoughResourcesException {
        if (!player.canBuyCard(card)) throw new NotEnoughResourcesException(
            Messages.BUY_CARD_NOT_ENOUGH_RESOURCES);
    }

    /**
     * Processes the valid input and updates the player's purchased 
     * cards, points, and the board.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The valid input provided by the player.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        final String[] inputs = input.split(" ");
        DevCard card;
        if ("R".equals(inputs[0])) { // Reserved card.
            final int number = Integer.parseInt(inputs[1]);
            // Get the reserved card at the specified index.
            card = player.getReservedCards()[number - 1];
            // Remove the reserved card from the player's reserved cards.
            player.removeReservedCard(number - 1);
        } else { // Default card.
            final int tier = Integer.parseInt(inputs[0]);
            final int column = Integer.parseInt(inputs[1]);
            // Get the card at the specified tier and column
            card = board.getCard(tier, column);
            // Update the board by removing the card.
            board.updateCard(card, tier, column);
        }
        // Add the processed card to the player's purchased cards.
        player.addPurchasedCard(card);
        // Update the player's points based on the processed card
        player.updatePoints(card);
        Resources resourceCosts = card.getCost();
        Resource[] resources = resourceCosts.getAvailableResources();
        // Iterate through the resources and deduct the quantity.
        for (final Resource resource : resources) {
            int quantity = resourceCosts.getNbResource(resource);
            // Update the player's resource quantity.
            player.updateNbResource(resource, -quantity);
        }
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return Messages.OPTION_C;
    }
}
