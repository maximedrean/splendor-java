package com.splendor.actions.robot;

import com.splendor.actions.IAction;
import com.splendor.board.Board;
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
     * @return {@code true}.
     */
    @Override
    public boolean process(Board board, Player player) {
        return true;
    }
}
