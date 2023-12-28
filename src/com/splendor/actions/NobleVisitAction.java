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
    // Main method for processing a noble's visit action.
    public void process(Board board, Player player) {
        Noble[] nobles = getNobleVisitArray(board, player);
        // Handle the different cases depending on the number of nobles available for the visit.
        if (nobles.length == 1) {
            // Only one noble available.
            Constants.display.outBoard.println("Vous avez reçu la visite d'un noble !");
            Noble noble = nobles[0];
            displayNoble(noble); // Display the noble's information.
            addNoble(noble, player);// Add the noble to the player.
        } else if (nobles.length > 1) { 
            // Several nobles available.
            Constants.display.outBoard.println("Vous avez reçu la visite de plusieurs nobles !");
            for (Noble noble : nobles) {
                displayNoble(noble); // Shows the information of each noble.
            }
            Constants.display.outBoard.println("Lequel choisissez-vous ?");
            boolean valid = false;
            while (!valid) {
                try {
                    String input = readInput(); // Read user input.
                    String[] inputs = validateInputFormat(player, input); // Validate the format of the input.
                    int number = parseValue(inputs[0]); // Convert the input to a number.
                    validateNumberRange(number, nobles.length);// Check that the number is in the valid range.
                    Noble noble = nobles[number - 1];
                    addNoble(noble, player);// Adds the chosen noble to the player
                    valid = true;
                } catch (Exception e) {
                    // Handle input errors.
                    Constants.display.outBoard.println("Erreur de saisie : " + e.getMessage());
                }
            }
        } else {
            // No nobles available.
            Constants.display.outBoard.println("Aucun noble ne peut vous rendre visite.");
        }
    }

    // Convert a string to an integer.
    private int parseValue(String input) {
        return Integer.parseInt(input);
    }

    // Returns an array of nobles who can visit the player.
    private Noble[] getNobleVisitArray(Board board, Player player) {
        Noble[] nobles = board.getNobles();  // Collect all the nobles on the board.
        ArrayList<Noble> noblesVisit = new ArrayList<Noble>();
        // Selects nobles who can visit the player.
        for (Noble noble : nobles) {
            if (canNobleVisit(noble, player)) {
                noblesVisit.add(noble);
            }
        }
        return noblesVisit.toArray(new Noble[noblesVisit.size()]);
    }

    // Checks if a noble can visit the player based on their resources.
    private boolean canNobleVisit(Noble noble, Player player) {
        Resources cost = noble.getCost(); // Get the cost for the noble's visit.
        // Checks if the player has enough resources for each type required by the noble.
        for (Resource resource : cost.getAvailableResources()) {
            if (cost.getNbResource(resource) > player.getNbResource(resource)) {
                return true;
            }
        }
        return false;
    }

    // Add a noble to the player and update his resources and points.
    private void addNoble(Noble noble, Player player) {
        player.addPurchasedCard(noble);// Add the noble to the player.
        // Updates player resources based on noble cost.
        for (Resource resource : noble.getCost().getAvailableResources()) {
            player.updateNbResource(resource, -noble.getCost().getNbResource(resource));
        }
        player.updatePoints(noble);// Updates the player's points.
    }

    // Read user input from the console.
    private String readInput() {
        Scanner scanner = new Scanner(Constants.display.in);
        String input = scanner.nextLine().strip(); // Reads and cleans the entry.
        scanner.close();
        return input;
    }

   // Validate the format of user input.
    public String[] validateInputFormat(Player player, String input) throws InvalidInputException {
        String[] inputs = input.split(" "); // Divide the input into several parts.
        if (inputs.length != 1) {
            // Throw an exception if the format is not a unique number.
            throw new InvalidInputException("Vous devez entrer un nombre correspondant au noble choisi.");
        }
        return inputs;
    }

    // Check that the entered number is in the valid range.
    private void validateNumberRange(int number, int noblesCount) throws InvalidNumberException {
        if (number > noblesCount) {
            // Throw an exception if the number is not in the valid range.
            throw new InvalidNumberException(
                    "Le numéro doit être compris entre 1 et " + noblesCount + ".");
        }
    }

    // Display a noble's information.
    private void displayNoble(Noble noble) {
        Constants.display.outBoard.println(noble.toStringArray());
    }

    public void processInput(Board board, Player player, String input) {

    }
}
