import java.util.*;
import java.lang.Iterable;

class Shuffler{
//This class is used to open a new deck of cards and get it shuffled
//__Class Data member:__
private  ArrayList<Integer> shuffledcard = new ArrayList<Integer>();//record the card base after shuffling
private int x;//the variable for helping shuffle card

//__Class Method:__
//method to shuffle card
void shufflecard(){
	for(int i=1;i<53;i++){
		x=(int)((Math.random())*52+1);
		if(check(x))
			shuffledcard.add(x);
		else
			i--;}
	}
	
//Make sure that every card can be only distributed one time
boolean check(int x){
boolean norepeat = true;

for(int j=0;j<shuffledcard.size();j++)
	{if(shuffledcard.contains(x))
		norepeat = false;}
	//else
		//norepeat = true;

	return norepeat;
}

//Change number to Poker's sign
String getcardinstring(int b,Card crd,Player pl){
	int cardnum = pl.gethandcard(b);
	return crd.getcardbase(cardnum);
}
//clear the deck after each round of the game
void cleardeck(){
	shuffledcard.clear();
}
//getter for shuffledcard
int getcardinnum(int a,Card crd){
	int cardnumber = shuffledcard.get(a);
		return cardnumber;
}
}