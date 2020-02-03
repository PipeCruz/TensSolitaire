package net.pipe.tens.internal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

    //instance vars
    private final int pointValue;
    private boolean selected;
    private final String suit, rank, url, urlS;
    private final ImageView image;

    //constructs a card
    public Card(String cardRank, String cardSuit, int cardPointValue) {
        rank = cardRank;
        suit = cardSuit;
        pointValue = cardPointValue;
        selected = false;
        url = "/net/pipe/tens/files/cards/" + rank + suit + ".png";
        urlS = "/net/pipe/tens/files/cards/" + rank + suit + "S.png";
        image = new ImageView(new Image(url));
    }

    //selects card
    public void select(){
        selected = !selected;
        if(selected)
            image.setImage(new Image(urlS));
        else{
            image.setImage(new Image(url));
            selected=false;
        }
    }

    //getters
    public boolean isSelected() {
        return selected;
    }

    public String suit() {
        return suit;
    }

    public String rank() {
        return rank;
    }

    public int pointValue() {
        return pointValue;
    }

    public ImageView getImage() {
        return image;
    }

    //comparing method
    public boolean matches(Card otherCard) {
        return otherCard.suit().equals(this.suit())
                && otherCard.rank().equals(this.rank())
                && otherCard.pointValue() == this.pointValue();
    }

    //for debug purposes
    @Override
    public String toString() {
        return rank + " of " + suit + " (point value = " + pointValue + ")";
    }
}