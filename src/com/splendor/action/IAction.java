package com.splendor.action;

import com.splendor.player.Player;

/**
 * Interface for all actions.
 */
public interface IAction {

    /**
     * Defines the process to be executed for a specific action on a player.
     *
     * @param player The player on whom the action is to be executed.
     */
    public void process(Player player);

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString();

}