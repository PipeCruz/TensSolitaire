package net.pipe.tens.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.pipe.tens.internal.Card;
import net.pipe.tens.internal.Deck;
import java.util.ArrayList;

public class Controller {


    private Deck deck;
    private Card[][] cards;
    private ArrayList<Card> picked;
    int[] POINT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0};
    String[] SUITS = {"spades", "hearts", "diamonds", "clubs"};
    String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    public Controller(){
        this.cards = new Card[3][5];

        deck = new Deck(RANKS, SUITS, POINT_VALUES);
        for(int i = 0; i < 39; i++)deck.deal();//todo temporary
        picked = new ArrayList<>(13);
    }

    @FXML
    private GridPane grid;
    @FXML
    private void initialize() {
        addImagesToGrid();
        System.out.println(anotherPlayIsPossible());
        if(!anotherPlayIsPossible()){
            restart(false);
        }
    }

    private void select(MouseEvent e) {
        ImageView im = (ImageView) e.getSource();
        Integer row = GridPane.getRowIndex(im),
                col = GridPane.getColumnIndex(im);
        System.out.println("row "+row+" col "+col);
        Card c = cards[row][col];
        if(c != null) {//fixme over here
            c.select();
            if (c.isSelected()) {
                picked.add(c);
            }else{
                picked.remove(c);
            }
            System.out.println(picked.toString());
        }
        if(deck.isEmpty() && c==null)
            grid.getChildren().remove(im);
    }


    @FXML
    private void replace() {//this is where the checking takes place
        if(picked.size()==2){
            Card one = picked.get(0),
                    two = picked.get(1);
            if(one.pointValue()+two.pointValue()==10) {
                picked.remove(one);
                picked.remove(two);
                for (int r = 0; r < cards.length; r++) {
                    for (int c = 0; c < cards[r].length; c++) {
                        if (cards[r][c] != null) {
                            if (one.matches(cards[r][c]) || two.matches(cards[r][c])) {
                                cards[r][c] = deck.getSize() != 0 ? deck.deal() : null;//todo fixme
                                System.out.println(deck.getSize());
                            }
                        }
                    }
                }
            }
            if(deck.isEmpty()){//fixme
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
                                System.out.println(deck.getSize());
                            }
                        }
                    }
                }
            }
            if(deck.isEmpty()){//fixme
                grid.getChildren().remove(one.getImage());
                grid.getChildren().remove(two.getImage());
                grid.getChildren().remove(three.getImage());
                grid.getChildren().remove(four.getImage());
            }

        }
        update();
        System.out.println("another move " + anotherPlayIsPossible());
        System.out.println("arraylist size " + picked.size());
    }

    @FXML private Label totalG,Wins,undealt;

    private void update() {
        for(Card[] c : cards){
            for(Card ca : c){
                if(ca!=null){
                    grid.getChildren().remove(ca.getImage());
                }
            }
        }
        for(int r = 0; r < cards.length;r++){
            for(int c = 0; c < cards[r].length; c++){
                if(!(r == 2 && (c==0 || c==4))){
                    if(cards[r][c]!=null){
                        ImageView im = cards[r][c].getImage();
                        im.setOnMouseClicked(this::select);
                        grid.add(im,c,r);
                    }
                }
            }
        }
        undealt.setText("Cards left: " + deck.getSize());
    }

    private boolean anotherPlayIsPossible(){
        return containsPairSum10() || contains10JQK("king")
          || contains10JQK("queen")
          || contains10JQK("jack")
          || contains10JQK("ten");
    }

    private boolean containsPairSum10() {
        for (Card[] card : cards)
            for (int c = 0; c < card.length; c++)
                for (int t = c + 1; t < card.length; t++)
                    if (card[c] != null && card[t] != null)
                        if (card[c].pointValue() + card[t].pointValue() == 10)
                            return true;
        return false;
    }

    private boolean contains10JQK(String param) {
        boolean one,two,three,four;
        one = two = three = four = false;
        for(int r = 0; r < cards.length; r++){
            for(int c = 0; c < cards[r].length; c++){
                if(!(r == 2 && (c==0 || c==4)) && cards[r][c]!=null){//todo fixme second
                    if(cards[r][c].rank().equalsIgnoreCase(param)) {
                        one = true;
                    }else if(one && cards[r][c].rank().equalsIgnoreCase(param)){
                        two = true;
                    }else if(two && cards[r][c].rank().equalsIgnoreCase(param)){
                        three = true;
                    }else if(three && cards[r][c].rank().equalsIgnoreCase(param)){
                        four = true;
                    }

                }
            }
        }
        return one && two && three && four;
    }

    private boolean contains(Card one, Card two, Card three, Card four){
        String rank = one.rank();
        return (   one.pointValue()       == 0
                && two.pointValue()    == 0
                && three.pointValue()  == 0
                && four.pointValue()   == 0)
                &&
                (  two.rank().equals(rank)
                && three.rank().equals(rank)
                && four.rank().equals(rank));
    }

    int tot = 0;
    public void restart(boolean b) {
        if(b) totalG.setText("Total Games: " + ++tot);
        for(Card[] c : cards)
            for(Card ca : c)
                try{
                    grid.getChildren().remove(ca.getImage());
                }catch (Exception ignored){

                }
        deck = new Deck(RANKS,SUITS,POINT_VALUES);
        addImagesToGrid();
        if(!anotherPlayIsPossible()){
            System.out.println("resetting");
            restart(false);
        }
    }

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

    public void help(MouseEvent event) {//fixme
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
                "-Select the \"REPLACE\" button");
        alert.showAndWait();
    }

    public void exit(MouseEvent event) {
        Node n = (Node)event.getSource();
        Stage s = (Stage) n.getParent().getScene().getWindow();
        s.close();
    }

    @FXML private void restart() {
        restart(true);
    }

}