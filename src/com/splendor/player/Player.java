package com.splendor.player;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.splendor.Constants;
import com.splendor.DevCard;
import com.splendor.Displayable;
import com.splendor.Resource;
import com.splendor.Resources;
import com.splendor.actions.IAction;

/**
 * The Player class represents a Splendor player, and implements the
 * Displayable interface.
 * A player has an ID, a name, a list of resources, a list of purchased cards,
 * and prestige points.
 */
public abstract class Player implements Displayable {

    /**
     * The ID of the player.
     */
    private final int id;

    /**
     * The name of the player.
     */
    private final String name;

    /**
     * The resources of the player.
     */
    private final Resources resources;

    /**
     * The list of purchased cards of the player.
     */
    private final ArrayList<DevCard> purchasedCards;

    private DevCard[] reservedCards;

    /**
     * The prestige points of the player.
     */
    private int points;

    /**
     * Constructs a new player with the specified name and identifier.
     * Initializes the player's resources, purchased cards, and points.
     *
     * @param name The name of the player.
     * @param id   The unique identifier of the player.
     */
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.resources = new Resources();
        this.purchasedCards = new ArrayList<DevCard>();
        this.reservedCards = new DevCard[Constants.MAX_RESERVED_CARDS];
        this.points = 0;
    }

    /**
     * Abstract method for choosing an action.
     * Concrete implementations in subclasses should define the logic for selecting
     * and returning an action.
     *
     * @return The chosen action.
     */
    public abstract IAction chooseAction();

    /**
     * Abstract method for choosing resources to discard.
     * Concrete implementations in subclasses should define the logic for selecting
     * and returning the resources to discard.
     *
     * @return The resources chosen to be discarded.
     */
    public abstract Resources chooseDiscardingTokens();

    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the current points earned by the player.
     *
     * @return The number of points accumulated by the player.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Retrieves the quantity of a specific resource owned by the player.
     *
     * @param resource The resource for which to retrieve the quantity.
     * @return The quantity of the specified resource owned by the player.
     */
    public int getNbResource(Resource resource) {
        return this.resources.getNbResource(resource);
    }

    /**
     * Retrieves an array of the available resources owned by the player.
     *
     * @return An array containing the available resources owned by the player.
     */
    public Resource[] getAvailableResources() {
        return this.resources.getAvailableResources();
    }

    public int getResFromCards(Resource resource) {
        int sum = 0;
        for (DevCard card : this.purchasedCards) {
            Resource bonus = card.getBonus();
            if (bonus != null && bonus == resource) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Updates the quantity of a specific resource in the player's resources.
     *
     * @param resource The resource to update.
     * @param quantity The quantity by which to update the resource.
     */
    public void updateNbResource(Resource resource, int quantity) {
        this.resources.updateNbResource(resource, quantity);
    }

    /**
     * Updates the player's points by incrementing them based on the provided
     * development card.
     *
     * @param card The development card for which to update the player's points.
     */
    public void updatePoints(DevCard card) {
        this.points++;
    }

    /**
     * Adds a purchased development card to the player's collection.
     *
     * @param card The development card to be added to the player's purchased cards.
     */
    public void addPurchasedCard(DevCard card) {
        this.purchasedCards.add(card);
    }

    public DevCard[] getReservedCards() {
        return this.reservedCards;
    }

    public DevCard[] removeReservedCard(int index) {
        DevCard[] newReservedCards = new DevCard[Constants.MAX_RESERVED_CARDS];
        int j = 0;
        for (int i = 0; i < this.reservedCards.length; i++) {
            if (i != index) {
                newReservedCards[j] = this.reservedCards[i];
                j++;
            }
        }
        this.reservedCards = newReservedCards;
        return this.reservedCards;
    }

    public void addReservedCard(DevCard card) {
        for (int i = 0; i < this.reservedCards.length; i++) {
            if (this.reservedCards[i] == null) {
                this.reservedCards[i] = card;
                break;
            }
        }
    }

    /**
     * Checks if the player has sufficient resources to buy a specified development
     * card.
     *
     * @param card The development card for which to check affordability.
     * @return {@code true} if the player can afford the card, {@code false}
     *         otherwise.
     */
    public boolean canBuyCard(DevCard card) {
        Resources cardCost = card.getCost();
        for (Entry<Resource, Integer> entry : cardCost.entrySet()) {
            Resource resource = entry.getKey();
            if (this.getNbResource(resource) + getResFromCards(resource) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean canReserveCard() {
        for (DevCard card : this.reservedCards) {
            if (card == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the player's information and assets to a formatted string array.
     *
     * @return An array of strings representing the player's information and asset
     *         details.
     */
    public String[] toStringArray() {
        // Player name, points, space, 5 tokens.
        String[] stringPlayer = new String[8];
        String pointString = this.points > 0 ? Character.toString(this.points + 9311) : "0";

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
