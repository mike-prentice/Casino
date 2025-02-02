package com.github.zipcodewilmington.casino.games.war;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.cards.Cards;
import com.github.zipcodewilmington.casino.cards.Deck;
import com.github.zipcodewilmington.casino.cards.Rank;
import com.github.zipcodewilmington.casino.cards.Suit;

import java.util.Collections;
import java.util.*;

import static java.sql.DriverManager.println;

public class WarGame implements GameInterface<WarPlayer> { // NON-GAMBLING

    Boolean isCardGame = true;

    // Scanner scanner = new Scanner(System.in);

    ArrayDeque<Cards> temporary = new ArrayDeque<Cards>();
    ArrayDeque<Cards> handPlayer1 = new ArrayDeque<Cards>();
    ArrayDeque<Cards> handPlayer2 = new ArrayDeque<Cards>();

    // ============================= SUB-METHODS =============================

    public static String getStringInput(String prompt) { // no test
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String userInput = scanner.nextLine();
        return userInput;
    }

    public ArrayDeque<Cards> generateWarDeck() { // no test
        ArrayList<Cards> unShuffled = createWarDeck();
        ArrayList<Cards> shuffled = shuffle(unShuffled);
        ArrayDeque<Cards> deck = convertDeckToDeque(shuffled);
        return deck;
    }

    public ArrayList<Cards> createWarDeck() { // tested
        Deck deck = new Deck();
        ArrayList<Cards> unShuffledWarDeck = deck.createDeck();
        return unShuffledWarDeck;
    }

    public ArrayList<Cards> shuffle(ArrayList<Cards> inputDeck) { // tested
        Collections.shuffle(inputDeck);
        return inputDeck;
    }

    public ArrayDeque<Cards> convertDeckToDeque(ArrayList<Cards> inputDeck) { // no test
        ArrayDeque<Cards> convertedDeck = new ArrayDeque<>(inputDeck);
        return convertedDeck;
    }

    public void deal(ArrayDeque<Cards> deck) { // tested
        for (int i = 1; i <= 26; i++) {
            handPlayer1.addFirst(deck.removeFirst());
            handPlayer2.addFirst(deck.removeFirst());
        }
    }

    public void distributeTemporaryCards(ArrayDeque<Cards> handOfWinner) { // tested
        int x = 0;
        while (x == 0) {
            if (temporary.isEmpty()) {
                x = 1;
            } else {
                handOfWinner.addLast(temporary.removeFirst());
            }
        }
    }

    public void addThreeCardsToTemporary(ArrayDeque<Cards> handOfPlayer) { // tested
        temporary.addFirst(handOfPlayer.removeFirst());
        temporary.addFirst(handOfPlayer.removeFirst());
        temporary.addFirst(handOfPlayer.removeFirst());
    }

    public void compareAndRedistribute(Cards player1Card, Cards player2Card) {
        int tierCard1 = player1Card.getTier();
        int tierCard2 = player2Card.getTier();
        Rank rank1 = player1Card.getRank();
        Suit suit1 = player1Card.getSuit();
        Rank rank2 = player2Card.getRank();
        Suit suit2 = player2Card.getSuit();
        if (tierCard1 > tierCard2) {
            handPlayer1.addLast(player1Card);
            handPlayer1.addLast(player2Card);
            distributeTemporaryCards(handPlayer1);
            System.out.println("Player 1: " + rank1 + " " + suit1);
            System.out.println("COMPUTER: " + rank2 + " " + suit2 + "\n");
            System.out.println("Player 1 Wins This Round!" + "\n");
            System.out.println("Player 1 Has " + handPlayer1.size() + " cards.");
            System.out.println("COMPUTER Has " + handPlayer2.size() + " cards." + "\n");

        } else if (tierCard1 < tierCard2) {
            handPlayer2.addLast(player1Card);
            handPlayer2.addLast(player2Card);
            distributeTemporaryCards(handPlayer2);
            System.out.println("Player 1: " + rank1 + " " + suit1);
            System.out.println("COMPUTER: " + rank2 + " " + suit2 + "\n");
            System.out.println("COMPUTER Wins This Round!" + "\n");
            System.out.println("Player 1 Has " + handPlayer1.size() + " cards.");
            System.out.println("COMPUTER Has " + handPlayer2.size() + " cards." + "\n");

        } else if (tierCard1 == tierCard2) {
            System.out.println("Player 1: " + rank1 + " " + suit1);
            System.out.println("COMPUTER: " + rank2 + " " + suit2 + "\n");
            if (handPlayer1.size() > 0 && handPlayer2.size() == 0) {
                return;
            } else if (handPlayer1.size() == 0 && handPlayer2.size() > 0) {
                return;
            } else if (handPlayer1.size() > 3 && handPlayer2.size() > 3) {
                System.out.println("WAR!" + "\n");
                temporary.addFirst(player1Card);
                temporary.addFirst(player2Card);
                addThreeCardsToTemporary(handPlayer1);
                addThreeCardsToTemporary(handPlayer2);
                return;
            } else if (handPlayer1.size() > 3 && handPlayer2.size() < 3) {
                System.out.println("WAR!" + "\n");
                temporary.addFirst(player1Card);
                temporary.addFirst(player2Card);
                addThreeCardsToTemporary(handPlayer1);
                while (handPlayer2.size() > 1) {
                    temporary.addFirst(handPlayer2.removeFirst());
                }
                return;
            } else if (handPlayer1.size() < 3 && handPlayer2.size() > 3) {
                System.out.println("WAR!" + "\n");
                temporary.addFirst(player1Card);
                temporary.addFirst(player2Card);
                addThreeCardsToTemporary(handPlayer2);
                while (handPlayer1.size() > 1) {
                    temporary.addFirst(handPlayer1.removeFirst());
                }
                return;
            } else {
                System.out.println("WAR!" + "\n");
                temporary.addFirst(player1Card);
                temporary.addFirst(player2Card);
                while (handPlayer1.size() > 1) {
                    temporary.addFirst(handPlayer1.removeFirst());
                }
                while (handPlayer2.size() > 1) {
                    temporary.addFirst(handPlayer2.removeFirst());
                }
                return;
            }
        }
    }


    @Override
    public Boolean isOver() {
        return null;
    }

    @Override
    public void addPlayer(WarPlayer player) {

    }

    @Override
    public void addPlayers(List<? extends WarPlayer> player) {

    }

    @Override
    public void evaluateTurn(WarPlayer player) {

    }

    @Override
    public void remove(WarPlayer player) {

    }

    @Override
    public void run() {
        // generate deck and deal cards
        ArrayDeque<Cards> deck = generateWarDeck();
        deal(deck);

        // gameplay
        while (handPlayer1.size() < 52 && handPlayer2.size() < 52) {
            String playerInput = getStringInput("Input FLIP to flip the next card " +
                    "or QUIT to exit the game.");
            if (playerInput.equals("FLIP")) {
                // do nothing/continue
            } else if (playerInput.equals("QUIT")) {
                return;
            }
            Cards player1Card = handPlayer1.removeFirst();
            Cards player2Card = handPlayer2.removeFirst();
            compareAndRedistribute(player1Card, player2Card);
        }

        // declare a winner
        if (handPlayer1.size() == 0) {
            System.out.println("COMPUTER WINS!");
        } else if (handPlayer2.size() == 0) {
            System.out.println("PLAYER WINS!");
        }
    }
}
