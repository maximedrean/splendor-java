package com.splendor;

import java.util.ArrayList;

import com.splendor.cards.CardReader;
import com.splendor.exceptions.CardReaderException;

public class CardReaderTest {
    
    public static void main(String[] args) {
        try {
            CardReader cardReader = new CardReader();
            ArrayList<DevCard> cards = cardReader.getCards();
            cards.forEach(System.out::println);
        } catch (CardReaderException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
