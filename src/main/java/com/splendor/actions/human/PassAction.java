package com.splendor.actions.human;

import com.splendor.actions.IAction;
import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Utility;
import com.splendor.player.Player;


/**
 * The PassAction class represents an action to pass a turn.
 * This class implements the IAction interface.
 */
public class PassAction implements IAction {

    /**
     * Executes the process for the action of passing a turn, displaying 
     * a message to the player.
     *
     * @param board The game board.
     * @param player The player who chose to pass their turn.
     */
    @Override
    public void process(Board board, Player player) {
        Utility.display.out.println(Messages.TAKE_YOUR_TURN);
    }

    /**
     * Returns a string representation of the action, indicating it is 
     * the action to pass a turn.
     *
     * @return A string representation of the action to pass a turn.
     */
    @Override
    public String toString() {
        return Messages.OPTION_E;
    }
}
