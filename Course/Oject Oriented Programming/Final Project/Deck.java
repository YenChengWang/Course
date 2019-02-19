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
class Deck
{
    ArrayList<Card> carddeck =new ArrayList<>();
    private int decktop = 24;
    Deck()
    {
            carddeck.add(new Card(1,1,0,0,1,5));      //top to bottom                   , 3 cards total
            carddeck.add(new Card(1,1,0,0,1,5));
            carddeck.add(new Card(1,1,0,0,1,5));      //3
            carddeck.add(new Card(0,0,1,1,1,6));      //left to right                   , 4 cards total
            carddeck.add(new Card(0,0,1,1,1,6));
            carddeck.add(new Card(0,0,1,1,1,6));
            carddeck.add(new Card(0,0,1,1,1,6));      //4                        
            carddeck.add(new Card(1,0,0,1,1,7));      //top to right   or left to bottom, 4 cards total  vvv
            carddeck.add(new Card(1,0,0,1,1,7));
            carddeck.add(new Card(1,0,0,1,1,7));
            carddeck.add(new Card(1,0,0,1,1,7));      //4
            carddeck.add(new Card(1,0,1,0,1,8));      //top to left   or right to bottom, 5 cards total  vvv
            carddeck.add(new Card(1,0,1,0,1,8)); 
            carddeck.add(new Card(1,0,1,0,1,8)); 
            carddeck.add(new Card(1,0,1,0,1,8)); 
            carddeck.add(new Card(1,0,1,0,1,8));      //5
            carddeck.add(new Card(1,0,1,1,1,9));      //right left+ (top/bottom)        , 5 cards total
            carddeck.add(new Card(1,0,1,1,1,9));
            carddeck.add(new Card(1,0,1,1,1,9));
            carddeck.add(new Card(1,0,1,1,1,9));
            carddeck.add(new Card(1,0,1,1,1,9));      //5
            carddeck.add(new Card(1,1,1,0,1,10));      //top bottom+ (left/right)        , 5 cards total
            carddeck.add(new Card(1,1,1,0,1,10));
            carddeck.add(new Card(1,1,1,0,1,10));
            carddeck.add(new Card(1,1,1,0,1,10));
            carddeck.add(new Card(1,1,1,0,1,10));      //5
            carddeck.add(new Card(1,1,1,1,1,11));      //all open                        , 5 cards total
            carddeck.add(new Card(1,1,1,1,1,11));
            carddeck.add(new Card(1,1,1,1,1,11));
            carddeck.add(new Card(1,1,1,1,1,11));
            carddeck.add(new Card(1,1,1,1,1,11));      //5
            carddeck.add(new Card(1,1,1,1,0,12));      //all close                       , 1 card  total
            carddeck.add(new Card(1,1,0,0,0,13));      //top bottom close                , 1 card  total
            carddeck.add(new Card(0,0,1,1,0,14));      //left right close                , 1 card  total
            carddeck.add(new Card(1,0,0,1,0,15));      //(top right / left bottom) close , 1 card  total
            carddeck.add(new Card(1,0,1,0,0,16));      //(top left / right bottom) close , 1 card  total
            carddeck.add(new Card(1,0,1,1,0,17));      //left right + (up/bottom)  close , 1 card  total
            carddeck.add(new Card(1,1,1,0,0,18));      //top bottom + (left/right) close , 1 card  total
            carddeck.add(new Card(1,0,0,0,0,19));      //(top/bottom) close              , 1 card  total
            carddeck.add(new Card(0,0,1,0,0,20));      //(left/right) close              , 1 card  total            
                                                                                    // 40 cards total
        Collections.shuffle(carddeck);
    }
    
    //distribute 6 cards to each player
    public void distribute(Player P1,Player P2,Player P3,Player P4){
    for(int i = 0;i<24;i+=4)
        {
           // System.out.println(carddeck.size());
        P1.gethandcard().add(carddeck.get(i));
        P2.gethandcard().add(carddeck.get(i+1));
        P3.gethandcard().add(carddeck.get(i+2));
        P4.gethandcard().add(carddeck.get(i+3));
        }
    }
    //Players draw 1 card for his/her own turn
    public void adddecktop(){
        decktop++;
    }
    //return deck top
    public int getdecktop(){
        return decktop;
    }
}