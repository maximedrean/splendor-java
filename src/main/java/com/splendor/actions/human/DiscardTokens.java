package com.splendor.actions.human;

import java.util.HashMap;
import static java.util.Map.Entry;

import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.NotEnoughResourcesException;
import com.splendor.player.Player;


/**
 * Represents an action where a player can discard excess tokens/resources 
 * in the Splendor board game. Extends Token and provides specific 
 * implementation for discarding tokens.
 */
public class DiscardTokens extends Token {

    static {
        Token.resources.put("F", Resource.JOKER);
    }

    /**
     * Displays information about the discard tokens action to the player.
     *
     * @param player The player for whom the action is displayed.
     */
    @Override
    public void displayAction(Player player) {
        Utility.display.out.println(Messages.RESOURCES_SELECTION);
        super.displayResources();
        Utility.display.out.println(Messages.DISCARD_TOKEN_MESSAGE);
    }

    /**
     * Checks the validity of the player's input for the discard 
     * tokens action.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     * @param input The player's input to be checked for validity.
     * @throws ActionException If the input is invalid for the discard 
     *         tokens action.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) 
            throws ActionException {
        final String[] inputs = this.validateInputFormat(input, player);
        final HashMap<Resource, Integer> resources = 
            this.extractResources(inputs);
        this.validateResources(player, resources);
    }

    /**
     * Validates the format of the player's input for the discard 
     * tokens action.
     *
     * @param input The player's input to be validated.
     * @param player The player performing the action.
     * @return An array of validated inputs (token identifiers) if the 
     *         format is correct.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input, Player player)
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        final int resources = player.getNumberResourcesToDiscard();
        if (inputs.length != resources) throw new InvalidInputException(
            String.format(Messages.DISCARD_TOKEN_SUPPLY_ERROR, resources));
        for (final String letter : inputs) {
            if (Token.resources.containsKey(letter)) continue;
            throw new InvalidInputException(Messages.DISCARD_TOKEN_ERROR);
        }
        return inputs;
    }

    /**
     * Validates that the player has enough resources to buy the 
     * specified card.
     *
     * @param player The player performing the action.
     * @param card The DevCard object to be purchased.
     * @throws NotEnoughResourcesException If the player does not have enough
     *         resources to buy the card.
     */
    private void validateResources(
            Player player, HashMap<Resource, Integer> map) 
            throws NotEnoughResourcesException {
        for (final Entry<Resource, Integer> entry : map.entrySet()) {
            final Resource resource = entry.getKey();
            final int value = entry.getValue();
            if (player.getNbResource(resource) >= value) continue;
            throw new NotEnoughResourcesException(
                Messages.NOT_ENOUGH_RESOURCES);
        }
    }

    /**
     * Extracts the resources to discard from the player's input.
     *
     * @param inputs The player's input as an array of token identifiers.
     * @return A mapping of resources to the number of tokens to discard.
     */
    private HashMap<Resource, Integer> extractResources(String[] inputs) {
        final HashMap<Resource, Integer> map = new HashMap<>();
        for (final String letter : inputs) {
            final Resource resource = Token.resources.get(letter);
            final boolean hasResource = map.containsKey(resource);
            final int value = hasResource ? map.get(resource) : 0;
            map.put(resource, value + 1);
        }
        return map;
    }

    /**
     * Processes the valid input for the discard tokens action, updating 
     * the player's and board's resources accordingly.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     * @param input The valid player's input to be processed.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        final String[] inputs = input.split(" ");
        final HashMap<Resource, Integer> resources = 
            this.extractResources(inputs);
        for (final Entry<Resource, Integer> entry : resources.entrySet()) {
            final Resource resource = entry.getKey();
            final int value = entry.getValue();
            player.updateNbResource(resource, -value);
            board.updateNbResource(resource, value);
        }
    }
}
