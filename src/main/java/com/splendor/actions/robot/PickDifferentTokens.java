package com.splendor.actions.robot;

import java.util.Random;

import com.splendor.board.Board;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;
import com.splendor.player.Player;


/**
 * The `PickDiffTokensAction` class represents an action where a player 
 * can take three different tokens from the game board.
 * It extends the `TokenAction` class.
 */
public class PickDifferentTokens extends Token {

    private Resource[] getRandomResources() {
        return new Random()
            .ints(Values.DIFF_TOKEN_LIMIT, 0, Resource.values().length)
            .mapToObj(index -> Resource.values()[index])
            .toArray(Resource[]::new);
    }

    /**
     * Processes the user input and updates the player's resources accordingly.
     *
     * @param board The game board.
     * @param player The player performing the action.
     */
    @Override
    public void processInput(Board board, Player player) {
        Resource[] resources;
        do resources = this.getRandomResources();
        while(!board.canGiveDiffTokens(resources));
        for (final Resource resource : resources)
            player.updateNbResource(resource, Values.DIFF_TOKEN_NUMBER);
    }
}
