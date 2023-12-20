package com.splendor.actions;

import java.util.Scanner;

import com.splendor.Board;
import com.splendor.Constants;
import com.splendor.exceptions.ActionException;
import com.splendor.player.Player;

public abstract class InputAction implements IAction {
    @Override
    public void process(Board board, Player player) {
        displayAction();
        try {
            String input = readInput();
            checkInputValidity(board, player, input);
            processInput(board, player, input);
        } catch (Exception e) {
            Constants.display.outBoard.println("Erreur de saisie : " + e.getMessage());
        }
    }

    public abstract void displayAction();

    public abstract void checkInputValidity(Board board, Player player, String input) throws ActionException;

    public abstract void processInput(Board board, Player player, String input);

    private String readInput() {
        Scanner scanner = new Scanner(Constants.display.in);
        String input = scanner.nextLine().strip();
        scanner.close();
        return input;
    }
}
