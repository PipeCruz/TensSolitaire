package net.pipe.tens.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import net.pipe.tens.internal.Card;
import net.pipe.tens.internal.Deck;

public class Controller {

    private Deck deck;
    private Card[][] cards;

    public Controller(){
        cards = new Card[3][5];
        int[] POINT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0};
        String[] SUITS = {"spades", "hearts", "diamonds", "clubs"};
        String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
        deck = new Deck(RANKS, SUITS, POINT_VALUES);
    }

    @FXML
    private GridPane grid;

    @FXML
    private void initialize(){
        for(int r = 0; r < cards.length;r++){
            for(int c = 0; c < cards[r].length; c++){
                if(!(r == 2 && (c==0 || c==4))){
                    cards[r][c]=deck.deal();
                    ImageView im = cards[r][c].getImage();
                    im.setOnMouseClicked(this::select);
                    grid.add(im,c,r);
                }
            }
        }
        System.out.println(deck.size());
    }

    private void select(MouseEvent e) {
        ImageView im = (ImageView) e.getSource();
        Integer row = GridPane.getRowIndex(im),
                col = GridPane.getColumnIndex(im);
        System.out.println("row "+row+" col "+col);
        cards[row][col].select();
    }

}
