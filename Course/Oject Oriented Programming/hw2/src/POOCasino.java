import java.util.*;
import java.lang.Iterable;


class POOCasino{
//This class is used to interact with players
//__Class Data Member:__
private int Pdollar = 1000;//Initialize Pdollar to 1000
private int numberOfRounds = 0;// Record how many rounds that player has played
private String nameOfPlayer;//record the name of players
private int betPDollars = 0;//recod the Pdollars that players want to bet

//__Class methods:__
//__Construtor__
public POOCasino(String name)
{
nameOfPlayer = name;
greetings();
}

//Greetings for the player
void greetings(){
System.out.println("Welcome, " + nameOfPlayer);
System.out.println("You have "+ Pdollar + "P-dollars now.");
}

//Start a new round of Game and let players bet Pdollars
void startnewgame(Scanner type)
{
System.out.println("Please enter your P-dollar bet for round" + (numberOfRounds+1) + "(1-5 or 0 for quitting the game)");
betPDollars = type.nextInt();

//Make betPDollars reasonable
if(betPDollars < 0)
	{betPDollars=1;
	System.out.println("You bet less than 0 PDollar,and we assign your stake to 1 PDollar.");}
else if(betPDollars > 5)
	{betPDollars=5;
	System.out.println("You bet more than 5 PDollars,and we assign your stake to 5 PDollars.");}
}

//Show the handcard to players
void showcard(Shuffler shuf,Card crd,Player pl){
System.out.println("Your card are(a) " + shuf.getcardinstring(0,crd,pl)+" (b) "+ shuf.getcardinstring(1,crd,pl)+" (c) "+shuf.getcardinstring(2,crd,pl)+" (d) "+shuf.getcardinstring(3,crd,pl)+" (e) "+shuf.getcardinstring(4,crd,pl));
}
//Make pdollar no less than 100
void pdollarmin100(){
	if(Pdollar<100)
		Pdollar=100;
}

//show the handcard to players after complementing
void shownewcard(Shuffler shuf,Card crd,Player pl){
System.out.println("Your new cards are "+shuf.getcardinstring(0,crd,pl)+" "+shuf.getcardinstring(1,crd,pl)+" "+shuf.getcardinstring(2,crd,pl)+" "+shuf.getcardinstring(3,crd,pl)+" "+shuf.getcardinstring(4,crd,pl));
}

//Calculate the Pdollars after each round of game
void calculate(Computer pc){
switch(pc.getjudge())
{
	case 10://royal flush
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+4000;
				System.out.println("The payoff is 4000");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+1000;
				System.out.println("The payoff is 1000");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+750;
				System.out.println("The payoff is 750");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+500;
				System.out.println("The payoff is 500");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+250;
				System.out.println("The payoff is 250");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 9://straightflush
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+250;
				System.out.println("The payoff is 250");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+200;
				System.out.println("The payoff is 200");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+150;
				System.out.println("The payoff is 750");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+100;
				System.out.println("The payoff is 500");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+50;
				System.out.println("The payoff is 250");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 8://four of a kind
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+125;
				System.out.println("The payoff is 125");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+100;
				System.out.println("The payoff is 100");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+75;
				System.out.println("The payoff is 75");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+50;
				System.out.println("The payoff is 50");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+25;
				System.out.println("The payoff is 25");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 7://full house
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+45;
				System.out.println("The payoff is 45");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+36;
				System.out.println("The payoff is 36");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+27;
				System.out.println("The payoff is 27");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+18;
				System.out.println("The payoff is 18");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+9;
				System.out.println("The payoff is 9");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 6://flush
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+30;
				System.out.println("The payoff is 30");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+24;
				System.out.println("The payoff is 24");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+18;
				System.out.println("The payoff is 18");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+12;
				System.out.println("The payoff is 12");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+6;
				System.out.println("The payoff is 6");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 5://straight
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+20;
				System.out.println("The payoff is 20");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+16;
				System.out.println("The payoff is 16");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+12;
				System.out.println("The payoff is 12");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+8;
				System.out.println("The payoff is 8");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+4;
				System.out.println("The payoff is 4");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
		break;}
		break;
	case 4://three of a kind
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+15;
				System.out.println("The payoff is 15");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+12;
				System.out.println("The payoff is 12");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+9;
				System.out.println("The payoff is 9");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+6;
				System.out.println("The payoff is 6");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+3;
				System.out.println("The payoff is 3");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 3://two pair
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+10;
				System.out.println("The payoff is 10");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+8;
				System.out.println("The payoff is 8");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+6;
				System.out.println("The payoff is 6");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+4;
				System.out.println("The payoff is 4");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+2;
				System.out.println("The payoff is 2");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 2://Jack or better
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars+5;
				System.out.println("The payoff is 5");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars+4;
				System.out.println("The payoff is 4");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars+3;
				System.out.println("The payoff is 3");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars+2;
				System.out.println("The payoff is 2");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars+1;
				System.out.println("The payoff is 1");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
	case 1://others
		switch(betPDollars){
			case 5:
				Pdollar = Pdollar-betPDollars;
				System.out.println("The payoff is 0");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 4:
				Pdollar = Pdollar-betPDollars;
				System.out.println("The payoff is 0");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 3:
				Pdollar = Pdollar-betPDollars;
				System.out.println("The payoff is 0");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 2:
				Pdollar = Pdollar-betPDollars;
				System.out.println("The payoff is 0");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;
			case 1:
				Pdollar = Pdollar-betPDollars;
				System.out.println("The payoff is 0");
				System.out.println("You have "+ Pdollar +" P-dollars now.");
				break;}
		break;
}
}

//Record number of rounds that players play for
void finishoneround(){
	numberOfRounds++;
}

//__method of Getter:__
//get the P dollars that the player bet
int getbetPDollars(){
	return betPDollars;
}
//get the numberOfRounds
int getnumberOfRounds(){
	return numberOfRounds;
}
//get the name of Player
String getnameOfPlayer(){
	return nameOfPlayer;
}
//get value Pdollar
int getPdollar(){
	return Pdollar;
}
}