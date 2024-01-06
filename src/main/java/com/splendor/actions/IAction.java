package com.splendor.actions;

import com.splendor.board.Board;
import com.splendor.player.Player;


/**
 * Interface for all actions.
 */
public interface IAction {

    /**
     * Defines the process to be executed for a specific action on a player.
     *
     * @param board The board on which the action is to be executed.
     * @param player The player on whom the action is to be executed.
     * @return {@code true} if everything happened correctly,
     *         otherwise {@code false}.
     */
    public boolean process(Board board, Player player);

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString();
}
