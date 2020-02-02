package net.pipe.tens.internal;

import java.util.List;

public class TensBoard extends Board{

    private static final int BOARD_SIZE = 13;

    private static final String[] RANKS =
            {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

    private static final String[] SUITS =
            {"spades", "hearts", "diamonds", "clubs"};

    private static final int[] POINT_VALUES =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0};

    public TensBoard() { super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES); }

    @Override
    public boolean isLegal(List<Integer> selectedCards) {
        if (selectedCards.size() == 2) {
            return containsPairSum11(selectedCards);
        } else if (selectedCards.size() == 4) {
            return contains10sJsQsKs(selectedCards);
        } else {
            return false;
        }
    }

    private boolean contains10sJsQsKs(List<Integer> cards) {
        return false;
    }

    @Override
    public boolean anotherPlayIsPossible() {
        return false;
    }

    private boolean containsPairSum11(List<Integer> selectedCards) {
        for (int sk1 = 0; sk1 < selectedCards.size(); sk1++) {
            int k1 = selectedCards.get(sk1);
            for (int sk2 = sk1 + 1; sk2 < selectedCards.size(); sk2++) {
                int k2 = selectedCards.get(sk2);
                if (cardAt(k1).pointValue() + cardAt(k2).pointValue() == 10) {
                    return true;
                }
            }
        }
        return false;
    }

}
