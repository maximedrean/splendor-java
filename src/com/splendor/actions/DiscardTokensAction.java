package com.splendor.actions;

import java.util.HashMap;
import java.util.Map;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.DevCard;
import com.splendor.Resource;
import com.splendor.exceptions.ActionException;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.NotEnoughResourcesException;
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
        HashMap<Resource, Integer> resources = extractResources(inputs);
        validatePlayerResources(player, resources);
    }

    private String[] validateInputFormat(String input, Player player) throws InvalidInputException {
        String[] inputs = input.split(" ");
        if (inputs.length != getNumberResourcesToDiscard(player)) {
            throw new InvalidInputException("Vous devez entrer " + getNumberResourcesToDiscard(player)
                    + " lettres correspondant aux ressources séparées par un espace.");
        }
        for (String letter : inputs) {
            if (!resources.containsKey(letter)) {
                throw new InvalidInputException("Vous devez entrer des lettres correspondant aux ressources.");
            }
        }
        return inputs;
    }

    /**
     * Validates that the player has enough resources to buy the specified card.
     *
     * @param player The player performing the action.
     * @param card   The DevCard object to be purchased.
     * @throws NotEnoughResourcesException If the player does not have enough
     *                                     resources to buy the card.
     */
    private void validatePlayerResources(Player player, HashMap<Resource, Integer> map)
            throws NotEnoughResourcesException {
        for (Map.Entry<Resource, Integer> entry : map.entrySet()) {
            Resource resource = entry.getKey();
            int value = entry.getValue();
            if (player.getNbResource(resource) < value) {
                throw new NotEnoughResourcesException("Vous n'avez pas assez de ressources à défausser.");
            }
        }
    }

    private int getNumberResourcesToDiscard(Player player) {
        int sum = 0;
        for (Resource resource : player.getAvailableResources()) {
            sum += player.getNbResource(resource);
        }
        return sum - Constants.MAX_NUMBER_RESOURCES_PER_PLAYER;
    }

    private HashMap<Resource, Integer> extractResources(String[] inputs) {
        HashMap<Resource, Integer> map = new HashMap<>();
        for (String letter : inputs) {
            Resource resource = resources.get(letter);
            if (map.containsKey(resource)) {
                map.put(resource, map.get(resource) + 1);
            } else {
                map.put(resource, 1);
            }
        }
        return map;
    }

    @Override
    public void processInput(Board board, Player player, String input) {
        String[] inputs = input.split(" ");
        HashMap<Resource, Integer> resources = extractResources(inputs);
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            Resource resource = entry.getKey();
            int value = entry.getValue();
            player.updateNbResource(resource, -value);
            board.updateNbResource(resource, value);
        }
    }
}
