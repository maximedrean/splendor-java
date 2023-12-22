package com.splendor.actions;

import java.util.Map;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.Resource;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.player.Player;

public class DiscardTokensAction extends TokenAction {

    static {
        resources.put("F", Resource.JOKER);
    }

    @Override
    public void displayAction(Player player) {
        Constants.display.outBoard.println("Quelles ressources ?");
        displayResources();
        Constants.display.outBoard.println("Entrez les lettres correspondant aux ressources séparées par un espace.");
        Constants.display.outBoard
                .println("Exemple : A A B E (pour les ressources EMERALD, EMERALD, DIAMOND et RUBY).");
    }

    @Override
    public void checkInputValidity(Board board, Player player, String input) throws ActionException {
        String[] inputs = validateInputFormat(input, player);
        Resource[] resources = extractResources(inputs);
        validateBoardResources(board, resources);
    }

    private String[] validateInputFormat(String input, Player player) throws InvalidInputException {
        String[] inputs = input.split(" ");
        int number = player.getAvailableResources().size();
        if (inputs.length != 3 || !resources.containsKey(inputs[0]) || !resources.containsKey(inputs[1])
                || !resources.containsKey(inputs[2])) {
            throw new InvalidInputException(
                    "Vous devez entrer trois lettres correspondant aux ressources séparées par un espace.");
        }
        return i;
    }

    private getNumberResourcesToDiscard(String input, Player player) throws InvalidInputException {
        int number = player.getAvailableResources().size();
    }
    // TODO
}
