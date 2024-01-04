package com.splendor.player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.splendor.Resources;
import com.splendor.actions.IAction;
import com.splendor.actions.human.BuyCard;
import com.splendor.actions.human.PassAction;
import com.splendor.actions.human.PickDifferentTokens;
import com.splendor.actions.human.PickSameTokens;
import com.splendor.actions.human.ReserveCard;
import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;
import com.splendor.constants.Values;


/**
 * Represents a human player in the Splendor board game.
 */
public class HumanPlayer extends Player {

    /**
     * The list of actions available to the player.
     */
    private static Map<String, IAction> actions = 
        new LinkedHashMap<String, IAction>();

    static {
        actions.put("A", new PickSameTokens());
        actions.put("B", new PickDifferentTokens());
        actions.put("C", new BuyCard());
        actions.put("D", new ReserveCard());
        actions.put("E", new PassAction());
    }

    /**
     * Constructs a new HumanPlayer with the specified name and ID.
     *
     * @param name The name of the human player.
     * @param id The unique ID of the human player.
     */
    public HumanPlayer(String name, int id) {
        super(name, id);
    }

    /**
     * Allows the human player to choose an action from the available options.
     *
     * @param board The game board on which the action is performed.
     * @return The chosen action.
     */
    public IAction chooseAction(Board board) {
        Utility.display.out.clean();
        Utility.display.out.println(Messages.ACTION_SELECTION);
        for (final IAction action : actions.values())
            Utility.display.out.println(action.toString());
        final Scanner scanner = new Scanner(Utility.display.in);
        String choice;
        do choice = scanner.nextLine().strip();
        while (!actions.containsKey(choice));
        scanner.close();
        return actions.get(choice);
    }

    /**
     * Allows the human player to choose resources for discarding.
     *
     * @return The resources chosen for discarding.
     */
    public Resources chooseDiscardingTokens() {
        // TODO: scanner.
        final Resources resourcesToDiscard = new Resources();
        while (this.getTotalTokens() > Values.TOKEN_LIMIT) {
            final Resource randomResource = Resource.values()[
                new Random().nextInt(Resource.values().length)];
            if (this.getNbResource(randomResource) > 0) {
                resourcesToDiscard.updateNbResource(randomResource, 1);
                this.updateNbResource(randomResource, -1);
            }
        }
        return resourcesToDiscard;
    }
}
