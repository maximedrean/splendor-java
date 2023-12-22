package com.splendor.actions;

import com.splendor.Constants;

public abstract class CardAction extends InputAction {
    public void displayAction() {
        Constants.display.outBoard.println("Quelle position ?");
        Constants.display.outBoard.println("Entrez le tier et la colonne séparés par un espace.");
        Constants.display.outBoard.println("Exemple : 1 2 (pour la carte tier 1, colonne 2).");
    }
}
