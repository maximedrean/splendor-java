package com.splendor.actions;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.Resource;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.NotEnoughResourcesException;
import com.splendor.player.Player;

/**
 * The `PickDiffTokensAction` class represents an action where a player can take
 * three different tokens from the game board.
 * It extends the `TokenAction` class.
 */
public class PickDiffTokensAction extends TokenAction {

    /**
     * Displays information about the action, including instructions for user input.
     */
    @Override
    public void displayAction(Player player) {
        Constants.display.outBoard.println("Quelles ressources ?");
        displayResources();
        Constants.display.outBoard.println("Entrez les lettres correspondant aux ressources séparées par un espace.");
        Constants.display.outBoard.println("Exemple : A C E (pour les ressources EMERALD, SAPPHIRE et RUBY).");
    }

    /**
     * Checks the validity of the user input for the action.
     * The input should consist of three letters separated by a space, corresponding
     * to different resources.
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
        Resource[] resources = extractResources(inputs);
        validateBoardResources(board, resources);
    }

    /**
     * Validates the format of the user input for the action.
     * The input should consist of three letters separated by a space.
     *
     * @param input The user input.
     * @return An array of strings representing the individual resource letters.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input) throws InvalidInputException {
        String[] inputs = input.split(" ");
        if (inputs.length != 3 || !resources.containsKey(inputs[0]) || !resources.containsKey(inputs[1])
                || !resources.containsKey(inputs[2])) {
            throw new InvalidInputException(
                    "Vous devez entrer trois lettres correspondant aux ressources séparées par un espace.");
        }
        return inputs;
    }

    /**
     * Extracts the resource objects corresponding to the user input.
     *
     * @param inputs An array of strings representing the individual resource
     *               letters.
     * @return An array of `Resource` objects.
     */
    private Resource[] extractResources(String[] inputs) {
        Resource[] resourcesArray = new Resource[3];
        resourcesArray[0] = resources.get(inputs[0]);
        resourcesArray[1] = resources.get(inputs[1]);
        resourcesArray[2] = resources.get(inputs[2]);
        return resourcesArray;
    }

    /**
     * Validates whether there are enough resources on the board for the player to
     * take.
     *
     * @param board     The game board.
     * @param resources An array of `Resource` objects representing the selected
     *                  resources.
     * @throws NotEnoughResourcesException If there are not enough resources on the
     *                                     board.
     */
    private void validateBoardResources(Board board, Resource[] resources) throws NotEnoughResourcesException {
        if (!board.canGiveDiffTokens(resources)) {
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
        Resource[] resources = extractResources(inputs);
        for (Resource resource : resources) {
            player.updateNbResource(resource, Constants.DIFF_TOKEN_NUMBER);
        }
    }

    /**
     * Returns a string representation of the action.
     *
     * @return A string representing the action.
     */
    @Override
    public String toString() {
        return "B: Prendre 3 gemmes différentes";
    }
}
