package com.github.zipcodewilmington.casino.cards;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Collections;



class DeckTest {

    @Test
    void createDeck() {
        Cards deck = new Cards(Rank.ACE, Suit.CLUBS, 14);
        int expected = 52;
        ArrayList<Cards> actualCards = Deck.createDeck();
        int actual = actualCards.size();

        Assert.assertEquals(expected, actual);
    }


    @Test
    void testShuffle() {
        Cards card = new Cards(Rank.ACE, Suit.CLUBS, 14);
        ArrayList<Cards> actualCards = Deck.createDeck();
        Collections.shuffle(actualCards);
        System.out.println(actualCards);
    }

}