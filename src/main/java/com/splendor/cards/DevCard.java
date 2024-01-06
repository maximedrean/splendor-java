package com.splendor.cards;

import java.text.MessageFormat;

import com.splendor.board.Resources;
import com.splendor.constants.Cards;
import com.splendor.constants.Messages;
import com.splendor.constants.Resource;
import com.splendor.constants.Values;
import com.splendor.display.Displayable;


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
     * @param level The level of the development card.
     * @param cost The resources required to acquire the development card.
     * @param points The prestige points associated with the development card.
     * @param bonus A bonus resource associated with the development card.
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
        return this.level;
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
        return this.cost;
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
        return this.points;
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
        return this.bonus;
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
     * Converts information about the development card to a formatted string 
     * array. The resulting array represents a visual preview of the 
     * development card, including points, bonuses, and resource costs.
     *
     * @return An array of strings representing the formatted preview of the
     *         development card. Each element in the array corresponds to a 
     *         line in the visual preview.
     */
    public String[] toStringArray() {
        // Convert the development card preview strings array into a single 
        // string in order to format with values.
        String devCardPreview = String.join("\n", Cards.DEV_CARD_PREVIEW);
        // Format the string with values.
        final String[] resourcesArray = this.resourcesToString();
        devCardPreview = MessageFormat.format(
            devCardPreview, this.pointsToString(), this.bonusToString(),
            resourcesArray[0], resourcesArray[1], resourcesArray[2],
            resourcesArray[3], resourcesArray[4], resourcesArray[5],
            resourcesArray[6], resourcesArray[7]);
        // Return the string split back into an array of strings.
        return devCardPreview.split("\n");
    }

    /**
     * Converts the points of the development card to a formatted string.
     * If the points value is greater than 0, it is converted to a string and
     * represented using special Unicode characters.
     *
     * @return A string representing the formatted points of the development 
     *         card. If points are greater than 0, special Unicode characters 
     *         are used for representation. If points are 0 or negative, an 
     *         empty string is returned.
     */
    private String pointsToString() {
        final String enhancedNumber = Character.toString(getPoints() + 9311);
        return this.points > 0 ? enhancedNumber : "  ";
    }

    /**
     * Converts the bonus of the development card to a formatted string.
     *
     * @return A string representing the formatted bonus of the development 
     *         card. The string is obtained by invoking the {@code toSymbol} 
     *         method on the bonus.
     */
    private String bonusToString() {
        return this.bonus == null ? "  " : this.bonus.toSymbol();
    }

    /**
     * Converts information about the development card's resource costs to a
     * formatted string array. The resulting array represents a visual preview
     * of the resource costs, including symbols and counts.
     *
     * @return An array of strings representing the formatted preview of the
     *         resource costs of the development card. Each pair of consecutive 
     *         elements in the array represents a resource symbol and its
     *         corresponding count.
     */
    private String[] resourcesToString() {
        String[] resources = new String[2 * Values.CARD_RESOURCES_LIMIT];
        // Initialize with empty spaces.
        for (int i = 0; i < 2 * Values.CARD_RESOURCES_LIMIT; i++)
            resources[i] = i % 2 == 0 ? "  " : " ";
        // Replace empty spaces with resources data.
        final Resource[] available = this.cost.getAvailableResources();
        for (int i = 0; i < available.length; i++) {
            int index = i * 2;
            resources[index] = available[i].toSymbol();
            final int resourcesNumber = cost.getNbResource(available[i]);
            resources[index + 1] = Integer.toString(resourcesNumber);
        }
        return resources;
    }

    /**
     * Converts the development card to a formatted string representation.
     * The resulting string includes information about points, bonus type, and
     * resource costs.
     *
     * @return A string representing the formatted preview of the development
     *         card, including points, bonus type, and resource costs.
     */
    public String toString() {
        // Create a development card preview with points, and bonus.
        String card = MessageFormat.format(
            Messages.CARD_RESOURCES, this.getPoints(),
            this.bonus == null ? "" : this.bonus.toSymbol());
        // Add each resource cost to the card preview if not null.
        for (Resource resource : this.cost.getAvailableResources()) {
            if (this.cost.getNbResource(resource) <= 0) continue;
            final int resourceNumber = this.cost.getNbResource(resource);
            final String resourceSymbol = resource.toSymbol();
            card += " " + resourceNumber + " " + resourceSymbol;
        }
        return card;
    }
}
