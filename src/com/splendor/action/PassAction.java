package com.splendor.action;

import com.splendor.player.Player;

/**
 * The PassAction class represents an action to pass a turn.
 * This class implements the IAction interface.
 */
public class PassAction implements IAction {

    /**
     * Defines a default empty process for an action on a player.
     * This method does not perform any specific action and serves as a placeholder.
     *
     * @param player The player on whom the empty action is applied.
     */
    @Override
    public void process(Player player) {
        // Do nothing.
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
