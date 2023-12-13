import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;

public class Board implements Displayable {

    /* --- Stringers --- */
    
    private HashMap<Resource, Integer> resources = new HashMap<>();
    private List<Stack<DevCard>> stackCards; // Piles de cartes pour chaque niveau
    private DevCard[][] visibleCards; // Cartes visibles organisées par niveau et position
    
    /* --- Méthodes de gestion des ressources --- */
    public int getNbResource(Resource type) {
        return resources.getOrDefault(type, 0);
    }

    public void setNbResource(Resource type, int nb) {
        resources.put(type, nb);
    }

    public void updateNbResource(Resource type, int v) {
        int currentNb = getNbResource(type);
        resources.put(type, Math.max(0, currentNb + v));
    }

    public Set<Resource> getAvailableResources() {
        return resources.keySet();
    }

    /* --- Méthodes de gestion des cartes --- */
    public DevCard getCard(int tier, int column) {
        return visibleCards[tier][column];
    }

    public void updateCard(DevCard card, int tier, int column) {
        if (!stackCards.get(tier).isEmpty()) {
            visibleCards[tier][column] = stackCards.get(tier).pop();
        } else {
            visibleCards[tier][column] = null;
        }
    }

    public DevCard drawCard(int tier) {
        if (!stackCards.get(tier).isEmpty()) {
            return stackCards.get(tier).pop();
        }
        return null;
    }

    public boolean canGiveSameTokens(Resource resource) {
        return getNbResource(resource) >= 4;
    }

    public boolean canGiveDiffTokens(List<Resource> resourcesList) {
        for (Resource res : resourcesList) {
            if (getNbResource(res) < 1) {
                return false;
            }
        }
        return true;
    }

    private String[] deckToStringArray(int tier){
        /** EXAMPLE
         * ┌────────┐
         * │        │╲ 
         * │ reste: │ │
         * │   16   │ │
         * │ cartes │ │
         * │ tier 3 │ │
         * │        │ │
         * └────────┘ │
         *  ╲________╲│
         */
        int nbCards = stackCards.get(tier).size(); // Remplacé par le nombre de cartes dans le deck pour le niveau donné
        String[] deckStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  ",
                            "\u2502        \u2502\u2572 ",
                            "\u2502 reste: \u2502 \u2502",
                            "\u2502   "+String.format("%02d", nbCards)+"   \u2502 \u2502",
                            "\u2502 carte"+(nbCards>1 ? "s" : " ")+" \u2502 \u2502",
                            "\u2502 tier "+tier+" \u2502 \u2502",
                            "\u2502        \u2502 \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 \u2502",
                            " \u2572________\u2572\u2502"};
        return deckStr;
    }

    private String[] resourcesToStringArray(){
        /** EXAMPLE
         * Resources disponibles : 4♥R 4♣E 4♠S 4♦D 4●O
         */
        String[] resStr = {"Resources disponibles : "};
        for (Resource res : resources.keySet()) {
            resStr[0] += getNbResource(res) + res.toSymbol() + " ";
        }
        return resStr;
    }

    private String[] boardToStringArray(){
        String[] res = Display.emptyStringArray(0, 0);
        
        // Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for (int i = stackCards.size(); i > 0; i--) {
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }
    
        // Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for (int i = 0; i < visibleCards.length; i++) { // parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for (int j = 0; j < visibleCards[i].length; j++) { // parcourir les 4 cartes faces visibles pour un niveau donné (j)
                tierCardsDisplay = Display.concatStringArray(tierCardsDisplay, visibleCards[i][j] != null ? visibleCards[i][j].toStringArray() : DevCard.noCardStringArray(), false);
            }
            cardDisplay = Display.concatStringArray(cardDisplay, Display.emptyStringArray(1, 40), true);
            cardDisplay = Display.concatStringArray(cardDisplay, tierCardsDisplay, true);
        }
    
        res = Display.concatStringArray(deckDisplay, cardDisplay, false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 52), true);
        res = Display.concatStringArray(res, resourcesToStringArray(), true);
        res = Display.concatStringArray(res, Display.emptyStringArray(35, 1, " \u250A"), false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 54, "\u2509"), true);
    
        return res;
    }

    @Override
    public String[] toStringArray() {
        return boardToStringArray();
    }
}