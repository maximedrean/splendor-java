package com.splendor.actions.robot;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.splendor.actions.RobotAction;
import com.splendor.constants.Resource;


/**
 * An abstract base class for token-related actions in the Splendor 
 * board game. Extends InputAction and provides a mechanism for 
 * displaying and mapping token resources.
 */
public abstract class Token extends RobotAction {

    public Resource getRandomResource() {
        // Get all values from the Resource enumeration.
        final Resource[] resources = Resource.values();
        final int index = new Random().nextInt(resources.length);
        return resources[index];
    }

    /**
     * A mapping of token identifiers (keys) to corresponding 
     * resource types (values).
     */
    public static Map<String, Resource> resources = 
        new LinkedHashMap<String, Resource>();

    static {
        Token.resources.put("A", Resource.EMERALD);
        Token.resources.put("B", Resource.DIAMOND);
        Token.resources.put("C", Resource.SAPPHIRE);
        Token.resources.put("D", Resource.ONYX);
        Token.resources.put("E", Resource.RUBY);
    }
}
