package com.splendor;

import com.splendor.cards.DevCard;
import com.splendor.constants.Resource;
import com.splendor.player.HumanPlayer;
import com.splendor.player.Player;

public class PlayerTest {

    public static void main(String[] args) {
        Player player = new HumanPlayer("Camille", 0);

        Resources cost = new Resources();
        cost.setNbResource(Resource.DIAMOND, 2);
        cost.setNbResource(Resource.EMERALD, 1);
        cost.setNbResource(Resource.SAPPHIRE, 3);
        DevCard card = new DevCard(1, cost, 2, Resource.DIAMOND);
        System.out.println(player.canBuyCard(card));
    }
}
