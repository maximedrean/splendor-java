package com.splendor;

public class DevCardTest {

    public static void main(String[] args) {
        Resources cost = new Resources();
        cost.setNbResource(Resource.DIAMOND, 2);
        cost.setNbResource(Resource.EMERALD, 1);
        cost.setNbResource(Resource.SAPPHIRE, 3);
        DevCard card = new DevCard(1, cost, 2, Resource.DIAMOND);
        String[] devCardPreview = card.toStringArray();
        System.out.println(String.join("\n", devCardPreview));
    }
}