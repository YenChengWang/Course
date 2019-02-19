import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException; 
class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private String identity = null;
    
    Player(String id){
    this.identity=id;
    } 
    
    public void discard(int i,Deck deck){ //input x_value
        hand.remove(i-1);
        if(deck.getdecktop()<40)
        {
            hand.add(deck.carddeck.get(deck.getdecktop()));
            deck.adddecktop();} //change the top card of deck
    }
    
    /*
    public void drawcard(Deck deck){
    hand.add(deck.get(getdecktop()));
    deck.adddecktop(); //change the top card of deck
    }**/
    //Get handcards
    
    public ArrayList<Card> gethandcard()
    {
    return hand;
    }
    public String getid(){
    return identity;
    }
}