import java.util.*;
import java.lang.Iterable;

public class PlayGame
{
//Use ArrayList to record the handcards for each player
ArrayList<Integer> P0 = new ArrayList<Integer>();
ArrayList<Integer>P1 = new ArrayList<Integer>();
ArrayList<Integer> P2 = new ArrayList<Integer>();
ArrayList<Integer> P3 = new ArrayList<Integer>();

//Database for All cards
String[] card = {"","R0","B0","C2","D2","H2","S2","C3","D3","H3","S3","C4","D4","H4","S4","C5","D5","H5","S5",
				"C6","D6","H6","S6","C7","D7","H7","S7","C8","D8","H8","S8","C9","D9","H9","S9","C10","D10","H10",
				"S10","CJ","DJ","HJ","SJ","CQ","DQ","HQ","SQ","CK","DK","HK","SK","CA","DA","HA","SA"};

//Start the game
public static void main(String[] args){
PlayGame dc = new PlayGame();
//Variables t0~t03 to record the # of card that each player have
int t0=0;
int t1=0;
int t2=0;
int t3=0;

int i=1; //The ith card to be given
int x=0; //Decide which player receive the card

//Deal Card to 4 players
while(i<55)
{
	x=(int)((Math.random())*4);
	switch(x)
	{
	//Use "if" to make sure the # of hand-card will be 14,14,13,13 for Player 0 to 3
	case 0:
		if(t0<14)
			{
			dc.P0.add(i);
			 t0++;
			 i++;}
		break;
	case 1:
		if(t1<14)
			{dc.P1.add(i);
			 t1++;
			 i++;}
		break;
	case 2:
		if(t2<13)
			{dc.P2.add(i);
			 t2++;
			 i++;}
		break;
	case 3:
		if(t3<13)
			{dc.P3.add(i);
			 t3++;
			 i++;}
		break;
	default: //If someone who have already got his maximum # of card get another new card this turn, this turn will be skipped
		continue;
	}
}

//Deal Cards output
System.out.println("Deal Cards:");
dc.outputP0();
dc.outputP1();
dc.outputP2();
dc.outputP3();

//Drop Cards process
System.out.println("Drop Cards:");
dc.dropcard0();
dc.dropcard1();
dc.dropcard2();
dc.dropcard3();

//Output the result after dropping card
dc.outputP0();
dc.outputP1();
dc.outputP2();
dc.outputP3();

System.out.println("Game Start");
dc.checkwinner(); //Check whether Player0 or Player1 win before drawing a card

//Process of drawing cards (the process of the game)

while(dc.P0.size()!=0||dc.P1.size()!=0||dc.P2.size()!=0||dc.P3.size()!=0) //Continue next round if no one wins
{
//Player0 draw a card from Player1
System.out.println("Player0 draws a card from Player1.");
int D0 = (int)(Math.random()*(dc.P1.size())); //Decide which card will be drawn
dc.P0.add(dc.P1.get(D0)); //Add the card into P0's hand-card
dc.P1.remove(D0); //Remove the card from P1's hand-card
Collections.sort(dc.P0); //Sort the hand-card of P0
dc.dropcard0(); //Drop cards from P1's hand-card if a pair form
dc.outputP0();
dc.outputP1();
dc.checkwinner(); //Check if anyone wins after this process

//Player1 draw a card from Player2
System.out.println("Player1 draws a card from Player2.");
int D1 = (int)(Math.random()*(dc.P2.size())); 
dc.P1.add(dc.P2.get(D1)); 
dc.P2.remove(D1);
Collections.sort(dc.P1);
dc.dropcard1();
dc.outputP1();
dc.outputP2();
dc.checkwinner();

//Player2 draw a card from Player3
System.out.println("Player2 draws a card from Player3.");
int D2 = (int)(Math.random()*(dc.P3.size())); 
dc.P2.add(dc.P3.get(D2));
dc.P3.remove(D2);
Collections.sort(dc.P2);
dc.dropcard2();
dc.outputP2();
dc.outputP3();
dc.checkwinner();

//Player3 draw a card from Player0
System.out.println("Player3 draws a card from Player0.");
int D3 = (int)(Math.random()*(dc.P0.size()));
dc.P3.add(dc.P0.get(D3));
dc.P0.remove(D3);
Collections.sort(dc.P3);
dc.dropcard3();
dc.outputP3();
dc.outputP0();
dc.checkwinner();

// the program finished
}
}

//Output P0's hand-card
void outputP0()
{
System.out.printf("Player0: ");
for(int i=0;i<P0.size();i++)
	System.out.print((card[(int)(P0.get(i))]+" "));
System.out.println();
}
//Output P1's hand-card
void outputP1()
{
System.out.printf("Player1: ");
for(int i=0;i<P1.size();i++)
	System.out.print((card[(int)(P1.get(i))]+" "));
System.out.println();
}
//Output P2's hand-card
void outputP2()
{
System.out.printf("Player2: ");
for(int i=0;i<P2.size();i++)
	System.out.print((card[(int)(P2.get(i))]+" "));
System.out.println();
}
//Output P3's hand-card
void outputP3()
{
System.out.printf("Player3: ");
for(int i=0;i<P3.size();i++)
	System.out.print((card[(int)(P3.get(i))]+" "));
System.out.println();
}

//P0 drop card
void dropcard0()
{
for (int i=0;i<P0.size()-1;i++)
	{
	if(P0.get(i)==1||P0.get(i)==2)
		continue;
	else if((((P0.get(i))-3)/4)==(((P0.get(i+1))-3)/4))
		{P0.remove(i);
		P0.remove(i);
		i--;}	
	}
}
//P1 drop card
void dropcard1()
{
for (int i=0;i<P1.size()-1;i++)
	{
	if(P1.get(i)==1||P1.get(i)==2)
		continue;
	else if((((P1.get(i))-3)/4)==(((P1.get(i+1))-3)/4))
		{P1.remove(i);
		P1.remove(i);
		i--;}	
	}
}
//P2 drop card
void dropcard2()
{
for (int i=0;i<P2.size()-1;i++)
	{
	if(P2.get(i)==1||P2.get(i)==2)
		continue;
	else if((((P2.get(i))-3)/4)==(((P2.get(i+1))-3)/4))
		{P2.remove(i);
		P2.remove(i);
		i--;}	
	}
}
//P3 drop card
void dropcard3()
{
for (int i=0;i<P3.size()-1;i++)
	{
	if(P3.get(i)==1||P3.get(i)==2)
		continue;
	else if((((P3.get(i))-3)/4)==(((P3.get(i+1))-3)/4))
		{P3.remove(i);
		P3.remove(i);
		i--;}	
	}
}
//Check if anyone wins the game
void checkwinner()
{
int win0=0;
int win1=0;
int win2=0;
int win3=0;
if(P0.size()==0)
	win0=1;
if(P1.size()==0)
	win1=1;
if(P2.size()==0)
	win2=1;
if(P3.size()==0)
	win3=1;
int winner=win0+win1+win2+win3;

if(winner==2) //If there are 2 winners
	{
	if(win0==1 && win1==1)
		System.out.println("Player0 and Player1 win");
	if(win0==1 && win2==1)
		System.out.println("Player0 and Player2 win");
	if(win0==1 && win3==1)
		System.out.println("Player0 and Player3 win");
	if(win1==1 && win2==1)
		System.out.println("Player1 and Player2 win");
	if(win1==1 && win3==1)
		System.out.println("Player1 and Player3 win");
	if(win2==1 && win3==1)
		System.out.println("Player2 and Player3 win");
System.out.println("Basic game over");
	}

else if(winner==1) //If only 1 winner
	{
	if(P0.size()==0)
		{
		System.out.println("Player0 wins");
		System.out.println("Basic game over");
		System.exit(0);}
	else if(P1.size()==0)
		{
		System.out.println("Player1 wins");
		System.out.println("Basic game over");
		System.exit(0);}
	else if(P2.size()==0)
		{
		System.out.println("Player2  wins");
		System.out.println("Basic game over");
		System.exit(0);}
	else if(P3.size()==0)
		{
		System.out.println("Player3  wins");
		System.out.println("Basic game over");
		System.exit(0);}
	}
}

}