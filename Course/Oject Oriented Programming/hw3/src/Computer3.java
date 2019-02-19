import java.util.*;
import java.lang.Iterable;
class Computer3 {
//__Class method:___
//Check if anyone wins the game
int checkwinner(Player0 p0,Player1 p1,Player2 p2,Player3 p3,Shuffle3 shuf3,Card cd)
{
int win0=0;
int win1=0;
int win2=0;
int win3=0;
if(p0.numberofhc()==0)
	win0=1;
if(p1.numberofhc()==0)
	win1=1;
if(p2.numberofhc()==0)
	win2=1;
if(p3.numberofhc()==0)
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
	if(p0.numberofhc()==0)
		{
		System.out.println("Player0 wins");
		System.out.println("Basic game over");
		}
	else if(p1.numberofhc()==0)
		{
		System.out.println("Player1 wins");
		System.out.println("Basic game over");
		}
	else if(p2.numberofhc()==0)
		{
		System.out.println("Player2  wins");
		System.out.println("Basic game over");
		}
	else if(p3.numberofhc()==0)
		{
		System.out.println("Player3  wins");
		System.out.println("Basic game over");
		}
	}

int y=0;
if(winner!=0)
	y=1;
else if(winner==0)
	y=0;

return y;
}
}