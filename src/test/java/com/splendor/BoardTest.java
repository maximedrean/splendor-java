package com.splendor;

import java.util.Random;
import java.util.stream.IntStream;

import com.splendor.board.Board;
import com.splendor.cards.DevCard;
import com.splendor.constants.Values;
import com.splendor.exceptions.CardReaderException;


public class BoardTest {

    public static void main(String[] args) throws CardReaderException {
        final Board board = new Board(new Random().nextInt(4));
        final DevCard[] cards = board.getVisibleCards();
        final int length = Values.TIER_NUMBER + 1;
        IntStream.range(0, cards.length).forEach(index -> {
            if (index % length == 0) System.out.println();
            System.out.print(cards[index].getLevel() + " ");
        });
    }
}
