package com.splendor.player;

import com.splendor.Board;
import com.splendor.DevCard;
import com.splendor.Resources;
import com.splendor.actions.IAction;
import com.splendor.actions.BuyCardAction;
import com.splendor.actions.TakeTokensAction;
import com.splendor.actions.PassTurnAction;

import java.util.Random;

/**
 * The DumbRobotPlayer class represents a stupid robot player in the game.
 * This robot player follows a basic strategy for gameplay.
 */
public class DumbRobotPlayer extends Player {

    /**
     * Constructs a new DumbRobotPlayer with the given name and ID.
     *
     * @param name The name of the player.
     * @param id   The unique identifier of the player.
     */
    public DumbRobotPlayer(String name, int id) {
        super(name, id);
    }

    /**
     * Chooses an action for the robot player based on a set of simple rules.
     * 
     * @param board The game board.
     * @return The action chosen by the robot player.
     */
    @Override
    public IAction chooseAction(Board board) {
        // Attempt to buy a card, starting with the highest level available.
        for (int level = board.getHighestLevel(); level >= 1; level--) {
            for (DevCard card : board.getCardsAtLevel(level)) {
                if (this.canBuyCard(card)) {
                    return new BuyCardAction(card);
                }
            }
        }

        // If unable to buy a card, try to acquire tokens.
        if (board.areTokensAvailable()) {
            Resources tokens = board.getAvailableTokens();
            // First, try to take two tokens of the same type.
            for (Resource resource : Resource.values()) {
                if (tokens.getNbResource(resource) >= 2) {
                    return new TakeTokensAction(resource, 2);
                }
            }
            // Otherwise, try to take tokens of different types.
            return new TakeTokensAction(tokens); 
        }

        // If no other action is possible, pass the turn.
        return new PassTurnAction();
    }

    /**
     * Chooses tokens to discard when the robot player has more than 10 tokens.
     * 
     * @return The resources to be discarded.
     */
    @Override
    public Resources chooseDiscardingTokens() {
        Resources resourcesToDiscard = new Resources();
        while (this.getTotalTokens() > 10) {
            Resource randomResource = Resource.values()[new Random().nextInt(Resource.values().length)];
            if (this.getNbResource(randomResource) > 0) {
                resourcesToDiscard.updateNbResource(randomResource, 1);
                this.updateNbResource(randomResource, -1);
            }
        }
        return resourcesToDiscard;
    }

    /**
     * Calculates the total number of tokens the player has.
     * 
     * @return The total number of tokens.
     */
    private int getTotalTokens() {
        int total = 0;
        for (Resource resource : Resource.values()) {
            total += this.getNbResource(resource);
        }
        return total;
    }
}
