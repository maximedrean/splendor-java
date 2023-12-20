package com.splendor.actions;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.player.Player;

/**
 * The PassAction class represents an action to pass a turn.
 * This class implements the IAction interface.
 */
public class PassAction implements IAction {

    /**
     * Executes the process for the action of passing a turn, displaying a message
     * to the player.
     *
     * @param board  The game board.
     * @param player The player who chose to pass their turn.
     */
    @Override
    public void process(Board board, Player player) {
        Constants.display.outBoard.println("Vous avez choisi de passer votre tour.");
    }

    /**
     * Returns a string representation of the action, indicating it is the action to
     * pass a turn.
     *
     * @return A string representation of the action to pass a turn.
     */
    @Override
    public String toString() {
        return "D: Passer son tour";
    }
}
