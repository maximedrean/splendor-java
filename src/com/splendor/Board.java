package com.splendor;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Stack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;


public class Board implements Displayable {

    /**
     * Add remaining cards and next tier to the deck representation
     * and return it.
     * 
     * @param tier The tier of the next available card in the deck.
     * @return The string array representation of the deck.
     */
    public String[] deckToStringArray(int tier) {
        int remainingCards = 0; // TODO
        String cards = String.format("%02d", remainingCards);
        String plural = remainingCards > 1 ? "s" : "";
        // Join and format the deck preview with the corresponding values.
        String deckPreview = String.join("\n", Constants.DECK_PREVIEW);
        deckPreview = MessageFormat.format(deckPreview, cards, plural, tier);
        return deckPreview.split("\n");
    }

    private String[] resourcesToStringArray() {
        /** EXAMPLE
         * Resources disponibles : 4♥R 4♣E 4♠S 4♦D 4●O
         */
        String[] resStr = {"Resources disponibles : "};
        /*
         * A decommenter
        for(ACOMPLETER){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            resStr[0] += resources.getNbResource(res)+res.toSymbol()+" ";
        }
                 */
        resStr[0] += "        ";
        return resStr;
    }

    private String[] boardToStringArray() {
        String[] res = Display.emptyStringArray(0, 0);
        /*
         * 

        //Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for(int i=stackCards.size();i>0;i--){
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        //Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for(ACOMPLETER){ //-- parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for(ACOMPLETER){ //-- parcourir les 4 cartes faces visibles pour un niveau donné (j)
                tierCardsDisplay = Display.concatStringArray(tierCardsDisplay, visibleCards[i][j]!=null ? visibleCards[i][j].toStringArray() : DevCard.noCardStringArray(), false);
            }
            cardDisplay = Display.concatStringArray(cardDisplay, Display.emptyStringArray(1, 40), true);
            cardDisplay = Display.concatStringArray(cardDisplay, tierCardsDisplay, true);
        }
        
        res = Display.concatStringArray(deckDisplay, cardDisplay, false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 52), true);
        res = Display.concatStringArray(res, resourcesToStringArray(), true);
        res = Display.concatStringArray(res, Display.emptyStringArray(35, 1, " \u250A"), false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 54, "\u2509"), true);
                 */
        return res;
    }

    @Override
    public String[] toStringArray() {
        return boardToStringArray();
    }
}
