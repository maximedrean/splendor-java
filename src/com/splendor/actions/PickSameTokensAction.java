package com.splendor.actions;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.Resource;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.NotEnoughResourcesException;
import com.splendor.player.Player;

/**
 * The `PickSameTokensAction` class represents an action where a player can take
 * two identical tokens from the game board.
 * It extends the `TokenAction` class.
 */
public class PickSameTokensAction extends TokenAction {

    /**
     * Displays information about the action, including instructions for user input.
     */
    @Override
    public void displayAction() {
        Constants.display.outBoard.println("Quelle ressource ?");
        displayResources();
        Constants.display.outBoard.println("Entrez la lettre correspondant à la ressource.");
        Constants.display.outBoard.println("Exemple : A (pour la ressource EMERALD).");
    }

    /**
     * Checks the validity of the user input for the action.
     * The input should consist of a single letter corresponding to a resource.
     *
     * @param board  The game board.
     * @param player The player performing the action.
     * @param input  The user input.
     * @throws ActionException If the input format is invalid or if there are not
     *                         enough resources on the board.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) throws ActionException {
        String[] inputs = validateInputFormat(input);
        Resource resource = extractResource(inputs);
        validateBoardResource(board, resource);
    }

    /**
     * Validates the format of the user input for the action.
     * The input should consist of a single letter.
     *
     * @param input The user input.
     * @return An array of strings representing the individual resource letters.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input) throws InvalidInputException {
        String[] inputs = input.split(" ");
        if (inputs.length != 1 || !resources.containsKey(inputs[0])) {
            throw new InvalidInputException("Vous devez entrer une seule lettre correspondant à une ressource");
        }
        return inputs;
    }

    /**
     * Extracts the resource object corresponding to the user input.
     *
     * @param inputs An array of strings representing the individual resource
     *               letters.
     * @return A `Resource` object.
     */
    private Resource extractResource(String[] inputs) {
        return resources.get(inputs[0]);
    }

    /**
     * Validates whether there are enough resources on the board for the player to
     * take.
     *
     * @param board    The game board.
     * @param resource A `Resource` object representing the selected resource.
     * @throws NotEnoughResourcesException If there are not enough resources on the
     *                                     board.
     */
    private void validateBoardResource(Board board, Resource resource) throws NotEnoughResourcesException {
        if (!board.canGiveSameTokens(resource)) {
            throw new NotEnoughResourcesException("Il n'y a pas assez de gemmes disponibles.");
        }
    }

    /**
     * Processes the user input and updates the player's resources accordingly.
     *
     * @param board  The game board.
     * @param player The player performing the action.
     * @param input  The user input.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        String[] inputs = input.split(" ");
        Resource resource = extractResource(inputs);
        player.updateNbResource(resource, Constants.SAME_TOKEN_NUMBER);
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return "A: Prendre 2 gemmes identiques";
    }
}
