import java.util.*;
import java.lang.Iterable;
class Shuffle{
//___Class Member__
//Variables t0~t03 to record the # of card that each player have
int t0=0;
int t1=0;
int t2=0;
int t3=0;

//___Class Method__
//Distribute Card to 4 players
void distribute(Player0 p0,Player1 p1,Player2 p2,Player3 p3){
int j=1; //the number of card
while(j<55)
{
	int x=(int)((Math.random())*4);
	switch(x)
	{
	//Use "if" to make sure the # of hand-card will be 14,14,13,13 for Player 0 to 3
	case 0:
		if(t0<14)
			{
			p0.setcard(j);
			 t0++;
			 j++;}
		break;
	case 1:
		if(t1<14)
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
}
}
//reset t
void resett(){
t0=0;
t1=0;
t2=0;
t3=0;

}

}