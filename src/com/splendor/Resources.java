package com.splendor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Resources class epresents a collection of resources in Splendor. Extends
 * HashMap to associate each resource with its quantity.
 */
public class Resources extends HashMap<Resource, Integer> {

    /**
     * Constructs an empty Resources object.
     */
    public Resources() {
        super();
    }

    /**
     * Retrieves the quantity of a specific resource from the collection.
     *
     * @param resource The resource to get the quantity for.
     * @return The quantity of the specified resource, or 0 if the resource is not
     *         present.
     */
    public int getNbResource(Resource resource) {
        return this.getOrDefault(resource, 0);
    }

    /**
     * Sets the quantity of a specific resource in the collection.
     * If the quantity is negative, it is set to 0.
     *
     * @param resource The resource to set the quantity for.
     * @param nb       The new quantity for the resource (non-negative).
     */
    public void setNbResource(Resource resource, int nb) {
        this.put(resource, Math.max(nb, 0));
    }

    /**
     * Updates the quantity of a specific resource in the collection by adding the
     * given amount.
     * If the resulting count is negative, it is set to 0.
     *
     * @param resource The resource to update the quantity for.
     * @param nb       The amount to add to the current quantity.
     */
    public void updateNbResource(Resource resource, int nb) {
        int currentNb = this.getNbResource(resource);
        this.put(resource, Math.max(currentNb + nb, 0));
    }

    /**
     * Retrieves an array of available resources with a quantity greater than zero.
     *
     * @return An array containing available resources with quantity greater than
     *         zero.
     */
    public Resource[] getAvailableResources() {
        // Initialize an empty array list
        ArrayList<Resource> availableResources = new ArrayList<>();

        // Add each resource with a quantity greater than zero to the array list
        for (Resource resource : this.keySet()) {
            if (this.getNbResource(resource) > 0) {
                availableResources.add(resource);
            }
        }

        // Return the array list as an array
        return availableResources.toArray(new Resource[availableResources.size()]);
    }

}
