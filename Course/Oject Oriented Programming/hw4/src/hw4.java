import java.util.*;
import java.lang.Iterable;
import foop.*;

public class hw4{

public static void main(String[] args){

int round = 1;
//Generate Object
POOCasino casino = new POOCasino();
//System.out.println("-------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-------------------");
CARD deck = new CARD(casino.getdeck());
Scanner type = new Scanner(System.in);
//System.out.println("-------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-------------------");

PlayerR03527017 Dealer = new PlayerR03527017(10000);
PlayerR03527017 P1 = new PlayerR03527017(Integer.valueOf(args[1]));
PlayerT04505201 P2 = new PlayerT04505201(Integer.valueOf(args[1]));
PlayerR03527017 P3 = new PlayerR03527017(Integer.valueOf(args[1]));
PlayerT04505201 P4 = new PlayerT04505201(Integer.valueOf(args[1]));
//PlayerR03527017 P1 = new PlayerR03527017(1000);
//PlayerT04505201 P2 = new PlayerT04505201(1000);
//PlayerR03527017 P3 = new PlayerR03527017(1000);
//PlayerT04505201 P4 = new PlayerT04505201(1000);


System.out.println("Welcome to BlackCasino");
System.out.println("Game Start!");
System.out.println("The chips for every player is "+Integer.valueOf(args[1]));
System.out.println("The game will be held for "+Integer.valueOf(args[0])+" rounds");
//System.out.println("The chips for every player is "+1000);
//System.out.println("The game will be held for "+3+" rounds");

while(round<=Integer.valueOf(args[0]))
//while(round <= 5)
{

if(round>1) 
	if(casino.get_decktop()>32)
	{
	casino.reset_decktop();
	Collections.shuffle(casino.getdeck());
	}

System.out.println("-------------------This is the  " + round + "  round.-------------------");
//
ArrayList<Card> Dhand = new ArrayList();
ArrayList<Card> hand1 = new ArrayList();
ArrayList<Card> hand2 = new ArrayList();
ArrayList<Card> hand3 = new ArrayList();
ArrayList<Card> hand4 = new ArrayList();

//Distribute cards
casino.distribute(Dhand,hand1,hand2,hand3,hand4); 

Hand Dh = new Hand(Dhand);
Hand h1 = new Hand(hand1);
Hand h2 = new Hand(hand2);
Hand h3 = new Hand(hand3);
Hand h4 = new Hand(hand4);
//

//(step 1)
//casino.make_ori_bet_USER(P1,P2,P3,P4,type);
casino.make_ori_bet_AI(P1,P2,P3,P4,type);

//(step 2)
casino.show_ini_status(Dh,h1,h2,h3,h4);

//(step 3)
casino.dealer_getace(Dh,h1,h2,h3,h4,P1,P2,P3,P4,type);

//(step 4)
boolean dealer_win = casino.checkdealer_win(Dh);

if(!dealer_win)
	casino.checksurrender(Dh,h1,h2,h3,h4,P1,P2,P3,P4);
else
	{
	casino.final_calculation(Dealer,P1,P2,P3,P4);
	System.out.println("This round is over");
	continue;
	}

//(step 5)
casino.open_facedown(Dh,h1,h2,h3,h4,P1,P2,P3,P4);
casino.checksplit(Dh,h1,h2,h3,h4,P1,P2,P3,P4);
casino.Do_D(Dh,h1,h2,h3,h4,P1,P2,P3,P4);
casino.organize_lasttable(); //renew last_table
casino.HIT(Dh,h1,h2,h3,h4,P1,P2,P3,P4);

//(step 6)
casino.dealer_action(Dealer,Dh);

//(step 7)
casino.final_calculation(Dealer,P1,P2,P3,P4);
//casino.show_currenttable();
casino.organize_lasttable();
casino.show_lasttable();
casino.clear_lasttable();
round++;
}
}
}