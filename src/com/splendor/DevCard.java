package com.splendor;

import java.text.MessageFormat;

/**
 * The DevCard class represents a development card in Splendor, and implements
 * the Displayable interface.
 * Development cards have a level, a cost, prestige points, and a bonus
 * resource.
 */
public class DevCard implements Displayable {

    /**
     * The level of the development card.
     */
    private int level;

    /**
     * The resources required to acquire the development card.
     */
    private Resources cost;

    /**
     * The prestige points associated with the development card.
     */
    private int points;

    /**
     * A bonus resource associated with the development card.
     */
    private Resource bonus;

    /**
     * Constructs a new DevCard with the specified parameters.
     *
     * @param level  The level of the development card.
     * @param cost   The resources required to acquire the development card.
     * @param points The prestige points associated with the development card.
     * @param bonus  A bonus resource associated with the development
     *               card.
     */
    public DevCard(int level, Resources cost, int points, Resource bonus) {
        this.level = level;
        this.cost = cost;
        this.points = points;
        this.bonus = bonus;
    }

    /**
     * Retrieves the level of the development card.
     *
     * @return The level of the development card.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the development card.
     *
     * @param level The new level to set for the development card.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Retrieves the resources required to acquire the development card.
     *
     * @return The resources required to acquire the development card.
     */
    public Resources getCost() {
        return cost;
    }

    /**
     * Sets the resources required to acquire the development card.
     *
     * @param cost The new resources required for the development card.
     */
    public void setCost(Resources cost) {
        this.cost = cost;
    }

    /**
     * Retrieves the prestige points associated with the development card.
     *
     * @return The prestige points associated with the development card.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the prestige points associated with the development card.
     *
     * @param points The new prestige points for the development card.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Retrieves the bonus resource associated with the development card.
     *
     * @return The bonus resource associated with the development card.
     */
    public Resource getBonus() {
        return bonus;
    }

    /**
     * Sets the bonus resource associated with the development card.
     *
     * @param bonus The new bonus resource for the development card.
     */
    public void setBonus(Resource bonus) {
        this.bonus = bonus;
    }

    /**
     * Returns a string representation of the development card.
     *
     * @return A string representation of the development card.
     */
    public String[] toStringArray() {
        // Convert the deck preview strings array into a single string in order to
        // format with values
        String formattedDevCardPreview = String.join("\n", Constants.DEV_CARD_PREVIEW);

        // Format the string with values
        String[] resourcesArray = resourcesToString();
        formattedDevCardPreview = MessageFormat.format(formattedDevCardPreview, pointsToString(), bonusToString(),
                resourcesArray[0], resourcesArray[1], resourcesArray[2], resourcesArray[3], resourcesArray[4],
                resourcesArray[5], resourcesArray[6], resourcesArray[7]);

        // Return the string split back into an array of strings
        return formattedDevCardPreview.split("\n");
    }

    /**
     * Converts the prestige points to a string representation.
     *
     * @return The string representation of prestige points.
     */
    private String pointsToString() {
        String points = " ";
        if (this.points > 0) {
            points = Character.toString(getPoints() + 9311);
        }
        return points;
    }

    /**
     * Retrieves the symbol representation of the bonus resource.
     *
     * @return The symbol representation of the bonus resource.
     */
    private String bonusToString() {
        return this.bonus.toSymbol();
    }

    /**
     * Converts the resources to a string array representation.
     *
     * @return The string array representation of resources.
     */
    private String[] resourcesToString() {
        String[] resources = new String[Constants.MAX_NUMBER_RESOURCES_PER_CARD * 2];

        // Initialize with empty spaces
        for (int i = 0; i < Constants.MAX_NUMBER_RESOURCES_PER_CARD * 2; i++) {
            resources[i] = (i % 2 == 0) ? "  " : " ";
        }

        // Replace empty spaces with resources data
        Resource[] availableResources = cost.getAvailableResources();
        for (int i = 0; i < availableResources.length; i++) {
            int index = i * 2;
            resources[index] = availableResources[i].toSymbol();
            resources[index + 1] = Integer.toString(cost.getNbResource(availableResources[i]));
        }

        return resources;
    }

    /**
     * Returns a string representation of the development card.
     *
     * @return A string representation of the development card.
     */
    public String toString() {
        String cardString = getPoints() + "pts, type " + bonus.toSymbol() + " | coût :";
        for (Resource resource : cost.getAvailableResources()) {
            if (cost.getNbResource(resource) > 0) {
                cardString += " " + cost.getNbResource(resource) + " " + resource.toSymbol();
            }
        }
        return cardString;
    }
}