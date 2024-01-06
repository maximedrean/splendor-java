package com.splendor.actions.human;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.splendor.actions.HumanAction;
import com.splendor.board.Board;
import com.splendor.board.Resources;
import com.splendor.cards.Noble;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.InvalidNumberException;
import com.splendor.player.Player;


/**
 * The {@code NobleVisit} class represents a human action of visiting a 
 * noble. It extends the {@code HumanAction} class, inheriting basic 
 * human action behavior. This class encapsulates the specific details 
 * and logic related to visiting a noble.
 */
public class NobleVisit extends HumanAction {

    private final Board board;

    public NobleVisit(Board board) {
        this.board = board;
    }

    /**
     * Gets the nobles from the board that a player can visit.
     *
     * @param board The game board containing the nobles.
     * @param player The player for whom to check if they can visit 
     *        the nobles.
     * @return An array of nobles that the player can visit.
     */
    private Noble[] getNobles(Board board, Player player) {
        final ArrayList<Noble> noblesVisit = new ArrayList<Noble>();
        for (Noble noble : board.getNobles())
            if (noble != null && this.canNobleVisit(noble, player))
                noblesVisit.add(noble);
        return noblesVisit.toArray(new Noble[noblesVisit.size()]);
    }

    /**
     * Checks if a player can visit a noble based on the required 
     * resources from purchased cards.
     *
     * @param noble The noble to be visited.
     * @param player The player attempting to visit the noble.
     * @return {@code true} if the player can visit the noble, 
     *         {@code false} otherwise.
     */
    private boolean canNobleVisit(Noble noble, Player player) {
        final Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            final int playerResources = player.getResFromCards(resource);
            if (cost.getNbResource(resource) > playerResources) return false;
        }
        return true;
    }

    /**
     * Adds a noble to the player's purchased cards, deducts the required 
     * resources, and updates the player's points accordingly.
     *
     * @param noble The noble to be added to the player.
     * @param player The player who receives the noble.
     */
    private void addNoble(Noble noble, Player player) {
        player.addPurchasedCard(noble);
        final Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            final int quantity = cost.getNbResource(resource);
            for (int index = 0; index < quantity; index++)
                player.removePurchasedCard(resource);
        }
        player.updatePoints(noble);
    }

    /**
     * An action representing the process of a player acquiring nobles in the 
     * Splendor game.
     *
     * @param board The game board.
     * @param player The player performing the action.
     * @return True if the action is successfully processed, false otherwise.
     */
    @Override
    public boolean process(Board board, Player player) {
        final Noble[] nobles = this.getNobles(this.board, player);
        if (nobles.length > 0) {
            if (!super.process(board, player)) return false;
        } else Utility.display.out.println(Messages.NO_NOBLE);
        return true;
    }

    /**
     * Displays the available nobles and prompts the player to choose one.
     *
     * @param player The player performing the action.
     */
    @Override
    public void displayAction(Player player) {
        final Noble[] nobles = this.getNobles(this.board, player);
        // Select the correct message according to the number of Nobles.
        String message = Messages.NOBLES_AVAILABLE;
        if (nobles.length == 1) message = Messages.NOBLE_AVAILABLE;
        // Display messages and information about each Nobles.
        Utility.display.out.println(message);
        for (Noble noble : nobles) Utility.display.out.println(noble);
        Utility.display.out.println(Messages.NOBLE_SELECTION);
    }

    /**
     * Checks the validity of the player's input for acquiring nobles.
     *
     * @param board The game board.
     * @param player The player making the input.
     * @param input The player's input to be validated.
     * @throws ActionException If the input is invalid.
     */
    @Override
    public void checkInputValidity(Board board, Player player, String input) 
            throws ActionException {
        final String[] inputs = this.validateInputFormat(input, player);
        final int number = Integer.parseInt(inputs[0]);
        this.validateNumber(number, this.getNobles(board, player).length);
    }

    /**
     * Validates the format of the player's input for acquiring nobles.
     *
     * @param input The player's input.
     * @param player The player making the input.
     * @return An array containing the validated input.
     * @throws InvalidInputException If the input format is invalid.
     */
    private String[] validateInputFormat(String input, Player player)
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        if (inputs.length == 1) return inputs;
        throw new InvalidInputException(Messages.NOBLE_INVALID_INPUT);
    }

    /**
     * Validates the chosen number against the number of available nobles.
     *
     * @param number The chosen number.
     * @param noblesCount The total number of available nobles.
     * @throws InvalidNumberException If the chosen number is invalid.
     */
    private void validateNumber(int number, int noblesCount)
            throws InvalidNumberException {
        if (0 < number && number <= noblesCount) return;
        throw new InvalidNumberException(MessageFormat.format(
            Messages.NOBLE_NUMBER_ERROR, noblesCount));
    }

    /**
     * Processes the input for a player, randomly selects a noble from 
     * available nobles, and adds the selected noble to the player.
     *
     * @param board  The game board.
     * @param player The player whose input is being processed.
     */
    @Override
    public void processInput(Board board, Player player, String input) {
        final String[] inputs = input.split(" ");
        final int number = Integer.parseInt(inputs[0]);
        final Noble[] nobles = this.getNobles(board, player);
        final Noble noble = nobles[number - 1];
        this.addNoble(noble, player);
    }
}
