package com.splendor.actions.robot;

import java.util.Random;

import com.splendor.Resources;
import com.splendor.actions.RobotAction;
import com.splendor.board.Board;
import com.splendor.cards.DevCard;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;
import com.splendor.player.Player;


/**
 * The BuyCard class represents the action of buying a card from the
 * board. It extends the InputAction class.
 */
public class BuyCard extends RobotAction {

    /**
     * Processes the valid input and updates the player's purchased 
     * cards, points, and the board.
     *
     * @param board The game board.
     * @param player The player performing the action.
     */
    @Override
    public void processInput(Board board, Player player) {
        DevCard card;
        if (new Random().nextBoolean()) { // Reserved card.
            final int number = new Random().nextInt(Values.TIER_NUMBER);
            // Get the reserved card at the specified index.
            card = player.getReservedCards()[number - 1];
            // Remove the reserved card from the player's reserved cards.
            player.removeReservedCard(number - 1);
        } else { // Default card.
            final int tier = new Random().nextInt(Values.TIER_NUMBER);
            final int column = new Random().nextInt(Values.TIER_NUMBER);
            // Get the card at the specified tier and column
            card = board.getCard(tier, column);
            // Update the board by removing the card.
            board.updateCard(card, tier, column);
        }
        // Add the processed card to the player's purchased cards.
        player.addPurchasedCard(card);
        // Update the player's points based on the processed card
        player.updatePoints(card);
        Resources resourceCosts = card.getCost();
        Resource[] resources = resourceCosts.getAvailableResources();
        // Iterate through the resources and deduct the quantity.
        for (final Resource resource : resources) {
            int quantity = resourceCosts.getNbResource(resource);
            // Update the player's resource quantity.
            player.updateNbResource(resource, -quantity);
        }
    }
}
