package com.splendor.actions.robot;

import java.util.ArrayList;
import java.util.Random;

import com.splendor.actions.RobotAction;
import com.splendor.board.Board;
import com.splendor.board.Resources;
import com.splendor.cards.Noble;
import com.splendor.constants.Resource;
import com.splendor.player.Player;


/**
 * The {@code NobleVisit} class represents a robot action of visiting 
 * a noble. It extends the {@code RobotAction} class, inheriting basic 
 * robot action behavior. This class encapsulates the specific details 
 * and logic related to a robot visiting a noble.
 */
public class NobleVisit extends RobotAction {

    /**
     * Gets the nobles from the board that a player can visit.
     *
     * @param board The game board containing the nobles.
     * @param player The player for whom to check if they can visit 
     *        the nobles.
     * @return An array of nobles that the player can visit.
     */
    private Noble[] getNobles(Board board, Player player) {
        final ArrayList<Noble> noblesVisit = new ArrayList<Noble>();
        for (Noble noble : board.getNobles())
            if (noble != null && this.canNobleVisit(noble, player))
                noblesVisit.add(noble);
        return noblesVisit.toArray(new Noble[noblesVisit.size()]);
    }

    /**
     * Checks if a player can visit a noble based on the required 
     * resources from purchased cards.
     *
     * @param noble The noble to be visited.
     * @param player The player attempting to visit the noble.
     * @return {@code true} if the player can visit the noble, 
     *         {@code false} otherwise.
     */
    private boolean canNobleVisit(Noble noble, Player player) {
        final Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            final int playerResources = player.getNbResource(resource);
            if (cost.getNbResource(resource) > playerResources) return false;
        }
        return true;
    }

    /**
     * Adds a noble to the player's purchased cards, deducts the required 
     * resources, and updates the player's points accordingly.
     *
     * @param noble  The noble to be added to the player.
     * @param player The player who receives the noble.
     */
    private void addNoble(Noble noble, Player player) {
        player.addPurchasedCard(noble);
        final Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            final int quantity = cost.getNbResource(resource);
            for (int index = 0; index < quantity; index++)
                player.removePurchasedCard(resource);
        }
        player.updatePoints(noble);
    }

    /**
     * Processes the input for a player, randomly selects a noble from 
     * available nobles, and adds the selected noble to the player.
     *
     * @param board  The game board.
     * @param player The player whose input is being processed.
     */
    @Override
    public void processInput(Board board, Player player) {
        Noble[] nobles = this.getNobles(board, player);
        if (nobles.length == 0) return; // No Nobles can be visited.
        final int number = new Random().nextInt(nobles.length);
        final Noble noble = nobles[number];
        this.addNoble(noble, player);
    }
}
