package com.splendor.actions.robot;

import com.splendor.board.Board;
import com.splendor.constants.Resource;
import com.splendor.player.Player;


/**
 * Represents an action where a player can discard excess tokens/resources 
 * in the Splendor board game. Extends Token and provides specific 
 * implementation for discarding tokens.
 */
public class DiscardTokens extends Token {

    /**
     * Processes the valid input for the discard tokens action, updating 
     * the player's and board's resources accordingly.
     *
     * @param board The game board on which the action is performed.
     * @param player The player performing the action.
     */
    @Override
    public void processInput(Board board, Player player) {
        final int quantity = player.getNumberResourcesToDiscard();
        for (int index = 0; index < quantity; index++) {
            Resource resource;
            // Take a random token until the quantity is sufficient.
            do resource = super.getRandomResource();
            while (player.getNbResource(resource) >= 1);
            player.updateNbResource(resource, -1);
            board.updateNbResource(resource, 1);
        }
    }
}
