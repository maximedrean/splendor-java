package com.splendor.player;

import static java.util.Map.Entry;
import java.util.ArrayList;

import com.splendor.DevCard;
import com.splendor.Displayable;
import com.splendor.Resource;
import com.splendor.Resources;
import com.splendor.action.IAction;


public abstract class Player implements Displayable {

    private final int id;
    private final String name;

    private final Resources resources;
    private final ArrayList<DevCard> purchasedCards;
    private int points;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.resources = new Resources();
        this.purchasedCards = new ArrayList<DevCard>();
        this.points = 0;
    }

    public abstract IAction chooseAction();
    public abstract Resources chooseDiscardingTokens();

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.points;
    }

    public int getNbResource(Resource resource) {
        return this.resources.getNbResource(resource);
    }

    public Resource[] getAvailableResources() {
        return this.resources.getAvailableResources();
    }

    public int getResFromCards(Resource resource) {        
        return this.purchasedCards.stream()
            .mapToInt(card -> card.getCost().getNbResource(resource)).sum();
    }

    public void updateNbResource(Resource resource, int quantity) {
        int previousQuantity = this.resources.getNbResource(resource);
        // Add or subtract a specific quantity from the previous quantity.
        previousQuantity += quantity;
        // Note: Resources.setNbResource() already verifies this.
        if (previousQuantity < 0) previousQuantity = 0;
        this.resources.setNbResource(resource, previousQuantity);
    }

    public void updatePoints(DevCard card) {
        this.points++;
    }

    public void addPurchasedCard(DevCard card) {
        this.purchasedCards.add(card);
    }

    public boolean canBuyCard(DevCard card) {
        Resources cardCost = card.getCost();
        for (Entry<Resource, Integer> entry : cardCost.entrySet())
            if (this.getNbResource(entry.getKey()) < entry.getValue()) return false;
        return true;
    }

    public String[] toStringArray() {
        // Player name, points, space, 5 tokens.
        String[] stringPlayer = new String[8];
        String pointString = this.points > 0 ?
            Character.toString(this.points + 9311) : "0";

        stringPlayer[0] = "Player " + (this.id + 1) + ": " + this.name;
        stringPlayer[1] = pointString + " pts";
        stringPlayer[2] = "";

        final int resourcesLength = 3 + Resource.values().length - 1;

        // Iterate through the resources and create a string
        // representation of the player's assets.
        for (Resource resource : Resource.values()) {
            int selectedResource = resourcesLength - resource.ordinal();
            String symbol = resource.toSymbol();
            int resourceToken = this.resources.getNbResource(resource);
            int resourceValue = this.getResFromCards(resource);

            stringPlayer[selectedResource] = symbol + " (" +
                resourceToken + ") [" + resourceValue + "]";
        }

        return stringPlayer;
    }
}
