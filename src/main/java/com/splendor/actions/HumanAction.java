package com.splendor.actions;

import java.text.MessageFormat;
import java.util.Scanner;

import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Utility;
import com.splendor.constants.Values;
import com.splendor.exceptions.ActionException;
import com.splendor.player.Player;


/**
 * An abstract base class for input-based actions in the Splendor board game.
 * Provides a template method structure for processing player input.
 */
public abstract class HumanAction implements IAction {

    /**
     * Processes the input for the action, displaying the action to the 
     * player, checking input validity, and handling the input until it 
     * is valid.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     * @return {@code true} if everything happened correctly,
     *         otherwise {@code false}.
     */
    @Override
    public boolean process(Board board, Player player) {
        this.displayAction(player);
        try {
            final String input = this.readInput();
            this.checkInputValidity(board, player, input);
            this.processInput(board, player, input);
            return true;
        } catch (Exception exception) {
            Utility.display.out.println(MessageFormat.format(
                Messages.INPUT_ERROR, exception.getMessage()));
            return false;
        }
    }

    /**
     * Displays information about the action to the player.
     *
     * @param player The player for whom the action is displayed.
     */
    public abstract void displayAction(Player player);

    /**
     * Checks the validity of the player's input for the specific action.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     * @param input The player's input to be checked for validity.
     * @throws ActionException If the input is invalid for the action.
     */
    public abstract void checkInputValidity(
        Board board, Player player, String input) throws ActionException;

    /**
     * Processes the valid input for the specific action.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     * @param input The valid player's input to be processed.
     */
    public abstract void processInput(
        Board board, Player player, String input);

    /**
     * Reads the player's input using the standard input stream.
     *
     * @return The player's input as a string.
     */
    private String readInput() {
        final Scanner scanner = new Scanner(Utility.display.in);
        String input = scanner.nextLine().strip();
        // Remove all characters that are not letters.
        input = input.replaceAll(Values.INPUT_REGEX, "");
        scanner.close();
        return input;
    }
}
