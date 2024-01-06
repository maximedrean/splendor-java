package com.splendor.actions.robot;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.splendor.actions.RobotAction;
import com.splendor.board.Board;
import com.splendor.board.Resources;
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
        // Extract the non null reserved cards from the fixed array.
        final DevCard[] reservedCards = player.getReservedCards();
        final List<DevCard> nonNullCards = Arrays.stream(reservedCards)
            .filter(Objects::nonNull).collect(Collectors.toList());
        final int reservedCardsLength = nonNullCards.size();
        // Randomly purchase a reserved card or card on the board.
        if (reservedCardsLength > 0 && new Random().nextBoolean()) {
            final int number = new Random().nextInt(reservedCardsLength);
            // Get the reserved card at the specified index.
            card = nonNullCards.get(number);
            // Remove the reserved card from the player's reserved cards.
            player.removeReservedCard(number);
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
        final Resources resourceCosts = card.getCost();
        final Resource[] resources = resourceCosts.getAvailableResources();
        // Iterate through the resources and deduct the quantity.
        for (final Resource resource : resources) {
            final int quantity = resourceCosts.getNbResource(resource);
            // Update the player's resource quantity.
            player.updateNbResource(resource, -quantity);
        }
    }
}
