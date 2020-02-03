package net.pipe.tens.internal;


import java.util.ArrayList;

public class Deck {

    //instance vars
    private final ArrayList<Card> cards;
    private int size;

    //contains a deck of cards
    public Deck(String[] ranks, String[] suits, int[] values) {
        cards = new ArrayList<>();
        for (int j = 0; j < ranks.length; j++) {
            for (String suitString : suits) {
                cards.add(new Card(ranks[j], suitString, values[j]));
            }
        }
        size = cards.size();
        shuffle();
    }
    public boolean isEmpty() { return size==0; }

    public int getSize() { return size; }

    //shuffles the deck
    public void shuffle() {
        for (int k = cards.size() - 1; k > 0; k--) {
            int howMany = k + 1;
            int start = 0;
            int randPos = (int) (Math.random() * howMany) + start;
            Card temp = cards.get(k);
            cards.set(k, cards.get(randPos));
            cards.set(randPos, temp);
        }
        size = cards.size();
    }

    //deals a card
    public Card deal() {
        if (isEmpty())
            return null;
        size--;
        return cards.get(size);
    }

    //for debug
    @Override
    public String toString() {
        StringBuilder rtn = new StringBuilder("size = " + size + "\nUndealt cards: \n");

        for (int k = size - 1; k >= 0; k--) {
            rtn.append(cards.get(k));
            if (k != 0) {
                rtn.append(", ");
            }
            if ((size - k) % 2 == 0) {
                // Insert carriage returns so entire deck is visible on console.
                rtn.append("\n");
            }
        }

        rtn.append("\nDealt cards: \n");
        for (int k = cards.size() - 1; k >= size; k--) {
            rtn.append(cards.get(k));
            if (k != size) {
                rtn.append(", ");
            }
            if ((k - cards.size()) % 2 == 0) {
                // Insert carriage returns so entire deck is visible on console.
                rtn.append("\n");
            }
        }

        rtn.append("\n");
        return rtn.toString();
    }

}
