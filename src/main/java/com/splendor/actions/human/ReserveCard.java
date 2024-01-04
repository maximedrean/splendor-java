package com.splendor.actions.human;

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
import com.splendor.exceptions.InvalidTierException;
import com.splendor.exceptions.MaxReservedCardsException;
import com.splendor.exceptions.NullCardException;
import com.splendor.player.Player;


public class ReserveCard extends HumanAction {

    /**
     * Displays messages related to the player's action for reserving a card.
     *
     * @param player The player performing the action.
     */
    @Override
    public void displayAction(Player player) {
        Utility.display.out.println(Messages.POSITION_SELECTION);
        Utility.display.out.println(Messages.CARD_MESSAGE);
        Utility.display.out.println(Messages.RESERVE_CARD_MESSAGE);
    }

    /**
     * Checks the validity of the input provided by the player for 
     * reserving a card.
     *
     * @param board  The game board.
     * @param player The player performing the action.
     * @param input  The input provided by the player.
     * @throws ActionException If the input is invalid or the action 
     *         cannot be performed.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) 
            throws ActionException {
        final String[] inputs = this.validateInput(input);
        final int tier = Integer.parseInt(inputs[0]);
        this.validateTier(tier);
        if (inputs.length != 2) {
            this.validatePlayerReservedCards(player);
            return;
        }
        final int column = Integer.parseInt(inputs[1]);
        this.validateColumn(column);
        final DevCard card = board.getCard(tier, column);
        this.validateCardPresence(card);
    }

    /**
     * Validates and splits the input into components.
     *
     * @param input The input provided by the player.
     * @return An array of input components.
     * @throws InvalidInputException If the input is invalid.
     */
    private String[] validateInput(String input)    
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        if (0 < inputs.length && inputs.length <= 2) return inputs;
        throw new InvalidInputException(Messages.RESERVE_CARD_INVALID_INPUT);
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
     * Validates that a card is present at the specified position 
     * on the board.
     *
     * @param card The DevCard object to validate.
     * @throws NullCardException If there is no card at the
     *         specified position.
     */
    private void validateCardPresence(DevCard card) throws NullCardException {
        if (card != null) return;
        throw new NullCardException(Messages.CARD_EMPTY_SLOT);
    }

    /**
     * Validates whether a player is allowed to reserve another card based
     * on the maximum limit.
     *
     * @param player The player whose reserved cards are to be validated.
     * @throws MaxReservedCardsException If the player has reached the 
     *         maximum limit of reserved cards.
     */
    private void validatePlayerReservedCards(Player player) 
            throws MaxReservedCardsException {
        if (!player.canReserveCard()) throw new MaxReservedCardsException(
            Messages.RESERVE_CARD_LIMIT_ERROR);
    }

    /**
     * Processes the valid input and updates the player's purchased cards, 
     * points, and the board.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The valid input provided by the player.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        final String[] inputs = input.split(" ");
        final int tier = Integer.parseInt(inputs[0]);
        final DevCard newCard = board.drawCard(tier);
        if (inputs.length != 1) {
            final int column = Integer.parseInt(inputs[1]);
            final DevCard card = board.getCard(tier, column);
            board.updateCard(newCard, tier, column);
            player.addReservedCard(card);
        } else player.addReservedCard(newCard);
        player.updateNbResource(Resource.JOKER, 1);
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return Messages.OPTION_D;
    }
}
