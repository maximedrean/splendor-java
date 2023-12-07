package com.splendor;

public class DevCardTest {

    public static void main(String[] args) {
        DevCard card = new DevCard(1, null, 2, Resource.DIAMOND);
        String[] devCardPreview = card.toStringArray();
        System.out.println(String.join("\n", devCardPreview));
    }
}