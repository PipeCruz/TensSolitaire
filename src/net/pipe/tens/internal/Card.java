package net.pipe.tens.internal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    private String suit, rank, url, urlS;
    private int pointValue;
    private ImageView image;
    private boolean selected;

    public Card(String cardRank, String cardSuit, int cardPointValue) {
        rank = cardRank;
        suit = cardSuit;
        pointValue = cardPointValue;
        url  = "/net/pipe/tens/files/cards/" + rank + suit + ".png";
        urlS  = "/net/pipe/tens/files/cards/" + rank + suit + "S.png";
        image = new ImageView(new Image(url));
    }

    public ImageView getImage(){return image;}

    public void select(){image.setImage(new Image(urlS));}

    public String suit() {return suit;}

    public String rank() {return rank;}

    public int pointValue() {return pointValue;}

    public boolean matches(Card otherCard) {
        return otherCard.suit().equals(this.suit())
                && otherCard.rank().equals(this.rank())
                && otherCard.pointValue() == this.pointValue();
    }

    @Override
    public String toString() {
        return rank + " of " + suit + " (point value = " + pointValue + ")";
    }
}