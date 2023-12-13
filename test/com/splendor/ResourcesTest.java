package com.splendor;

import java.util.Arrays;

public class ResourcesTest {
    
    public static void main(String[] args) {
        Resources resources = new Resources();
        Resource diamond = Resource.DIAMOND;
        System.out.println(Arrays.toString(resources.getAvailableResources()));
    }
}
