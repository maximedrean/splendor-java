package com.splendor.actions.robot;

import java.util.Random;

import com.splendor.actions.RobotAction;
import com.splendor.board.Board;
import com.splendor.cards.DevCard;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;
import com.splendor.player.Player;


public class ReserveCard extends RobotAction {

    /**
     * Processes the valid input and updates the player's purchased cards, 
     * points, and the board.
     *
     * @param board The game board.
     * @param player The player performing the action.
     */
    @Override
    public void processInput(Board board, Player player) {
        final int tier = new Random().nextInt(Values.TIER_NUMBER) + 1;
        final DevCard newCard = board.drawCard(tier);
        if (new Random().nextBoolean()) {
            final int column = new Random().nextInt(Values.TIER_NUMBER);
            final DevCard card = board.getCard(tier, column);
            board.updateCard(newCard, tier, column);
            player.addReservedCard(card);
        } else player.addReservedCard(newCard);
        player.updateNbResource(Resource.JOKER, 1);
    }
}
