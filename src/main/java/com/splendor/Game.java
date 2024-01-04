package com.splendor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.splendor.actions.IAction;
import com.splendor.actions.human.DiscardTokens;
import com.splendor.actions.human.NobleVisit;
import com.splendor.board.Board;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Symbols;
import com.splendor.constants.Utility;
import com.splendor.constants.Values;
import com.splendor.display.Display;
import com.splendor.exceptions.CardReaderException;
import com.splendor.player.HumanPlayer;
import com.splendor.player.Player;
import com.splendor.player.RobotPlayer;


/**
 * The Game class represents a game of Splendor, and implements 
 * the Displayable interface.
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
    private Player[] players;

    private static HashMap<Integer, Integer[]> 
        defaultResources = new HashMap<Integer, Integer[]>();

    static {
        defaultResources.put(2, new Integer[]{4, 5, 3}); // For 2 players.
        defaultResources.put(3, new Integer[]{5, 5, 4}); // For 3 players.
        defaultResources.put(4, new Integer[]{7, 5, 5}); // For 4 players.
    }

    /**
     * Constructs a new game with the specified number of players.
     *
     * @param players The number of players for the game. Must be 
     *        between 2 and 4 included.
     * @throws IllegalArgumentException If the number of players is 
     *         not within the valid range (2 to 4).
     */
    public Game(int playersCount) 
            throws IllegalArgumentException, CardReaderException {
        if (playersCount < 2 || 4 < playersCount) throw new 
            IllegalArgumentException(Messages.PLAYERS_NUMBER_ERROR);
        this.players = new Player[playersCount];
        this.addPlayers(playersCount);
        this.board = new Board();
        this.setDefaultResources(playersCount);
    }

    /**
     * Adds players to the game. The first player is a {@code HumanPlayer}, 
     * and the rest are {@code RobotPlayer} instances.
     *
     * @param players The number of players to add to the game.
     */
    private final void addPlayers(final int players) {
        this.players[0] = new HumanPlayer(Messages.HUMAN, 0);
        for (int index = 1; index < players; index++) {
            final String name = MessageFormat.format(Messages.ROBOT, index);
            this.players[index] = new RobotPlayer(name, index);
        }
    }

    /**
     * Sets default resources on the game board based on the 
     * number of players.
     *
     * @param playersCount The number of players in the game.
     */
    private final void setDefaultResources(int playersCount) {
        Integer[] resources = Game.defaultResources.get(playersCount);
        for (Resource resource : Resource.values())
            this.board.setNbResource(resource, resources[0]);
    }

    /**
     * Retrieves the number of players in the game.
     *
     * @return The number of players currently participating in the game.
     */
    private int getNbPlayers() {
        return this.players.length;
    }

    /**
     * Displays the current state of the game, including the game 
     * board and player information.
     *
     * @param currentPlayer The index of the current player whose 
     *        turn is being displayed.
     */
    private void display(int currentPlayer) {
        final String[] boardDisplay = board.toStringArray();
        String[] playerDisplay = Display.emptyStringArray(0, 0);
        for (int index = 0; index < this.getNbPlayers(); index++) {
            final String[] playerArray = players[index].toStringArray();

            if (index == currentPlayer)
                playerArray[0] = Symbols.ARROW + playerArray[0];

            playerDisplay = Display.concatStringArray(
                    playerDisplay, playerArray, true);
            final String[] emptyString = Display.emptyStringArray(
                1, Values.COLUMNS_CONSOLE - 54, Symbols.DELIMITER);
            playerDisplay = Display.concatStringArray(
                playerDisplay, emptyString, true);
        }

        String[] mainDisplay = Display.concatStringArray(
            boardDisplay, playerDisplay, false);
        Utility.display.outBoard.clean();
        Utility.display.outBoard.print(String.join("\n", mainDisplay));
    }

    /**
     * Initiates and manages the gameplay loop where each player takes turns 
     * making moves until the game is over. After each player's turn, the game
     * state is displayed, and if a player has more resources than the maximum 
     * allowed, they must discard tokens. Once the game is over, the 
     * appropriate end-of-game procedures are executed.
     */
    public void play() {
        while (!this.isGameOver())
            for (int index = 0; index < this.getNbPlayers(); index++) {
                final Player player = this.players[index];
                this.display(index);  // Select the player on the board.
                this.move(player);  // Start the round of the player.
                final int resources = player.getAvailableResources().length;
                if (resources > Values.MAX_NUMBER_RESOURCES_PER_PLAYER)
                    this.discardTokens(player);
            }
        this.gameOver();
    }

    /**
     * Checks if the game has reached its end based on the win condition.
     * The game is over if any player has accumulated points equal to or 
     * exceeding the win threshold.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean isGameOver() {
        for (Player player : this.players)
            if (player.getPoints() >= Values.WIN_THRESHOLD) return true;
        return false;
    }

    /**
     * Executes end-of-game procedures, announcing the winning player(s) 
     * based on the win condition. The winning player(s) are those with 
     * points equal to or exceeding the win threshold. If multiple players 
     * achieve the win condition, they are all considered winners.
     */
    private void gameOver() {
        final ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (Player player : this.players)
            if (player.getPoints() >= Values.WIN_THRESHOLD)
                winningPlayers.add(player);
        // Retrieve all the names of the winning player(s)
        // and format it/them with delimiters into the victory message.
        final String[] players = winningPlayers.stream()
            .map(Player::toString).toArray(String[]::new);
        final String playersPreview = String.join(", ", players);
        String message = players.length > 1 ? Messages.WINS : Messages.WIN;
        message = MessageFormat.format(message, playersPreview);
        Utility.display.outBoard.println(message);
    }

    /**
     * Facilitates and processes a player's move by presenting available 
     * actions and handling the chosen action.
     *
     * @param player The player whose move is being processed.
     */
    private void move(Player player) {
        final IAction action = player.chooseAction(board);
        Utility.display.out.clean();
        action.process(board, player);
        Utility.display.out.clean();
    }

    private void discardTokens(Player player) {
        final DiscardTokens action = new DiscardTokens();
        action.process(board, player);
        Utility.display.out.clean();
    }

    private void nobleVisit(Player player) {
        final NobleVisit action = new NobleVisit();
        action.process(board, player);
        Utility.display.out.clean();
    }
}
