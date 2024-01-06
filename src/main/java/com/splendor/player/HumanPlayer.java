package com.splendor.player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import com.splendor.actions.IAction;
import com.splendor.actions.human.BuyCard;
import com.splendor.actions.human.PassAction;
import com.splendor.actions.human.PickDifferentTokens;
import com.splendor.actions.human.PickSameTokens;
import com.splendor.actions.human.ReserveCard;
import com.splendor.actions.human.DiscardTokens;
import com.splendor.actions.human.NobleVisit;
import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Utility;
import com.splendor.constants.Values;


/**
 * Represents a human player in the Splendor board game.
 */
public class HumanPlayer extends Player {

    /**
     * The list of actions available to the player.
     */
    private static final Map<String, IAction> actions = 
        new LinkedHashMap<String, IAction>();

    static {
        HumanPlayer.actions.put("A", new PickSameTokens());
        HumanPlayer.actions.put("B", new PickDifferentTokens());
        HumanPlayer.actions.put("C", new BuyCard());
        HumanPlayer.actions.put("D", new ReserveCard());
        HumanPlayer.actions.put("E", new PassAction());
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
     * @return An {@code IAction} representing the action of a noble visit.
     */
    public IAction nobleVisit(Board board) {
        return new NobleVisit(board);
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
        // Remove all characters that are not letters.
        do choice = scanner.nextLine().replaceAll(Values.INPUT_REGEX, "");
        while (!actions.containsKey(choice));
        scanner.close();
        return actions.get(choice);
    }
}
