package com.splendor.actions.human;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.splendor.Resources;
import com.splendor.actions.IAction;
import com.splendor.board.Board;
import com.splendor.cards.Noble;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;
import com.splendor.exceptions.InvalidInputException;
import com.splendor.exceptions.InvalidNumberException;
import com.splendor.player.Player;


public class NobleVisit implements IAction {

    @Override
    // Main method for processing a noble's visit action.
    public void process(Board board, Player player) {
        final Noble[] nobles = getNobleVisitArray(board, player);

        if (nobles.length <= 0) {
            Utility.display.out.println("Aucun noble ne peut vous rendre visite.");
            return;
        }

        if (nobles.length == 1) {
            Utility.display.out.println("Vous avez reçu la visite d'un noble !");
            final Noble noble = nobles[0];
            this.displayNoble(noble); // Display the noble's information.
            this.addNoble(noble, player); // Add the noble to the player.
            return;
        }

        Utility.display.out.println("Vous avez reçu la visite de plusieurs nobles !");
        for (Noble noble : nobles) displayNoble(noble);
        Utility.display.out.println("Lequel choisissez-vous ?");
        while (true) {
            try {
                final String input = readInput();
                final String[] inputs = this.validateInput(player, input);
                final int number = Integer.parseInt(inputs[0]);
                this.validateNumber(number, nobles.length);
                Noble noble = nobles[number - 1];
                addNoble(noble, player);
                break;
            } catch (Exception exception) {
                Utility.display.out.println(MessageFormat.format(
                    Messages.INPUT_ERROR, exception.getMessage()));
            }
        }
    }

    private Noble[] getNobleVisitArray(Board board, Player player) {
        final Noble[] nobles = board.getNobles();
        final ArrayList<Noble> noblesVisit = new ArrayList<Noble>();
        for (Noble noble : nobles)
            if (this.canNobleVisit(noble, player)) noblesVisit.add(noble);
        return noblesVisit.toArray(new Noble[noblesVisit.size()]);
    }

    private boolean canNobleVisit(Noble noble, Player player) {
        final Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            final int playerResources = player.getNbResource(resource);
            if (cost.getNbResource(resource) > playerResources) return true;
        }
        return false;
    }

    private void addNoble(Noble noble, Player player) {
        player.addPurchasedCard(noble);
        final Resources cost = noble.getCost();
        for (Resource resource : cost.getAvailableResources()) {
            final int quantity = cost.getNbResource(resource);
            player.updateNbResource(resource, -quantity);
        }
        player.updatePoints(noble);
    }

    private String readInput() {
        final Scanner scanner = new Scanner(Utility.display.in);
        final String input = scanner.nextLine().strip();
        scanner.close();
        return input;
    }

    public String[] validateInput(Player player, String input) 
            throws InvalidInputException {
        final String[] inputs = input.split(" ");
        if (inputs.length == 1) return inputs;
        throw new InvalidInputException("Vous devez entrer un nombre correspondant au noble choisi.");
    }

    private void validateNumber(int number, int noblesCount)
            throws InvalidNumberException {
        if (number <= noblesCount) return;
        throw new InvalidNumberException(
                "Le numéro doit être compris entre 1 et " + noblesCount + ".");
    }

    // Display a noble's information.
    private void displayNoble(Noble noble) {
        Utility.display.out.println(noble.toStringArray());
    }

    public void processInput(Board board, Player player, String input) {

    }
}
