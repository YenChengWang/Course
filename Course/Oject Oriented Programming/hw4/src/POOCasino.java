import java.util.*;
import java.lang.Iterable;
import foop.*;

class POOCasino {
private int decktop = 0; //record the current card on the top of the deck
private int ttvalue1,ttvalue2,ttvalue3,ttvalue4,ttvalueD = 0;
private int bet1,bet2,bet3,bet4 = 0; //add own var
private boolean insur1,insur2,insur3,insur4 = false;
private boolean surr1,surr2,surr3,surr4 = false;
private boolean dd1,dd2,dd3,dd4 = false;
private boolean split1,split2,split3,split4 = false;
private boolean bust1,bust2,bust3,bust4 = false;

//plus private
private ArrayList<Hand> current_table = new ArrayList<Hand>();
private ArrayList<Hand> last_table = new ArrayList<Hand>();
private ArrayList<Hand> empty_table = new ArrayList<Hand>();



private ArrayList<Card> tthand1 = new ArrayList();
private ArrayList<Card> tthand2 = new ArrayList();
private ArrayList<Card> tthand3 = new ArrayList();
private ArrayList<Card> tthand4 = new ArrayList();
private ArrayList<Card> tthandD = new ArrayList();


private ArrayList<Card> deck = new ArrayList();
private ArrayList<Integer> bet_list = new ArrayList();

public ArrayList<Card> getdeck(){
	return deck;
}

public String showcard(Card Crd){
	String card = null;
	switch(Crd.getSuit())
	{
	case 1:
		switch(Crd.getValue())
		{
		case 1:
			card = "SA";
			break;
		case 11:
			card = "SJ";
			break;
		case 12:
			card = "SQ";
			break;
		case 13:
			card = "SK";
			break;
		default:
			card = "S"+String.valueOf(Crd.getValue());
			break;
		}
		break;
	case 2:
		{
		switch(Crd.getValue())
		{
		case 1:
			card = "HA";
			break;
		case 11:
			card = "HJ";
			break;
		case 12:
			card = "HQ";
			break;
		case 13:
			card = "HK";
			break;
		default:
			card = "H"+String.valueOf(Crd.getValue());
			break;
		}
		}
		break;
	case 3:
		{
		switch(Crd.getValue())
		{
		case 1:
			card = "DA";
			break;
		case 11:
			card = "DJ";
			break;
		case 12:
			card = "DQ";
			break;
		case 13:
			card = "DK";
			break;
		default:
			card = "D"+String.valueOf(Crd.getValue());
			break;
		}
		}
		break;
	case 4:
		{
		switch(Crd.getValue()){
		case 1:
			card = "CA";
			break;
		case 11:
			card = "CJ";
			break;
		case 12:
			card = "CQ";
			break;
		case 13:
			card = "CK";
			break;
		default:
			card = "C"+String.valueOf(Crd.getValue());
			break;
		}
		}
		break;
	}
	return card;
}

public void reset_decktop(){
	decktop = 0;
}

public int get_decktop(){
	return decktop;
}

//(1) Ask every player to make an initial debt (AI make_bet)
public void make_ori_bet_AI(PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4,Scanner type)
{
//Ask Player 1~4 to make an initial bet
System.out.println("-------------------Now start bet making part.-------------------");
System.out.println("Now, every player should make an initial bet");
bet1 = P1.make_bet(last_table,4,0);
while(true){
try{
P1.decrease_chips(bet1);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet1 = -bet1;}
 catch (Player.BrokeException be){
System.out.println("Player 1 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player1 make a bet " + bet1);


bet2 = P2.make_bet(last_table,4,1);
while(true){
try{
P2.decrease_chips(bet2);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet2 = -bet2;}
 catch (Player.BrokeException be){
System.out.println("Player 2 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player2 make a bet " + bet2);

bet3 = P3.make_bet(last_table,4,2);
while(true){
try{
P3.decrease_chips(bet3);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet3 = -bet3;}
 catch (Player.BrokeException be){
System.out.println("Player 3 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player3 make a bet " + bet3);


bet4 = P4.make_bet(last_table,4,3);
while(true){
try{
P4.decrease_chips(bet4);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet4 = -bet4;}
 catch (Player.BrokeException be){
System.out.println("Player 4 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player4 make a bet " + bet4);
}


//(1) Ask every player to make an initial debt (Let user make bet)
public void make_ori_bet_USER(PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4,Scanner type)
{
//Ask Player 1~4 to make an initial bet
System.out.println("Now, every player should make an initial bet");
System.out.println("Player1 please make a bet");
bet1 = type.nextInt(); //revise
while(true){
try{
P1.decrease_chips(bet1);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet1 = -bet1;}
 catch (Player.BrokeException be){
System.out.println("Player 1 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player1 make a bet " + bet1);


System.out.println("Player2 please make a bet");
bet2 = type.nextInt();
while(true){
try{
P2.decrease_chips(bet2);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet2 = -bet2;}
 catch (Player.BrokeException be){
System.out.println("Player 2 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player2 make a bet " + bet2);

System.out.println("Player3 please make a bet");
bet3 = type.nextInt();
while(true){
try{
P3.decrease_chips(bet3);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet3 = -bet3;}
 catch (Player.BrokeException be){
System.out.println("Player 3 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player3 make a bet " + bet3);

System.out.println("Player4 please make a bet");
bet4 = type.nextInt();
while(true){
try{
P4.decrease_chips(bet4);}
catch (Player.NegativeException ne)
{System.out.println("You bet negative number and we change it to positive");
 bet4 = -bet4;}
 catch (Player.BrokeException be){
System.out.println("Player 4 is in bankruptcy!! Chips become negative!");
 }
break;
}
System.out.println("Player4 make a bet " + bet4);
}







//(2)Assign 2 cards to all players
public void distribute(ArrayList<Card> Dhand,  ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> hand3, ArrayList<Card> hand4)
{
System.out.println("-------------------Now start card assigning part.-------------------");
//Distribute face up card
Dhand.add(deck.get(decktop));
decktop++;
hand1.add(deck.get(decktop));
decktop++;
hand2.add(deck.get(decktop));
decktop++;
hand3.add(deck.get(decktop));
decktop++;
hand4.add(deck.get(decktop));
decktop++;
//Distribute face down card
Dhand.add(deck.get(decktop));
decktop++;
hand1.add(deck.get(decktop));
decktop++;
hand2.add(deck.get(decktop));
decktop++;
hand3.add(deck.get(decktop));
decktop++;
hand4.add(deck.get(decktop));
decktop++;
/* check card assigning part
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
System.out.println(showcard(hand1.get(0))+" "+showcard(hand1.get(1))+" "+showcard(hand2.get(0))+" "+showcard(hand2.get(1))+" "+
showcard(hand3.get(0))+" "+showcard(hand3.get(1))+" "+showcard(hand4.get(0))+" "+showcard(hand4.get(1))+" ");
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");**/

}

//Show the initial status
void show_ini_status(Hand Dh,Hand h1,Hand h2,Hand h3,Hand h4){
System.out.println("The current situation is "+ "Dealer - "+ showcard((Dh.getCards()).get(0)) +" ,P1 - "+showcard((h1.getCards()).get(0))
+" ,P2 - "+showcard((h2.getCards()).get(0))+" ,P3 - "+showcard(h3.getCards().get(0))
+" ,P4 - "+showcard((h4.getCards()).get(0)));

//renew current table
current_table.add(Dh);
current_table.add(h1);
current_table.add(h2);
current_table.add(h3);
current_table.add(h4);
}

//check dealer's card and buy insurance
void dealer_getace(Hand Dh,Hand h1,Hand h2,Hand h3,Hand h4,PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4,Scanner type){
System.out.println("The dealer's face up card is  " + showcard((Dh.getCards()).get(0))); 

if(((Dh.getCards()).get(0)).getValue()==1)
	{System.out.println("-------------------Now start insurance buying part.-------------------");
	System.out.println("The dealer's Face up card is Ace, all the players must decide whether to buy an insurance");
	//Player 1 insurance
	if(P1.buy_insurance(h1.getCards().get(0),Dh.getCards().get(0),current_table))
		{
		System.out.println("Player 1 bought an insurance"); 
		try{
		P1.decrease_chips(0.5*bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;}
		catch (Player.BrokeException be){
		System.out.println("Player 1 is in bankruptcy!! Chips become negative!");
		}
		}
	else
		System.out.println("Player 1 didn't buy an insurance");
	
	//Player 2 insurance
	if(P2.buy_insurance(h2.getCards().get(0),Dh.getCards().get(0),current_table))
		{
		System.out.println("Player 2 bought an insurance"); 
		try{
		P2.decrease_chips(0.5*bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;}
		catch (Player.BrokeException be){
		System.out.println("Player 2 is in bankruptcy!! Chips become negative!");
		}
		}
	else
		System.out.println("Player 2 didn't buy an insurance");
	
	//Player 3 insurance
	if(P3.buy_insurance(h3.getCards().get(0),Dh.getCards().get(0),current_table))
		{
		System.out.println("Player 3 bought an insurance");
		try{
		P3.decrease_chips(0.5*bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;}
		catch (Player.BrokeException be){
		System.out.println("Player 3 is in bankruptcy!! Chips become negative!");
		}
		}
	else
		System.out.println("Player 3 didn't buy an insurance");


	if(P4.buy_insurance(h4.getCards().get(0),Dh.getCards().get(0),current_table))
		{
		System.out.println("Player 4 bought an insurance"); 
		try{
		P4.decrease_chips(0.5*bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;}
		catch (Player.BrokeException be){
		System.out.println("Player 4 is in bankruptcy!! Chips become negative!");
		}
		}
	else
		System.out.println("Player 4 didn't buy an insurance");
	}
}


public boolean checkdealer_win(Hand Dh)
{
boolean D_win = false;
System.out.println("The dealer's face up card is " + showcard((Dh.getCards()).get(0)) + " face down card is " + showcard((Dh.getCards()).get(1)));
/*
if((Dh.getCards()).get(0).getValue()==1)
	{if((Dh.getCards()).get(1).getValue() == 11 || (Dh.getCards()).get(1).getValue() == 12 || (Dh.getCards()).get(1).getValue() == 13)
		System.out.println("The dealer get a Blackjack, the dealer wins");
		D_win = true;}
else if((Dh.getCards()).get(1).getValue() == 11 || (Dh.getCards()).get(1).getValue() == 12 || (Dh.getCards()).get(1).getValue() == 13)
	{if((Dh.getCards()).get(0).getValue()==1)
		System.out.println("The dealer get a Blackjack, the dealer wins");
		D_win = true;}
else
	D_win = false;**/
ArrayList<Card> Dhand = Dh.getCards();

if(cal_point(Dhand)==21 || cal_point(Dhand)==11)
	{
	System.out.println("The dealer get a Blackjack, the dealer wins");
	D_win = true;
	}
else
	D_win = false;

return D_win;
}

//Check if dealer get blackjack
public void checksurrender(Hand Dh,Hand h1, Hand h2, Hand h3, Hand h4, PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4)
{
	System.out.println("-------------------Now start surrender deciding part.-------------------");
	//Ask Player1~Player4 to decide whether to surrender
	System.out.println("The dealer doesn't get a Blackjack,all players can choose whether surrender or not");
	surr1 = P1.do_surrender(h1.getCards().get(0),Dh.getCards().get(0),current_table);
	surr2 = P2.do_surrender(h2.getCards().get(0),Dh.getCards().get(0),current_table);
	surr3 = P3.do_surrender(h3.getCards().get(0),Dh.getCards().get(0),current_table);
	surr4 = P4.do_surrender(h4.getCards().get(0),Dh.getCards().get(0),current_table);
	
	
	if(surr1)
		System.out.println("Player 1 surrendered");
	else
		System.out.println("Player 1 didn't surrender");

	if(surr2)
		System.out.println("Player 2 surrendered");
	else
		System.out.println("Player 2 didn't surrender");

	if(surr3) 
		System.out.println("Player 3 surrendered");
	else
		System.out.println("Player 3 didn't surrender");

	if(surr4) 
		System.out.println("Player 4 surrendered");
	else
		System.out.println("Player 4 didn't surrender");
}

//Flit up the face down card
public void open_facedown(Hand Dh,Hand h1,Hand h2,Hand h3,Hand h4,PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4){ 
//give parameter current_table------------------------
	if(!surr1){
		System.out.println("Player 1's face down card is  " + showcard((h1.getCards()).get(1)));
		//current_table.add((h1.getCards()).get(1));
		}
	if(!surr2){
		System.out.println("Player 2's face down card is  " + showcard((h2.getCards()).get(1)));
		//current_table.add((h2.getCards()).get(1));
		}
	if(!surr3)
		{System.out.println("Player 3's face down card is  " + showcard((h3.getCards()).get(1)));
		//current_table.add((h3.getCards()).get(1));
		}
	if(!surr4)
		{System.out.println("Player 4's face down card is  " + showcard((h4.getCards()).get(1)));
		//current_table.add((h4.getCards()).get(1));
		}
}


//Check split
public void checksplit(Hand Dh,Hand h1,Hand h2,Hand h3,Hand h4,PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4){
if(!surr1)
	if((h1.getCards()).get(0).getValue() == (h1.getCards()).get(1).getValue())
		{
		System.out.println("-------------------Split deciding part.-------------------");
		System.out.println("Player1's two hand cards have same value, he/she can choose whether to split!");
		split1 = P1.do_split((h1.getCards()),(Dh.getCards()).get(0),current_table);
		if(!split1)
			System.out.println("Player1 don't want to split! ");
		}

if(!surr2)
	if((h2.getCards()).get(0).getValue() == (h2.getCards()).get(1).getValue())
		{
		System.out.println("-------------------Split deciding part.-------------------");
		System.out.println("Player2's two hand cards have same value, he/she can choose whether to split!");
		split2 = P2.do_split(h2.getCards(),(Dh.getCards()).get(0),current_table);
		if(!split2)
			System.out.println("Player2 don't want to split! ");
		}
if(!surr3)
	if((h3.getCards()).get(0).getValue() == (h3.getCards()).get(1).getValue())
		{
		System.out.println("-------------------Split deciding part.-------------------");
		split3 = P3.do_split((h3.getCards()),(Dh.getCards()).get(0),current_table);
		System.out.println("Player3's two hand cards have same value, he/she can choose whether to split!");
		if(!split3)
			System.out.println("Player3 don't want to split! ");
		}
if(!surr4)
	if((h4.getCards()).get(0).getValue() == (h4.getCards()).get(1).getValue())
		{
		System.out.println("-------------------Split deciding part.-------------------");
		System.out.println("Player4's two hand cards have same value, he/she can choose whether to split!");
		split4 = P4.do_split((h4.getCards()),(Dh.getCards()).get(0),current_table);
		if(!split4)
			System.out.println("Player4 don't want to split! ");
		}
}

//decide whether to double down
public void Do_D(Hand Dh,Hand h1,Hand h2,Hand h3,Hand h4,PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4)
{
System.out.println("-------------------Now start doing double bet part.-------------------");
if(!surr1){
	dd1 = P1.do_double(h1,Dh.getCards().get(0),current_table);
	if(dd1)
		{
		System.out.println("Player 1 did double!!!");
		try{
		P1.decrease_chips(bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;}
		catch (Player.BrokeException be){
		System.out.println("Player 1 is in bankruptcy!! Chips become negative!");
		};
		bet1*=2;
		}
	else
		System.out.println("Player 1 didn't do double!!!");
		
		
		}
if(!surr2){
	dd2 = P2.do_double(h2,Dh.getCards().get(0),current_table);
	if(dd2)
		{
		System.out.println("Player 2 did double!!!");
		try{
		P2.decrease_chips(bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;}
		catch (Player.BrokeException be){
		System.out.println("Player 2 is in bankruptcy!! Chips become negative!");
		};
		bet2*=2;
		}
	else
		System.out.println("Player 2 didn't do double!!!");
		}
if(!surr3){
	dd3 = P3.do_double(h3,Dh.getCards().get(0),current_table);
	if(dd3)
		{
		System.out.println("Player 3 did double!!!");
		try{
		P3.decrease_chips(bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;}
		catch (Player.BrokeException be){
		System.out.println("Player 3 is in bankruptcy!! Chips become negative!");
		};
		bet3*=2;
		}
	else
		System.out.println("Player 3 didn't do double!!!");
		}
if(!surr4){
	dd4 = P4.do_double(h4,Dh.getCards().get(0),current_table);
	if(dd4)
		{
		System.out.println("Player 4 did double!!!");
		try{
		P4.decrease_chips(bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;}
		catch (Player.BrokeException be){
		System.out.println("Player 4 is in bankruptcy!! Chips become negative!");
		};
		bet4*=2;
		}
	else
		System.out.println("Player 4 didn't do double!!!");
		}
	
}

//calculate total point
public int cal_point(ArrayList<Card> card)
{
int ttvalue = 0;
for(int i = 0;i<card.size();i++)
	{
	if (card.get(i).getValue()>10)
		ttvalue +=10;
	else
		ttvalue += card.get(i).getValue();
	}
	
for(int j = 0;j<card.size();j++)
	{if(card.get(j).getValue() == 1)
	{
	if((ttvalue-1+11) < 22)
		ttvalue=ttvalue+10;
	}
	}
	
	return ttvalue;
}


public void HIT(Hand Dh,Hand h1,Hand h2,Hand h3,Hand h4,PlayerR03527017 P1,PlayerT04505201 P2,PlayerR03527017 P3,PlayerT04505201 P4)
{
System.out.println("-------------------Now start hitting part.-------------------");
current_table.clear();
//-------------------------------Player 1 hit----------------------------------------
if(!surr1){
tthand1 = h1.getCards();
while(P1.hit_me(h1,Dh.getCards().get(0),current_table))
{
	tthand1 = h1.getCards();
	System.out.println("Player1 choose to hit.");
	System.out.println("The card is  " + showcard(deck.get(decktop)));
	tthand1.add(deck.get(decktop));
	decktop++;
	h1 = new Hand(tthand1);
}
//Renew current_table
current_table.add(h1);
//show final stage
System.out.print("Player1's all cards are  ");
for(int i = 0;i<tthand1.size();i++)
	System.out.print(showcard((tthand1).get(i)) + "  ");
System.out.println("");
ttvalue1 = cal_point(tthand1);  //calculate Player1's points
if(ttvalue1>21)
	System.out.println("Player 1 get busted!!!!!");
else
	System.out.println("Player1 choose to stand.");

System.out.println("Player 1 get " + ttvalue1 + " points.!");
		}
else  //Restore used card
	current_table.add(h1);
//-------------------------------Player 2 hit----------------------------------------
if(!surr2){
tthand2 = h2.getCards();
while(P2.hit_me(h2,Dh.getCards().get(0),current_table))
{
	tthand2 = h2.getCards();
	System.out.println("Player2 choose to hit.");
	System.out.println("The card is  " + showcard(deck.get(decktop)));
	tthand2.add(deck.get(decktop));
	decktop++;
	h2 = new Hand(tthand2);
}
//Renew current_table
current_table.add(h2);
//show final stage
System.out.print("Player2's all cards are  ");
for(int i = 0;i<tthand2.size();i++)
	System.out.print(showcard((tthand2).get(i))+"  ");
System.out.println("");
ttvalue2 = cal_point(tthand2);  //calculate Player2's points
if(ttvalue2>21)
	System.out.println("Player 2 get busted!!!!!");
else
	System.out.println("Player2 choose to stand.");

System.out.println("Player 2 get " + ttvalue2 + " points.!");
}
else  //Restore used card
	current_table.add(h2);
//-------------------------------Player 3 hit----------------------------------------
if(!surr3){
tthand3 = h3.getCards();
while(P3.hit_me(h3,Dh.getCards().get(0),current_table))
{
	tthand3 = h3.getCards();
	System.out.println("Player3 choose to hit.");
	System.out.println("The card is  " + showcard(deck.get(decktop)));
	tthand3.add(deck.get(decktop));
	decktop++;
	h3 = new Hand(tthand3);
}
//Renew current_table
current_table.add(h3);
//show final stage
System.out.print("Player3's all cards are  ");
for(int i = 0;i<tthand3.size();i++)
	System.out.print(showcard((tthand3).get(i)) + "  ");
System.out.println("");
ttvalue3 = cal_point(tthand3);  //calculate Player2's points

if(ttvalue3>21)
	System.out.println("Player 3 get busted!!!!!");
else
	System.out.println("Player3 choose to stand.");
System.out.println("Player 3 get " + ttvalue3 + " points.!");
}
else  //Restore used card
	current_table.add(h3);
//-------------------------------Player 4 hit----------------------------------------
if(!surr4){
tthand4 = h4.getCards();
while(P4.hit_me(h4,Dh.getCards().get(0),current_table))
{
	tthand4 = h4.getCards();
	System.out.println("Player4 choose to hit.");
	System.out.println("The card is  " + showcard(deck.get(decktop)));
	tthand4.add(deck.get(decktop));
	decktop++;
	h4 = new Hand(tthand4);
}
//Renew current_table
current_table.add(h4);
//show final stage
System.out.print("Player4's all cards are  ");
for(int i = 0;i<tthand4.size();i++)
	System.out.print(showcard((tthand4).get(i))+"  ");
System.out.println("");
ttvalue4 = cal_point(tthand4);  //calculate Player2's points

if(ttvalue4>21)
	System.out.println("Player 4 get busted!!!!!");
else
	System.out.println("Player4 choose to stand.");
System.out.println("Player 4 get " + ttvalue4 + " points.!");
}
else  //Restore used card
	current_table.add(h4);
//-----------------------------------------------------------------------
System.out.println("-------------------Now start dealer's hitting part.-------------------");
}
//(6) dealer action
//Check whether dealer has to hit
public boolean hit_dealer(PlayerR03527017 Dealer,Hand Dh)
{
	boolean hit_dealer = false;
	int dealer_value = 0;
	for(int i = 0;i<Dh.getCards().size();i++)
	{
	int j=0;
	if(Dh.getCards().get(i).getValue()>10)
		j = 10;
	else
		j = Dh.getCards().get(i).getValue();
	
	dealer_value += j;
	}
	
	if(dealer_value <= 16)
		hit_dealer = true;
	else if(dealer_value == 17)
		{if((Dh.getCards()).get(0).getValue()==1)
			hit_dealer = true;
		else if((Dh.getCards()).get(1).getValue()==1)
			hit_dealer = true;
		else
			hit_dealer = false;}
	else
		hit_dealer = false;
	return hit_dealer;}
//Deal with hitting of dealer
public void dealer_action(PlayerR03527017 Dealer,Hand Dh)
{
	boolean HIT = hit_dealer(Dealer,Dh);
	tthandD = Dh.getCards();
	//show first stage
	System.out.print("Dealer's cards are  ");
	for(int i = 0;i<tthandD.size();i++)
		System.out.print(showcard((tthandD).get(i)) + "  ");
	while(HIT)
	{
	tthandD = Dh.getCards();
	System.out.println("The Dealer's card value <17 or got soft 17, hit!");
	System.out.println("The hit card is  " + showcard(deck.get(decktop)));
	tthandD.add(deck.get(decktop));
	decktop++;
	Dh = new Hand(tthandD);
	HIT = hit_dealer(Dealer,Dh);
	}
	//Renew current_table
	current_table.add(Dh);
	ttvalueD = cal_point(tthandD);
	//show final stage
	System.out.print("Dealer's all cards are  ");
	for(int i = 0;i<tthandD.size();i++)
		System.out.print(showcard((tthandD).get(i)) + "  ");
	System.out.println("");
	if(ttvalueD < 22)
		System.out.println("The Dealer stand, dealer's total point is  " + cal_point(tthandD));
	else if(ttvalueD > 21)
		{
		System.out.println("The Dealer stand, dealer's total point is  " + cal_point(tthandD));
		System.out.println("The Dealer is busted");
		}
}


//(7) Final Calculation
public void final_calculation(PlayerR03527017 Dealer, PlayerR03527017 P1, PlayerT04505201 P2, PlayerR03527017 P3, PlayerT04505201 P4)
{
System.out.println("-------------------Now start chips calculating part.-------------------");
boolean finish1 = false; //avoid repeatly calculating
boolean finish2 = false;
boolean finish3 = false;
boolean finish4 = false;

//Surrender
if(surr1)
	{
	System.out.println("Player1 chose to surrender and get back 0.5 bet chips");
	try{
	P1.increase_chips(0.5*bet1);}
	catch (Player.NegativeException ne)
	{System.out.println("You bet negative number and we change it to positive");
	bet1 = -bet1;}
	finish1 = true;}
if(surr2)
	{
	System.out.println("Player2 chose to surrender and get back 0.5 bet chips");
	try{
	P2.increase_chips(0.5*bet2);}
	catch (Player.NegativeException ne)
	{System.out.println("You bet negative number and we change it to positive");
	bet2 = -bet2;}
	finish2 = true;}
if(surr3)
	{
	System.out.println("Player3 chose to surrender and get back 0.5 bet chips");
	try{
	P3.increase_chips(0.5*bet3);}
	catch (Player.NegativeException ne)
	{System.out.println("You bet negative number and we change it to positive");
	bet3 = -bet3;}
	finish3 = true;}
if(surr4)
	{
	System.out.println("Player4 chose to surrender and get back 0.5 bet chips");
	try{
	P4.increase_chips(0.5*bet4);}
	catch (Player.NegativeException ne)
	{System.out.println("You bet negative number and we change it to positive");
	bet4 = -bet4;}
	finish4 = true;}

//Dealer get Black Jack
if(ttvalueD == 21)
	{
	System.out.println("Dealer got 21 points (Black Jack), all players who didn't surrender lose his/her chips");
	if(ttvalue1 != 21)
		if(insur1)
			{try{
			P1.increase_chips(bet1);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet1 = -bet1;};}
		else
			{try{
			P1.increase_chips(0);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet1 = -bet1;};}

	if(ttvalue2 != 21)
		if(insur2)
			{try{
			P2.increase_chips(bet2);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet2 = -bet2;};}
		else
			{try{
			P2.increase_chips(0);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet2 = -bet2;};}

	if(ttvalue3 != 21)
		if(insur3)
			{try{
			P3.increase_chips(bet3);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet3 = -bet3;};}
		else
			{try{
			P3.increase_chips(0);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet3 = -bet3;};}

	if(ttvalue4 != 21)
		if(insur4)
			{try{
			P4.increase_chips(bet4);}
			catch (Player.NegativeException ne)
			{System.out.println("You bet negative number and we change it to positive");
			bet4 = -bet4;};}
			else
				{try{
				P4.increase_chips(0);}
				catch (Player.NegativeException ne)
				{System.out.println("You bet negative number and we change it to positive");
				bet4 = -bet4;};}
		
	//System.out.println("Player 1 now has chips  " + P1.toString());
	//System.out.println("Player 2 now has chips  " + P2.toString());
	//System.out.println("Player 3 now has chips  " + P3.toString());
	//System.out.println("Player 4 now has chips  " + P4.toString());
	finish1 = true; 
	finish2 = true;
	finish3 = true;
	finish4 = true;
	}

//Busted
if(!finish1)
	if(ttvalue1>21)
		{
		try{
		P1.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;}
		bust1 = true;
		finish1 = true;
		System.out.println("Player1 get busted and lose his/her bet!!");
		}
if(!finish2)
	if(ttvalue2>21)
		{try{
		P2.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;}
		bust2 = true;
		finish2 = true;
		System.out.println("Player2 get busted and lose his/her bet!!");
		}
if(!finish3)
	if(ttvalue3>21)
		{try{
		P3.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;}
		bust3 = true;
		finish3 = true;
		System.out.println("Player3 get busted and lose his/her bet!!");
		}
if(!finish4)
	if(ttvalue4>21)
		{
		try{
		P4.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;}
		bust4 = true;
		finish4 = true;
		System.out.println("Player4 get busted and lose his/her bet!!");
		}


//Dealer get busted
if(ttvalueD > 21)
	{
	System.out.println("Dealer got busted, anyone who didn't surrender/busted get back their bet!");
	//Player 1
if(!surr1 && !bust1){
	try{ //2*bet -> only bet
		P1.increase_chips(bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;}
		//System.out.println("Player 1 now has chips  " + P1.toString());
		}
	//Player 2
if(!surr2 && !bust2){
	try{
		P2.increase_chips(bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;}
		//System.out.println("Player 2 now has chips  " + P2.toString());
		}
	//Player 3
if(!surr3 && !bust3){
	try{
		P3.increase_chips(bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;}
		//System.out.println("Player 3 now has chips  " + P3.toString());
		}
if(!surr4 && !bust4){
	//Player 4
	try{
		P4.increase_chips(bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;}
		//System.out.println("Player 4 now has chips  " + P4.toString());
		}
	
	finish1 = true; 
	finish2 = true;
	finish3 = true;
	finish4 = true;
	}

//Player get BlackJack 
if(ttvalue1 == 21){
	if(ttvalueD != 21)
		{
		System.out.println("Player 1 get Black Jack!");
		try{
		P1.increase_chips(2.5*bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;};
		finish1 = true;}
	else
		{try{
		P1.increase_chips(bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;};}
		
if(ttvalue2 == 21)
	if(ttvalueD != 21)
		{
		System.out.println("Player 2 get Black Jack!");
		try{
		P2.increase_chips(2.5*bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;};
		finish2 = true;}
	else
		{
		try{
		P2.increase_chips(bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;};}
if(ttvalue3 == 21)
	if(ttvalueD  != 21)
		{
		System.out.println("Player 3 get Black Jack!");
		try{
		P3.increase_chips(2.5*bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;};
		finish3 = true;}
	else
		{try{
		P3.increase_chips(bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;};}
if(ttvalue4 == 21)
	if(ttvalueD  != 21)
		{
		System.out.println("Player 4 get Black Jack!");
		try{
		P4.increase_chips(2.5*bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;};
		finish4 = true;}
	else
		{try{
		P4.increase_chips(bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;};}



}

//Compare the point value
//if(!bust1 && !finish1)
if(!finish1)
	if(ttvalue1 > ttvalueD)
		{
		System.out.println("Player 1 get higher points than Dealer! Win his/her bet!");
		try{
		P1.increase_chips(2*bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;};}
	else if(ttvalue1 == ttvalueD)
		{
		System.out.println("Player 1 get equal points as Dealer! get back his/her bet");
		try{
		P1.increase_chips(bet1);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;};}
	else
		{
		System.out.println("Player 1 get lower point than Dealer! Lose his/her bet!");
		try{
		P1.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet1 = -bet1;};}
		
if(!finish2)
	if(ttvalue2 > ttvalueD)
		{
		System.out.println("Player 2 get higher point than Dealer! Win his/her bet!");
		try{
		P2.increase_chips(2*bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;};}
	else if(ttvalue2 == ttvalueD)
		{
		System.out.println("Player 2 get equal points as Dealer! get back his/her bet");
		try{
		P2.increase_chips(bet2);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet2 = -bet2;};}
	else
	{
		System.out.println("Player 2 get lower point than Dealer! Lose his/her bet!");
	try{
	P2.increase_chips(0);}
	catch (Player.NegativeException ne)
	{System.out.println("You bet negative number and we change it to positive");
	bet2 = -bet2;};}

if(!finish3)
	if(ttvalue3 > ttvalueD)
		{
		System.out.println("Player 3 get higher point than Dealer! Win his/her bet!");
		try{
		P3.increase_chips(2*bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;};}
	else if(ttvalue3 == ttvalueD)
		{
		System.out.println("Player 3 get equal points as Dealer! get back his/her bet");
		try{
		P3.increase_chips(bet3);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;};}
	else
		{
		System.out.println("Player 3 get lower point than Dealer! Lose his/her bet!");
		try{
		P3.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet3 = -bet3;};}

if(!finish4)
	if(ttvalue4 > ttvalueD)
		{
		System.out.println("Player 4 get higher point than Dealer! Win his/her bet!");
		try{
		P4.increase_chips(2*bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;};}
	else if(ttvalue4 == ttvalueD)
		{
		System.out.println("Player 4 get equal points as Dealer! get back his/her bet");
		try{
		P4.increase_chips(bet4);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;};}
	else
		{
		System.out.println("Player 4 get lower point than Dealer! Lose his/her bet!");
		try{
		P4.increase_chips(0);}
		catch (Player.NegativeException ne)
		{System.out.println("You bet negative number and we change it to positive");
		bet4 = -bet4;};}

//Show every player's chips
System.out.println("This round has finished");
System.out.println("Now, Player1's chips are " + P1.toString());
System.out.println("Player2's chips are " + P2.toString());
System.out.println("Player3's chips are " + P3.toString());
System.out.println("Player4's chips are " + P4.toString());
}

//RECORD LAST ROUNDS CARDS
public void organize_lasttable()
{
	last_table = current_table;
	current_table = empty_table;
}

public void show_currenttable(){
System.out.println("The current stage is  ");

for(int i = 0 ; i<current_table.size() ; i++)
	for(int j = 0 ; j<((current_table.get(i)).getCards()).size() ; j++)
		{
		System.out.print(" " + showcard(((current_table.get(i)).getCards()).get(j)) + " ");
		}
System.out.println("");
}

public void show_lasttable(){
System.out.println("The all used cards (including face down cards) in last round is  ");

for(int i = 0 ; i<last_table.size() ; i++)
	for(int j = 0 ; j<((last_table.get(i)).getCards()).size() ; j++)
		{
		System.out.print(" " + showcard(((last_table.get(i)).getCards()).get(j)) + " ");
		}
	System.out.println("");
}


public ArrayList<Hand> get_last_table(){
	return last_table;
}

public void clear_lasttable(){
	last_table.clear();
}

}