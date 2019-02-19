import java.util.*;
import java.lang.Iterable;
class Drawcards3 {
//__Data member:__
int x=0; //used as a function return value
int stop = 0; //if stop equals 1, the game will end.
//Process of drawing cards (the process of the game)
//__Class method:__
void drawingprocess(Player0 p0,Player1 p1,Player2 p2,Player3 p3,Computer3 pc3,Shuffle3 shuf3,Card cd){
//Continue next round if no one wins
while(stop!=1)
{
//Player0 draw a card from Player1
int D0 = (int)(Math.random()*(p1.numberofhc())); //Decide which card will be drawn
System.out.println("Player0 draws a card from Player1. "+cd.getcard(p1.getcard(D0)));
p0.setcard(p1.getcard(D0)); //Add the card into P0's hand-card
p1.remove(D0); //Remove the card from P1's hand-card
p0.sorthc(); //Sort the hand-card of P0
p0.dropcard3(); //Drop cards from P1's hand-card if a pair form
p0.output(cd);
p1.output(cd);
x = pc3.checkwinner(p0,p1,p2,p3,shuf3,cd);//Check if anyone wins after this process

if(x==1)
	{
	stop=1;
	continue;
	}

//Player1 draw a card from Player2
int D1 = (int)(Math.random()*(p2.numberofhc()));
System.out.println("Player1 draws a card from Player2. "+cd.getcard(p2.getcard(D1)));
p1.setcard(p2.getcard(D1));
p2.remove(D1); 
p1.sorthc(); 
p1.dropcard3(); 
p1.output(cd);
p2.output(cd);
x = pc3.checkwinner(p0,p1,p2,p3,shuf3,cd);//Check if anyone wins after this process

if(x==1)
	{
	stop=1;
	continue;
	}

//Player2 draw a card from Player3
int D2 = (int)(Math.random()*(p3.numberofhc()));
System.out.println("Player2 draws a card from Player3. "+cd.getcard(p3.getcard(D2)));
p2.setcard(p3.getcard(D2));
p3.remove(D2); 
p2.sorthc(); 
p2.dropcard3(); 
p2.output(cd);
p3.output(cd);
x = pc3.checkwinner(p0,p1,p2,p3,shuf3,cd);

if(x==1)
	{
	stop=1;
	continue;
	}

//Player3 draw a card from Player0
int D3 = (int)(Math.random()*(p0.numberofhc()));
System.out.println("Player3 draws a card from Player0. "+cd.getcard(p0.getcard(D3)));
p3.setcard(p0.getcard(D3));
p0.remove(D3); 
p3.sorthc(); 
p3.dropcard3(); 
p3.output(cd);
p0.output(cd);
x = pc3.checkwinner(p0,p1,p2,p3,shuf3,cd);

if(x==1)
	{
	stop=1;
	continue;
	}
}
}
//reset variable
void resetvar(){
x=0;
stop = 0;
}
}