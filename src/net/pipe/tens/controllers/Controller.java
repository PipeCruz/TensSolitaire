package net.pipe.tens.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import net.pipe.tens.internal.Card;
import net.pipe.tens.internal.Deck;

import java.util.ArrayList;

public class Controller {
    private final Card[][] cards;
    private final ArrayList<Card> picked;
    //instance vars
    private Deck deck;
    int[] POINT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0};
    String[] SUITS = {"spades", "hearts", "diamonds", "clubs"};
    String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    private int wins = 0;
    private int total = 0;

    @FXML
    private GridPane grid;

    //creates a new Card array
    //generates deck
    public Controller() {
        this.cards = new Card[3][5];
        deck = new Deck(RANKS, SUITS, POINT_VALUES);
        picked = new ArrayList<>(13);
    }

    //adds images to the grid (visual)
    @FXML
    private void initialize() {
        addImagesToGrid();
        if (anotherPlayIsPossible()) {
            restart(false);
        }
    }

    @FXML
    private Label totalG, Wins, undealt;

    //clicks, selects a card
    private void select(MouseEvent e) {
        ImageView im = (ImageView) e.getSource();
        Integer row = GridPane.getRowIndex(im),
                col = GridPane.getColumnIndex(im);
        Card c = cards[row][col];
        if (c != null) {//fixme over here
            c.select();
            if (c.isSelected()) {
                picked.add(c);
            }else{
                picked.remove(c);
            }
        }
        if(deck.isEmpty() && c==null)
            grid.getChildren().remove(im);
    }

    //replaces a card with a new one
    @FXML
    private void replace() {
        if (picked.size() == 2) {
            Card one = picked.get(0),
                    two = picked.get(1);
            if (one.pointValue() + two.pointValue() == 10) {
                picked.remove(one);
                picked.remove(two);
                for (int r = 0; r < cards.length; r++) {
                    for (int c = 0; c < cards[r].length; c++) {
                        if (cards[r][c] != null) {
                            if (one.matches(cards[r][c]) || two.matches(cards[r][c])) {
                                cards[r][c] = deck.getSize() != 0 ? deck.deal() : null;
                            }
                        }
                    }
                }
            }
            if (deck.isEmpty()) {
                grid.getChildren().remove(one.getImage());
                grid.getChildren().remove(two.getImage());
            }
        }else if(picked.size()==4){
            Card one = picked.get(0),
                    two = picked.get(1),
                    three = picked.get(2),
                    four = picked.get(3);
            if(contains(one,two,three,four)){
                picked.remove(one);
                picked.remove(two);
                picked.remove(three);
                picked.remove(four);
                for (int i = 0; i < cards.length; i++) {
                    for (int j = 0; j < cards[i].length; j++) {
                        if (cards[i][j] != null) {
                            if (one.matches(cards[i][j])
                                    || two.matches(cards[i][j])
                                    || three.matches(cards[i][j])
                                    || four.matches(cards[i][j])) {
                                cards[i][j] = deck.getSize() != 0 ? deck.deal() : null;
                            }
                        }
                    }
                }
            }
            if (deck.isEmpty()) {
                grid.getChildren().remove(one.getImage());
                grid.getChildren().remove(two.getImage());
                grid.getChildren().remove(three.getImage());
                grid.getChildren().remove(four.getImage());
            }

        }
        update();
    }

    //checker method to see if the player can make another move
    private boolean anotherPlayIsPossible() {
        return !containsPairSum10() && contains10JQK("king")
                && contains10JQK("queen")
                && contains10JQK("jack")
                && contains10JQK("ten");
    }

    //checks if theres a pair sum 10
    private boolean containsPairSum10() {
        for (Card[] card : cards)
            for (int c = 0; c < card.length; c++)
                for (int t = c + 1; t < card.length; t++)
                    if (card[c] != null && card[t] != null)
                        if (card[c].pointValue() + card[t].pointValue() == 10)
                            return true;
        return false;
    }

    //checks if there is a 4 pair of 10 Jack Queen or King
    private boolean contains10JQK(String param) {
        boolean one,two,three,four;
        one = two = three = four = false;
        for(int r = 0; r < cards.length; r++){
            for(int c = 0; c < cards[r].length; c++) {
                if (!(r == 2 && (c == 0 || c == 4)) && cards[r][c] != null) {
                    if (cards[r][c].rank().equalsIgnoreCase(param))
                        one = true;
                    else if (one && cards[r][c].rank().equalsIgnoreCase(param))
                        two = true;
                    else if (two && cards[r][c].rank().equalsIgnoreCase(param))
                        three = true;
                    else if (three && cards[r][c].rank().equalsIgnoreCase(param))
                        four = true;
                }
            }
        }
        return !one || !two || !three || !four;
    }

    //checks if all 4 cards are contained
    private boolean contains(Card one, Card two, Card three, Card four){
        String rank = one.rank();
        return (one.pointValue() == 0
                && two.pointValue() == 0
                && three.pointValue() == 0
                && four.pointValue() == 0)
                &&
                (two.rank().equals(rank)
                        && three.rank().equals(rank)
                        && four.rank().equals(rank));
    }

    //updates the visual cards
    private void update() {
        for (Card[] c : cards) {
            for (Card ca : c) {
                if (ca != null) {
                    grid.getChildren().remove(ca.getImage());
                }
            }
        }
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[r].length; c++) {
                if (!(r == 2 && (c == 0 || c == 4))) {
                    if (cards[r][c] != null) {
                        ImageView im = cards[r][c].getImage();
                        im.setOnMouseClicked(this::select);
                        grid.add(im, c, r);
                    }
                }
            }
        }
        if (anotherPlayIsPossible() && deck.isEmpty()) {
            Wins.setText("Wins " + ++wins);
            return;
        }
        if (anotherPlayIsPossible()) {//FIXME
            undealt.setText("NO MOVES LEFT");
            undealt.setTextFill(Paint.valueOf("red"));
            return;
        }
        undealt.setText("Cards Left: " + deck.getSize());
    }

    //restarts the game
    public void restart(boolean b) {
        picked.clear();
        if (b) totalG.setText("Total Games: " + ++total);
        for (Card[] c : cards) {
            for (Card ca : c) {
                try {
                    grid.getChildren().remove(ca.getImage());
                } catch (Exception ignored) {
                }
            }
        }
        deck = new Deck(RANKS, SUITS, POINT_VALUES);
        addImagesToGrid();
        undealt.setText("Cards Left: " + deck.getSize());
        undealt.setTextFill(Paint.valueOf("white"));
        if (anotherPlayIsPossible()) {
            restart(false);
        }
    }

    //sets up visual deck
    private void addImagesToGrid() throws IllegalArgumentException {
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
    }

    //help dialog
    public void help() {//fixme
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("net/pipe/tens/files/css/icon.png"));
        alert.setHeaderText(null);
        alert.setContentText("Welcome to Tens!\n" +
                "To play this solitaire type game you must\n" +
                "--Select a pair of cards that add up to 10\n" +
                "or\n" +
                "--Select 4 face cards of the same rank, 10s included\n" +
                "-Select the \"REPLACE\" button\n" +
                "continue until there are no cards left\n" +
                "Good Luck!");
        alert.showAndWait();
    }

    //exit application
    public void exit(MouseEvent event) {
        Node n = (Node) event.getSource();
        Stage s = (Stage) n.getParent().getScene().getWindow();
        s.close();
    }

    //restart the game
    @FXML
    private void restart() {
        restart(true);
    }

}