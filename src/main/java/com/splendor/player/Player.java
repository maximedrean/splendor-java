package com.splendor.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import com.splendor.Resources;
import com.splendor.actions.IAction;
import com.splendor.board.Board;
import com.splendor.cards.DevCard;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;
import com.splendor.display.Displayable;


/**
 * The Player class represents a Splendor player, and implements the
 * Displayable interface. A player has an ID, a name, a list of resources, 
 * a list of purchased cards, and prestige points.
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
     * @param id The unique identifier of the player.
     */
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.resources = new Resources();
        this.purchasedCards = new ArrayList<DevCard>();
        this.reservedCards = new DevCard[Values.MAX_RESERVED_CARDS];
        this.points = 0;
    }

    /**
     * Abstract method for choosing an action.
     * Concrete implementations in subclasses should define the logic 
     * for selecting and returning an action.
     *
     * @return The chosen action.
     */
    public abstract IAction chooseAction(Board board);

    /**
     * Abstract method for choosing resources to discard.
     * Concrete implementations in subclasses should define the logic 
     * for selecting and returning the resources to discard.
     *
     * @return The resources chosen to be discarded.
     */
    public abstract Resources chooseDiscardingTokens();

    /**
     * Retrieves the id of the player.
     *
     * @return The id of the player.
     */
    public int getId() {
        return this.id;
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
     * @return An array containing the available resources owned by 
     *         the player.
     */
    public Resource[] getAvailableResources() {
        return this.resources.getAvailableResources();
    }

    /**
     * Calculates the total number of development cards in the player's 
     * purchased cards that provide a specific bonus resource.
     *
     * @param resource The bonus resource to count in the development cards.
     * @return The count of development cards with the specified bonus 
     *         resource.
     */
    public int getResFromCards(Resource resource) {
        return (int) this.purchasedCards.stream()
            .map(DevCard::getBonus).filter(resource::equals).count();
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
     * @param card The development card for which to update the player's 
     *        points.
     */
    public void updatePoints(DevCard card) {
        this.points++;
    }

    /**
     * Adds a purchased development card to the player's collection.
     *
     * @param card The development card to be added to the player's purchased 
     *        cards.
     */
    public void addPurchasedCard(DevCard card) {
        this.purchasedCards.add(card);
    }

    /**
     * Retrieves the array of reserved development cards for the player.
     *
     * @return The array of reserved development cards.
     */
    public DevCard[] getReservedCards() {
        return this.reservedCards;
    }

    /**
     * Removes the reserved card at the specified index and returns the 
     * updated array of reserved cards.
     *
     * @param selectedIndex The index of the reserved card to be removed.
     * @return The updated array of reserved cards after removing the
     *         specified card.
     */
    public DevCard[] removeReservedCard(int selectedIndex) {
        // Create a new array to store the updated reserved cards.
        DevCard[] newCards = new DevCard[Values.MAX_RESERVED_CARDS];
        int reservedIndex = 0;
        // Iterate through the existing reserved cards array.
        for (int index = 0; index < this.reservedCards.length; index++) {
            // Skip the card at the specified index to remove it.
            if (reservedIndex == index) continue;
            final DevCard card = this.reservedCards[index];
            newCards[reservedIndex] = card;
            reservedIndex++;
        }
        // Update the reservedCards field with the new array.
        this.reservedCards = newCards;
        return this.reservedCards;
    }

    /**
     * Adds a development card to the player's reserved cards.
     * 
     * @param card The development card to be added to the reserved cards.
     */
    public void addReservedCard(DevCard card) {
        for (int index = 0; index < this.reservedCards.length; index++) {
            // If an empty slot is found, add the card and return.
            if (this.reservedCards[index] == null) {
                this.reservedCards[index] = card;
                return;
            }
        }
    }

    /**
     * Checks if the player has sufficient resources to buy a specified
     * development card.
     *
     * @param card The development card for which to check affordability.
     * @return {@code true} if the player can afford the card, {@code false}
     *         otherwise.
     */
    public boolean canBuyCard(DevCard card) {
        final Resources cardCost = card.getCost();
        for (final Entry<Resource, Integer> entry : cardCost.entrySet()) {
            final Resource resource = entry.getKey();
            final int resourceNumber = this.getNbResource(resource);
            final int resourceCount = this.getResFromCards(resource);
            final int cost = resourceNumber + resourceCount;
            if (cost < entry.getValue()) return false;
        }
        return true;
    }

    /**
     * Checks if the player can reserve additional development cards.
     * 
     * @return {@code true} if the player can reserve a card (has an 
     *         available slot), {@code false} otherwise.
     */
    public boolean canReserveCard() {
        for (final DevCard card : this.reservedCards)
            if (card == null) return true;
        return false;
    }

    /**
     * Calculates the total number of tokens the player has.
     * 
     * @return The total number of tokens.
     */
    public int getTotalTokens() {
        return Arrays.stream(Resource.values())
            .mapToInt(this::getNbResource).sum();
    }

    /**
     * Calculates the number of resources that a player needs to discard in
     * order to comply with the maximum number of resources a player can have.
     *
     * @return The number of resources to discard to stay within the limit.
     */
    public int getNumberResourcesToDiscard() {
        final int sum = Arrays.stream(this.getAvailableResources())
            .mapToInt(this::getNbResource).sum();
        return sum - Values.MAX_NUMBER_RESOURCES_PER_PLAYER;
    }

    /**
     * Converts the player's information and assets to a formatted 
     * string array.
     *
     * @return An array of strings representing the player's information 
     *         and asset details.
     */
    public String[] toStringArray() {
        final String enhancedNumber = Character.toString(this.points + 9311);
        final String pointString = this.points > 0 ? enhancedNumber : "0";
        // Player name, points, space, 5 tokens.
        String[] stringPlayer = new String[8];
        stringPlayer[0] = "Player " + (this.id + 1) + ": " + this.name;
        stringPlayer[1] = pointString + " pts";
        stringPlayer[2] = "";
        final int resourcesLength = 3 + Resource.values().length - 1;
        // Iterate through the resources and create a string
        // representation of the player's assets.
        for (final Resource resource : Resource.values()) {
            final int selectedResource = resourcesLength - resource.ordinal();
            final String symbol = resource.toSymbol();
            final int resourceToken = this.resources.getNbResource(resource);
            final int resourceValue = this.getResFromCards(resource);
            stringPlayer[selectedResource - 1] = symbol + " (" +
                resourceToken + ") [" + resourceValue + "]";
        }
        return stringPlayer;
    }
}
