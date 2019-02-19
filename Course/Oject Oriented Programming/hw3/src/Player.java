import java.util.*;
import java.lang.Iterable;
abstract class Player{
//__class member:__
private ArrayList<Integer> hc = new ArrayList<Integer>(); //Arraylist of handcards

//__class method:__
//output handcards(abstract method)
abstract void output(Card cd);
//Add cards to handcard Arryalist
void setcard(int i)
{
	hc.add(i);}
//Get the number of handcard
int numberofhc()
{
	return hc.size();}

//Drop card of the same number(process for Game1 and Game2)
void dropcard()
{
for (int i=0;i<hc.size()-1;i++)
	{
	if(hc.get(i)==1||hc.get(i)==2)
		continue;
	else if((((hc.get(i))-3)/4)==(((hc.get(i+1))-3)/4))
		{hc.remove(i);
		hc.remove(i);
		i--;}	
	}
}
//remove all handcard
void removeall(){
	hc.clear();
}



//process of droping card for Game3
void dropcard3()
{
for (int i=0;i<numberofhc()-1;i++)
	{
	int judgeA = (getcard(i)-3)/4;
	
	if(judgeA==12)
		{
		if(getcard(i)==54 && getcard(i+1)==54) //if the player has 2 SA, he/she can drop both
			{
			remove(i);
			remove(i);
			i--;
			}
		else if(getcard(i)==51 && getcard(i+1)==52) //if the player has CA and DA, he/she can drop both
			{
			remove(i);
			remove(i);
			i--;
			}
		else if(getcard(i)==51 && getcard(i+1)==53) //if the player has CA and HA, he/she can drop both
			{
			remove(i);
			remove(i);
			i--;
			}
		else if(getcard(i)==52 && getcard(i+1)==53) //if the player has DA and HA, he/she can drop both
			{
			remove(i);
			remove(i);
			i--;
			}
		}
	else
		{if((((getcard(i))-3)/4)==(((getcard(i+1))-3)/4))
			{remove(i);
			remove(i);
			i--;}}
	}
	}
//remove card
void remove(int i){
	hc.remove(i);
}
//sort handcard
void sorthc(){
Collections.sort(hc);
}
//get card
int getcard(int i){
	return hc.get(i);
}
}