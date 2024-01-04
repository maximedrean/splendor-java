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
 * The `PickDiffTokensAction` class represents an action where a player 
 * can take three different tokens from the game board.
 * It extends the `TokenAction` class.
 */
public class PickDifferentTokens extends Token {

    /**
     * Displays information about the action, including instructions
     * for user input.
     */
    @Override
    public void displayAction(Player player) {
        Utility.display.out.println(Messages.RESOURCES_SELECTION);
        super.displayResources();
        Utility.display.out.println(Messages.DIFFERENT_TOKEN_MESSAGE);
    }

    /**
     * Checks the validity of the user input for the action.
     * The input should consist of three letters separated by a space, 
     * corresponding to different resources.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The user input.
     * @throws ActionException If the input format is invalid or if there 
     *         are not enough resources on the board.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) 
            throws ActionException {
        final String[] inputs = this.validateInputFormat(input);
        final Resource[] resources = this.extractResources(inputs);
        this.validateBoardResources(board, resources);
    }

    /**
     * Validates the format of the user input for the action.
     * The input should consist of three letters separated by a space.
     *
     * @param input The user input.
     * @return An array of strings representing individual resource letters.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input)
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        if (inputs.length != 3)  // Not enough value to unpack.
            throw new InvalidInputException(Messages.DIFFERENT_TOKEN_ERROR);
        boolean valid = Token.resources.containsKey(inputs[0]);
        valid = valid && Token.resources.containsKey(inputs[1]);
        valid = valid && Token.resources.containsKey(inputs[2]);
        if (valid) return inputs;
        throw new InvalidInputException(Messages.DIFFERENT_TOKEN_ERROR);
    }

    /**
     * Extracts the resource objects corresponding to the user input.
     *
     * @param inputs An array of strings representing the individual 
     *        resource letters.
     * @return An array of `Resource` objects.
     */
    private Resource[] extractResources(String[] inputs) {
        final Resource[] resources = new Resource[Values.DIFF_TOKEN_LIMIT];
        for (int index = 0; index < Values.DIFF_TOKEN_LIMIT; index++)
            resources[index] = Token.resources.get(inputs[index]);
        return resources;
    }

    /**
     * Validates whether there are enough resources on the board for the 
     * player to take.
     *
     * @param board The game board instance.
     * @param resources An array of `Resource` objects representing the 
     *        selected resources.
     * @throws NotEnoughResourcesException If there are not enough 
     *         resources on the board.
     */
    private void validateBoardResources(Board board, Resource[] resources) 
            throws NotEnoughResourcesException {
        if (board.canGiveDiffTokens(resources)) return;
        throw new NotEnoughResourcesException(Messages.NOT_ENOUGH_GEMS);
    }

    /**
     * Processes the user input and updates the player's resources accordingly.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @param input The user input.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        final String[] inputs = input.split(" ");
        final Resource[] resources = this.extractResources(inputs);
        for (final Resource resource : resources)
            player.updateNbResource(resource, Values.DIFF_TOKEN_NUMBER);
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return Messages.OPTION_B;
    }
}
