package com.splendor.actions;

import java.text.MessageFormat;

import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Utility;
import com.splendor.player.Player;


/**
 * An abstract base class for input-based actions in the Splendor board game.
 * Provides a template method structure for processing player input.
 */
public abstract class RobotAction implements IAction {

    /**
     * Processes the valid input for the specific action.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     * @param input The valid player's input to be processed.
     */
    public abstract void processInput(Board board, Player player);

    /**
     * Processes the input for the action, displaying the action to the 
     * player, checking input validity, and handling the input until it 
     * is valid.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     */
    @Override
    public void process(Board board, Player player) {
        while (true) {
            try {
                this.processInput(board, player);
                return;
            } catch (Exception exception) {
                Utility.display.out.println(MessageFormat.format(
                    Messages.INPUT_ERROR, exception.getMessage()));
            }
        }
    }
}
