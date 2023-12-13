package com.splendor;
import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.splendor.action.IAction;
import com.splendor.action.BuyCardAction;
import com.splendor.action.PassAction;
import com.splendor.action.PickDiffTokensAction;
import com.splendor.action.PickSameTokensAction;
import com.splendor.player.HumanPlayer;
import com.splendor.player.Player;
import com.splendor.player.RobotPlayer;

public class Game {
    /* L'affichage et la lecture d'entrée avec l'interface de jeu se fera entièrement via l'attribut display de la classe Game.
     * Celui-ci est rendu visible à toutes les autres classes par souci de simplicité.
     * L'intéraction avec la classe Display est très similaire à celle que vous auriez avec la classe System :
     *    - affichage de l'état du jeu (méthodes fournies): Game.display.outBoard.println("Nombre de joueurs: 2");
     *    - affichage de messages à l'utilisateur: Game.display.out.println("Bienvenue sur Splendor ! Quel est ton nom?");
     *    - demande d'entrée utilisateur: new Scanner(Game.display.in);
     */
    private static final int ROWS_BOARD = 36;
    private static final int ROWS_CONSOLE = 8;
    private static final int COLS = 82;
    public static final Display display = new Display(ROWS_BOARD, ROWS_CONSOLE, COLS);

    private Board board;
    private ArrayList<Player> players;
    private static Map<String, IAction> actions = new Hashtable<String, IAction>();

    static {
        actions.put("A", new PickDiffTokensAction());
        actions.put("B", new PickSameTokensAction());
        actions.put("C", new BuyCardAction());
        actions.put("D", new PassAction());
    }

    public static void main(String[] args) {
        //-- à modifier pour permettre plusieurs scénarios de jeu
        display.outBoard.println("Bienvenue sur Splendor !");
        Game game = new Game(2); 
        game.play();
        display.close();
    }

    public Game(int players) throws IllegalArgumentException {
        if (players < 2 || players > 4) throw new IllegalArgumentException(
            "The number of players must be between 2, 3 or 4.");
        this.players = new ArrayList<Player>(Arrays.asList(
            new HumanPlayer("Human player", 0),
            new RobotPlayer("Robot player 1", 1),
            new RobotPlayer("Robot player 2", 2)
        ));
        this.board = new Board();
    }

    public int getNbPlayers() {
        return this.players.size();
    }

    private void display(int currentPlayer) {
        String[] boardDisplay = board.toStringArray();
        String[] playerDisplay = Display.emptyStringArray(0, 0);
        for (int i = 0 ; i < this.getNbPlayers(); i++) {
            String[] playerArray = players.get(i).toStringArray();
            if (i == currentPlayer) playerArray[0] = "\u27A4 " + playerArray[0];

            playerDisplay = Display.concatStringArray(
                playerDisplay, playerArray, true);
            String[] emptyString = Display.emptyStringArray(1, COLS-54, "┉");
            playerDisplay = Display.concatStringArray(playerDisplay, emptyString, true);
        }

        String[] mainDisplay = Display.concatStringArray(boardDisplay, playerDisplay, false);
        display.outBoard.clean();
        display.outBoard.print(String.join("\n", mainDisplay));
    }

    public void play() {
        do {
            for (int index = 0; index < this.getNbPlayers(); index++) {
                Player player = this.players.get(index);
                this.display(index);
                this.move(player);
                if (player.getAvailableResources().length > 10)
                    this.discardToken(player);
            }
        } while(!this.isGameOver());
        this.gameOver();
    }

    private void move(Player player) {
        display.outBoard.print("Quelle action ?");
        for (IAction action : actions.values()) display.outBoard.print(action.toString());
        Scanner scanner = new Scanner(Game.display.in);
        String choice;
        do {
            choice = scanner.nextLine().strip();
        } while (!actions.containsKey(choice));
        actions.get(choice).process(player);
    }

    private void discardToken(Player player) {
    }

    public boolean isGameOver() {
        for (Player player : this.players)
            if (player.getPoints() >= 15) return true;
        return false;
    }

    private void gameOver() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (Player player : this.players)
            if (player.getPoints() >= 15) winningPlayers.add(player);

        String[] players = winningPlayers.toArray(new String[winningPlayers.size()]);
        String playersPreview = String.join(", ", players);
        display.outBoard.println(playersPreview + " won the game!");
    }
}
