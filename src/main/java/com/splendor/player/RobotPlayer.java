package com.splendor.player;

import java.util.Arrays;

import com.splendor.actions.IAction;
import com.splendor.actions.robot.BuyCard;
import com.splendor.actions.robot.DiscardTokens;
import com.splendor.actions.robot.NobleVisit;
import com.splendor.actions.robot.PassAction;
import com.splendor.actions.robot.PickDifferentTokens;
import com.splendor.actions.robot.PickSameTokens;
import com.splendor.board.Board;
import com.splendor.cards.DevCard;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;


/**
 * The RobotPlayer class represents a robot player in the game.
 * This robot player follows a basic strategy for gameplay.
 */
public class RobotPlayer extends Player {

    /**
     * Constructs a new RobotPlayer with the given name and ID.
     *
     * @param name The name of the player.
     * @param id The unique identifier of the player.
     */
    public RobotPlayer(String name, int id) {
        super(name, id);
    }

    /**
     * Creates and returns an action to discard a token.
     *
     * @return An {@code IAction} representing the action to discard tokens.
     */
    public IAction discardToken() {
        return new DiscardTokens();
    }

    /**
     * Creates and returns an action for a noble visit.
     *
     * @param board Unused.
     * @return An {@code IAction} representing the action of a noble visit.
     */
    public IAction nobleVisit(Board board) {
        return new NobleVisit();
    }

    /**
     * Retrieves the highest development card level among the visible cards.
     *
     * @param visibleCards An array of visible development cards.
     * @return The highest development card level, or 0 if there are no 
     *         visible cards.
     */
    private int getHighestLevel(DevCard[] visibleCards) {
        return Arrays.stream(visibleCards).mapToInt(DevCard::getLevel)
            .max().orElse(0);
    }

    /**
     * Retrieves an array of development cards at the specified level from
     * the visible cards.
     *
     * @param visibleCards An array of visible development cards.
     * @param level The level of development cards to retrieve.
     * @return An array containing development cards at the specified level.
     */
    private DevCard[] getCardsAtLevel(DevCard[] visibleCards, int level) {
        return Arrays.stream(visibleCards)
            .filter(card -> card.getLevel() == level)
            .toArray(DevCard[]::new);
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
        DevCard[] visibleCards = board.getVisibleCards();
        int highestLevel = this.getHighestLevel(visibleCards);
        for (int level = highestLevel; level >= 1; level--)
            for (DevCard card : this.getCardsAtLevel(visibleCards, level))
                if (this.canBuyCard(card)) return new BuyCard();
        // If unable to buy a card or to acquire tokens, pass the turn.
        Resource[] availableResources = board.getAvailableResources();
        if (availableResources.length == 0) return new PassAction();
        // Try to take two tokens of the same type.
        for (Resource resource : Resource.values())
            if (board.getNbResource(resource) >= Values.REQUIRED_RESOURCES)
                return new PickSameTokens();
        // Otherwise, try to take tokens of different types.
        return new PickDifferentTokens();
    }
}
