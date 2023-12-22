package com.splendor.actions;

import java.util.ArrayList;
import java.util.Scanner;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.Noble;
import com.splendor.Resource;
import com.splendor.Resources;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.InvalidNumberException;
import com.splendor.player.Player;

public class NobleVisitAction implements IAction {
    @Override
    public void process(Board board, Player player) {
        Noble[] nobles = getNobleVisitArray(board, player);
        if (nobles.length == 1) {
            Constants.display.outBoard.println("Vous avez reçu la visite d'un noble !");
            Noble noble = nobles[0];
            displayNoble(noble);
            addNoble(noble, player);
        } else if (nobles.length > 1) {
            Constants.display.outBoard.println("Vous avez reçu la visite de plusieurs nobles !");
            for (Noble noble : nobles) {
                displayNoble(noble);
            }
            Constants.display.outBoard.println("Lequel choisissez-vous ?");
            boolean valid = false;
            while (!valid) {
                try {
                    String input = readInput();
                    String[] inputs = validateInputFormat(player, input);
                    int number = parseValue(inputs[0]);
                    validateNumberRange(number, nobles.length);
                    Noble noble = nobles[number - 1];
                    addNoble(noble, player);
                    valid = true;
                } catch (Exception e) {
                    Constants.display.outBoard.println("Erreur de saisie : " + e.getMessage());
                }
            }
        } else {
            Constants.display.outBoard.println("Aucun noble ne peut vous rendre visite.");
        }
    }

    private int parseValue(String input) {
        return Integer.parseInt(input);
    }

    private Noble[] getNobleVisitArray(Board board, Player player) {
        Noble[] nobles = board.getNobles();
        ArrayList<Noble> noblesVisit = new ArrayList<Noble>();
        for (Noble noble : nobles) {
            if (canNobleVisit(noble, player)) {
                noblesVisit.add(noble);
            }
        }
        return noblesVisit.toArray(new Noble[noblesVisit.size()]);
    }

    private boolean canNobleVisit(Noble noble, Player player) {
        Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            if (cost.getNbResource(resource) > player.getNbResource(resource)) {
                return true;
            }
        }
        return false;
    }

    private void addNoble(Noble noble, Player player) {
        player.addPurchasedCard(noble);
        for (Resource resource : noble.getCost().getAvailableResources()) {
            player.updateNbResource(resource, -noble.getCost().getNbResource(resource));
        }
        player.updatePoints(noble);
    }

    private String readInput() {
        Scanner scanner = new Scanner(Constants.display.in);
        String input = scanner.nextLine().strip();
        scanner.close();
        return input;
    }

    public String[] validateInputFormat(Player player, String input) throws InvalidInputException {
        String[] inputs = input.split(" ");
        if (inputs.length != 1) {
            throw new InvalidInputException("Vous devez entrer un nombre correspondant au noble choisi.");
        }
        return inputs;
    }

    private void validateNumberRange(int number, int noblesCount) throws InvalidNumberException {
        if (number > noblesCount) {
            throw new InvalidNumberException(
                    "Le numéro doit être compris entre 1 et " + noblesCount + ".");
        }
    }

    private void displayNoble(Noble noble) {
        Constants.display.outBoard.println(noble.toStringArray());
    }

    public void processInput(Board board, Player player, String input) {

    }
}
