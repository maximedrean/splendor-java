package com.splendor;

// import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;

import com.splendor.cards.CardReader;
import com.splendor.cards.DevCard;
// import com.splendor.cards.Noble;
import com.splendor.exceptions.CardReaderException;


public class CardReaderTest {

    private final static String DELIMITER = "-".repeat(5);

    public static void main(String[] args) {
        try {
            // Retrieve all the cards from the CSV file.
            final CardReader cardReader = new CardReader();
            Stack<DevCard>[] cards = cardReader.getDevCards();
            // int playersCount = new Random().nextInt(4);
            // Noble[] noble = cardReader.getNobleCards(playersCount);

            IntStream.range(0, cards.length).forEach(index -> {
                final Stack<DevCard> stack = cards[index];
                System.out.print(CardReaderTest.DELIMITER);
                System.out.print("Index: " + index + " Length: " + stack.size());
                System.out.println(CardReaderTest.DELIMITER);
                for (final DevCard card : stack) {
                    System.out.println(card);
                    System.out.println(card.getLevel());
                }
            });
        } catch (CardReaderException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
