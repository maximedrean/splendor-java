package com.splendor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import com.splendor.actions.BuyCardAction;
import com.splendor.actions.IAction;
import com.splendor.actions.PassAction;
import com.splendor.actions.PickDiffTokensAction;
import com.splendor.actions.PickSameTokensAction;
import com.splendor.player.HumanPlayer;
import com.splendor.player.Player;
import com.splendor.player.RobotPlayer;

/**
 * The Game class represents a game of Splendor, and implements the Displayable
 * interface.
 * A game of Splendor has a board and a list of players.
 */
public class Game {

    /**
     * The board of the game.
     */
    private Board board;

    /**
     * The list of players of the game.
     */
    private ArrayList<Player> players;

    /**
     * The list of actions available to the player.
     */
    private static Map<String, IAction> actions = new Hashtable<String, IAction>();

    static {
        actions.put("A", new PickDiffTokensAction());
        actions.put("B", new PickSameTokensAction());
        actions.put("C", new BuyCardAction());
        actions.put("D", new PassAction());
    }

    /**
     * Entry point for the Splendor game application.
     * Initializes the game, displays a welcome message, and starts the gameplay
     * loop.
     * Finally, closes the display after the game is over.
     *
     * @param args Command line arguments (mandatory but not used here).
     */
    public static void main(String[] args) {
        Constants.display.outBoard.println("Bienvenue sur Splendor !");
        Game game = new Game(2);
        game.play();
        Constants.display.close();
    }

    /**
     * Constructs a new game with the specified number of players.
     *
     * @param players The number of players for the game. Must be between 2 and 4
     *                (inclusive).
     * @throws IllegalArgumentException If the number of players is not within the
     *                                  valid range (2 to 4).
     */
    public Game(int players) throws IllegalArgumentException {
        if (players < 2 || players > 4) {
            throw new IllegalArgumentException("The number of players must be between 2 and 4.");
        }
        this.players = new ArrayList<Player>(Arrays.asList(
                new HumanPlayer("Human player", 0),
                new RobotPlayer("Robot player 1", 1),
                new RobotPlayer("Robot player 2", 2)));
        this.board = new Board();
    }

    /**
     * Retrieves the number of players in the game.
     *
     * @return The number of players currently participating in the game.
     */
    public int getNbPlayers() {
        return this.players.size();
    }

    /**
     * Displays the current state of the game, including the game board and player
     * information.
     *
     * @param currentPlayer The index of the current player whose turn is being
     *                      displayed.
     */
    private void display(int currentPlayer) {
        String[] boardDisplay = board.toStringArray();
        String[] playerDisplay = Display.emptyStringArray(0, 0);
        for (int i = 0; i < this.getNbPlayers(); i++) {
            String[] playerArray = players.get(i).toStringArray();
            if (i == currentPlayer)
                playerArray[0] = "\u27A4 " + playerArray[0];

            playerDisplay = Display.concatStringArray(
                    playerDisplay, playerArray, true);
            String[] emptyString = Display.emptyStringArray(1, Constants.COLS - 54, "┉");
            playerDisplay = Display.concatStringArray(playerDisplay, emptyString, true);
        }

        String[] mainDisplay = Display.concatStringArray(boardDisplay, playerDisplay, false);
        Constants.display.outBoard.clean();
        Constants.display.outBoard.print(String.join("\n", mainDisplay));
    }

    /**
     * Initiates and manages the gameplay loop where each player takes turns making
     * moves until the game is over.
     * After each player's turn, the game state is displayed, and if a player has
     * more resources than the maximum allowed,
     * they must discard tokens.
     * Once the game is over, the appropriate end-of-game procedures are executed.
     */
    public void play() {
        do {
            for (int index = 0; index < this.getNbPlayers(); index++) {
                Player player = this.players.get(index);
                this.display(index);
                this.move(player);
                if (player.getAvailableResources().length > Constants.MAX_NUMBER_RESOURCES_PER_PLAYER) {
                    this.discardToken(player);
                }
            }
        } while (!this.isGameOver());
        this.gameOver();
    }

    /**
     * Facilitates and processes a player's move by presenting available actions and
     * handling the chosen action.
     *
     * @param player The player whose move is being processed.
     */
    private void move(Player player) {
        Constants.display.outBoard.print("Quelle action ?");
        for (IAction action : actions.values())
            Constants.display.outBoard.print(action.toString());
        Scanner scanner = new Scanner(Constants.display.in);
        String choice;
        do {
            choice = scanner.nextLine().strip();
        } while (!actions.containsKey(choice));
        actions.get(choice).process(board, player);
        scanner.close();
    }

    private void discardToken(Player player) {
        // TODO
    }

    /**
     * Checks if the game has reached its end based on the win condition.
     * The game is over if any player has accumulated points equal to or exceeding
     * the win threshold.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean isGameOver() {
        for (Player player : this.players) {
            if (player.getPoints() >= Constants.WIN_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executes end-of-game procedures, announcing the winning player(s) based on
     * the win condition.
     * The winning player(s) are those with points equal to or exceeding the win
     * threshold.
     * If multiple players achieve the win condition, they are all considered
     * winners.
     */
    private void gameOver() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (Player player : this.players) {
            if (player.getPoints() >= Constants.WIN_THRESHOLD) {
                winningPlayers.add(player);
            }
        }

        String[] players = winningPlayers.stream().map(Player::toString).toArray(String[]::new);
        String playersPreview = String.join(", ", players);
        Constants.display.outBoard.println(playersPreview + " won the game!");
    }

}
