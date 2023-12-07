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
        String points = " ";
        if (this.points > 0) {
            points = Character.toString(getPoints() + 9311);
        }
        String devCardPreview = String.join("\n", Constants.DEV_CARD_PREVIEW);
        // TODO: Add resources cost
        devCardPreview = MessageFormat.format(devCardPreview, points, bonus.toSymbol());
        return devCardPreview.split("\n");
    }
}