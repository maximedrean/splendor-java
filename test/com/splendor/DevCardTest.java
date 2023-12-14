package com.splendor;

public class DevCardTest {

    public static void main(String[] args) {
        testDevCardConstructor();
        testDevCardSetters();
    }

    private static void testDevCardConstructor() {
        Resources cost = new Resources();
        cost.setNbResource(Resource.EMERALD, 2);
        cost.setNbResource(Resource.RUBY, 1);
        Resource bonus = Resource.DIAMOND;
        DevCard devCard = new DevCard(1, cost, 5, bonus);

        assertEqual(1, devCard.getLevel());
        assertEqual(cost, devCard.getCost());
        assertEqual(5, devCard.getPoints());
        assertEqual(bonus, devCard.getBonus());
    }

    private static void testDevCardSetters() {
        Resources cost = new Resources();
        cost.setNbResource(Resource.EMERALD, 2);
        cost.setNbResource(Resource.RUBY, 1);
        Resource bonus = Resource.DIAMOND;
        DevCard devCard = new DevCard(1, cost, 5, bonus);

        devCard.setLevel(2);
        Resources new_cost = new Resources();
        new_cost.setNbResource(Resource.SAPPHIRE, 2);
        devCard.setCost(new_cost);
        devCard.setPoints(8);
        devCard.setBonus(Resource.ONYX);

        assertEqual(2, devCard.getLevel());
        assertEqual(new_cost, devCard.getCost());
        assertEqual(8, devCard.getPoints());
        assertEqual(Resource.ONYX, devCard.getBonus());
    }

    private static void assertEqual(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            System.out.println("Test failed. Expected: " + expected + ", Actual: " + actual);
        }
    }
}
