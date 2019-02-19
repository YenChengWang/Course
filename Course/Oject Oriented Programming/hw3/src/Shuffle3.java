import java.util.*;
import java.lang.Iterable;
class Shuffle3 extends Shuffle{
//___Class Member:__
//decide the adding card from 52 cards
int add=54;
//int added = 0; //if the spade ace has been added, this number will become 1
//___Class Method__
//Distribute Card to 4 players
//Override distribute method
void distribute(Player0 p0,Player1 p1,Player2 p2,Player3 p3){
int j=3; //the number of card
while(j<55)
{
	int x=(int)((Math.random())*4);
	switch(x)
	{
	//Use "if" to make sure the # of hand-card will be 14,14,13,13 for Player 0 to 3
	case 0:
		if(t0<13)
			{
			p0.setcard(j);
			 t0++;
			 j++;}
		break;
	case 1:
		if(t1<13)
			{
			p1.setcard(j);
			 t1++;
			 j++;}
		break;
	case 2:
		if(t2<13)
			{
			p2.setcard(j);
			 t2++;
			 j++;}
		break;
	case 3:
		if(t3<13)
			{
			p3.setcard(j);
			 t3++;
			 j++;}
		break;
	default: //If someone who have already got his maximum # of card get another new card this turn, this turn will be skipped
		continue;
	}
	
	/*if(j==54 && added==0) //Add one more SA to the one of the players
	{
	j--;
	added=1;
	}**/

}
//Add more SA to the player who have not have enough handcard
int x=(int)((Math.random())*4);

if(x==0)
	p0.setcard(54);
else if(x==1)
	p1.setcard(54);
else if(x==2)
	p2.setcard(54);
else if(x==3)
	p3.setcard(54);
}
//show the joker card
String getjoker(Card cd){
	return cd.getcard(add);
}
}