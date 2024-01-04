package com.splendor.actions.human;

import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;
import com.splendor.constants.Values;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.NotEnoughResourcesException;
import com.splendor.player.Player;


/**
 * The `PickSameTokensAction` class represents an action where a player 
 * can take two identical tokens from the game board.
 * It extends the `TokenAction` class.
 */
public class PickSameTokens extends Token {

    /**
     * Displays information about the action, including instructions 
     * for user input.
     */
    @Override
    public void displayAction(Player player) {
        Utility.display.out.println(Messages.RESOURCE_SELECTION);
        super.displayResources();
        Utility.display.out.println(Messages.SAME_TOKEN_MESSAGE);
    }

    /**
     * Checks the validity of the user input for the action. The input 
     * should consist of a single letter corresponding to a resource.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The user input.
     * @throws ActionException If the input format is invalid or if 
     *         there are not enough resources on the board.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) 
            throws ActionException {
        final String[] inputs = this.validateInputFormat(input);
        final Resource resource = this.extractResource(inputs);
        this.validateBoardResource(board, resource);
    }

    /**
     * Validates the format of the user input for the action.
     * The input should consist of a single letter.
     *
     * @param input The user input.
     * @return An array of strings representing the individual 
     *         resource letters.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input) 
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        if (inputs.length < 1) // Not enough value to unpack.
            throw new InvalidInputException(Messages.SAME_TOKEN_ERROR);
        final boolean valid = Token.resources.containsKey(inputs[0]);
        if (valid) return inputs;
        throw new InvalidInputException(Messages.SAME_TOKEN_ERROR);
    }

    /**
     * Extracts the resource object corresponding to the user input.
     *
     * @param inputs An array of strings representing the individual 
     *        resource letters.
     * @return A `Resource` object.
     */
    private Resource extractResource(String[] inputs) {
        return Token.resources.get(inputs[0]);
    }

    /**
     * Validates whether there are enough resources on the board for 
     * the player to take.
     *
     * @param board The game board.
     * @param resource A `Resource` object representing the selected resource.
     * @throws NotEnoughResourcesException If there are not enough 
     *         resources on the board.
     */
    private void validateBoardResource(Board board, Resource resource) 
            throws NotEnoughResourcesException {
        if (board.canGiveSameTokens(resource)) return;
        throw new NotEnoughResourcesException(Messages.NOT_ENOUGH_GEMS);
    }

    /**
     * Processes the user input and updates the player's resources 
     * accordingly.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The user input.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        final String[] inputs = input.split(" ");
        final Resource resource = this.extractResource(inputs);
        player.updateNbResource(resource, Values.SAME_TOKEN_NUMBER);
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return Messages.OPTION_A;
    }
}
