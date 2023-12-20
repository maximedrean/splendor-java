package com.splendor.actions;

import java.util.Hashtable;
import java.util.Map;

import com.splendor.Constants;
import com.splendor.Resource;

public abstract class TokenAction extends InputAction {
    public static Map<String, Resource> resources = new Hashtable<String, Resource>();

    static {
        resources.put("A", Resource.EMERALD);
        resources.put("B", Resource.DIAMOND);
        resources.put("C", Resource.SAPPHIRE);
        resources.put("D", Resource.ONYX);
        resources.put("E", Resource.RUBY);
    }

    public void displayResources() {
        for (Map.Entry<String, Resource> entry : resources.entrySet()) {
            Constants.display.outBoard.println(entry.getKey() + " : " + entry.getValue().toString());
        }
    }
}
