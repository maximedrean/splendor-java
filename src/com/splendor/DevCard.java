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
     * Converts information about the development card to a formatted string array.
     * The resulting array represents a visual preview of the development card,
     * including points, bonuses, and resource costs.
     *
     * @return An array of strings representing the formatted preview of the
     *         development card.
     *         Each element in the array corresponds to a line in the visual
     *         preview.
     */
    public String[] toStringArray() {
        // Convert the dev card preview strings array into a single string in order to
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
     * Converts the points of the development card to a formatted string.
     * If the points value is greater than 0, it is converted to a string and
     * represented using special Unicode characters.
     *
     * @return A string representing the formatted points of the development card.
     *         If points are greater than 0, special Unicode characters are used for
     *         representation.
     *         If points are 0 or negative, an empty string is returned.
     */
    private String pointsToString() {
        String points = " ";
        if (this.points > 0) {
            points = Character.toString(getPoints() + 9311);
        }
        return points;
    }

    /**
     * Converts the bonus of the development card to a formatted string.
     *
     * @return A string representing the formatted bonus of the development card.
     *         The string is obtained by invoking the {@code toSymbol} method on the
     *         bonus.
     */
    private String bonusToString() {
        return this.bonus.toSymbol();
    }

    /**
     * Converts information about the development card's resource costs to a
     * formatted string array.
     * The resulting array represents a visual preview of the resource costs,
     * including symbols and counts.
     *
     * @return An array of strings representing the formatted preview of the
     *         resource costs of the development card.
     *         Each pair of consecutive elements in the array represents a resource
     *         symbol and its corresponding count.
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
     * Converts the development card to a formatted string representation.
     * The resulting string includes information about points, bonus type, and
     * resource costs.
     *
     * @return A string representing the formatted preview of the development card,
     *         including points, bonus type, and resource costs.
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