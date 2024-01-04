package com.splendor.actions.robot;

import com.splendor.board.Board;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;
import com.splendor.player.Player;


/**
 * The `PickSameTokensAction` class represents an action where a player 
 * can take two identical tokens from the game board.
 * It extends the `TokenAction` class.
 */
public class PickSameTokens extends Token {

    /**
     * Processes the user input and updates the player's resources 
     * accordingly.
     *
     * @param board The game board.
     * @param player The player performing the action.
     */
    @Override
    public void processInput(Board board, Player player) {
        Resource resource;
        do resource = super.getRandomResource();
        while (!board.canGiveSameTokens(resource));
        player.updateNbResource(resource, Values.SAME_TOKEN_NUMBER);
    }
}
