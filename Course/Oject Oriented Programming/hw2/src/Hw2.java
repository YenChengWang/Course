import java.util.*;
import java.lang.Iterable;

public class Hw2{

public static void main(String[] args){
	
System.out.println("POOCasino Jacks or better, written by r03527017 Yen-Cheng Wang");//Brief intro. of this program
System.out.print("Please enter your name: ");//Ask the player to enter his/her name
//Generate object for each class
Scanner type = new Scanner(System.in);
String name = type.nextLine();//record the name of players
POOCasino pooc = new POOCasino(name);//create an object of POOCasino
Computer pc = new Computer();//create an object of Computer
Card crd = new Card(); //create an object of Computer
Shuffler shuf = new Shuffler();//create an object of shuffler
Player pl = new Player();//create an object of Player

//Loop used for process of game
while (true)
	{
	shuf.shufflecard();//Shuffle a new deck of card
	pooc.startnewgame(type);//Start a round of game
	pooc.pdollarmin100();//Make Pdollars no less than 100
	
	//Check whether the player want to close the game
	if(pooc.getbetPDollars()==0)
		{System.out.println("Good bye, "+ pooc.getnameOfPlayer() + " You played for " + pooc.getnumberOfRounds() + " round and have "+ pooc.getPdollar() +" P-dollars now.");
		break;}
	
	//distribute cards to players and show the cards
	pc.distribute(pl,shuf,crd);
	pooc.showcard(shuf,crd,pl);
	
	//Ask players to drop some cards
	pl.keepcard(pc,pl,type,shuf,crd);
	pc.dropcard(pl,shuf,crd);
	
	//Distribute more card to let players have 5 cards after dropping and show the cards
	pc.disrtibutenew(pl,shuf,crd);
	pooc.shownewcard(shuf,crd,pl);
	
	//Recognize the outcome for players
	pc.transformhandcard(pl);
	pc.recognizecard();
	//pc.printoutc();// This process use for debugging to assure the accuracy of recognitionprocess 
	
	//Calculate the Pdollars after the game
	pooc.calculate(pc);
	pooc.finishoneround();
	
	//Clear all data for new round of game
	pl.handcardclear();
	shuf.cleardeck();
	pc.cleargamedata();
	//pc.printoutc();// This process use for debugging to assure the accuracy of clear process
	}

}

}