package com.splendor.actions.human;

import java.util.LinkedHashMap;
import java.util.Map;

import com.splendor.actions.HumanAction;
import com.splendor.constants.Resource;
import com.splendor.constants.Utility;


/**
 * An abstract base class for token-related actions in the Splendor 
 * board game. Extends InputAction and provides a mechanism for 
 * displaying and mapping token resources.
 */
public abstract class Token extends HumanAction {

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

    /**
     * Displays the available token resources along with their 
     * corresponding identifiers.
     */
    public void displayResources() {
        for (Map.Entry<String, Resource> entry : Token.resources.entrySet()) {
            final String resourcePreview = entry.getValue().toString();
            final String output = entry.getKey() + " : " + resourcePreview;
            Utility.display.out.println(output);
        }
    }
}
