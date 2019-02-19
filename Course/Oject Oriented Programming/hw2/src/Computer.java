import java.util.*;
import java.lang.Iterable;

class Computer{
//This calss contains action of computer and contains game data

//__Class Data Member:__
private ArrayList<Integer> recordnum = new ArrayList();
private ArrayList<String> recordsuit = new ArrayList();
private int[] numappeartimes = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
private int numdouble = 0;
private int numtriple = 0;
private int numfourtimes = 0;
private int judge = 0;

//__Class method:__
//distribute 5 cards to player and out put
void distribute(Player pl,Shuffler shuf,Card crd){
for(int i=0;i<5;i++)
	pl.addhandcard(shuf.getcardinnum(i,crd));
	pl.sorthandcard();
}
//Drop card that players do not want to keep
void dropcard(Player pl,Shuffler shuf,Card crd){
System.out.print("Ok, I will drop ");

if (pl.getkeep().contains("abcde"))
	System.out.print("no cards");
if (!pl.getkeep().contains("e"))
	{System.out.print(shuf.getcardinstring(4,crd,pl)+" ");
	pl.removehandcard(4);}
if (!pl.getkeep().contains("d"))
	{System.out.print(shuf.getcardinstring(3,crd,pl)+" ");
	pl.removehandcard(3);}
if (!pl.getkeep().contains("c"))
	{System.out.print(shuf.getcardinstring(2,crd,pl)+" ");
	pl.removehandcard(2);}
if (!pl.getkeep().contains("b"))
	{System.out.print(shuf.getcardinstring(1,crd,pl)+" ");
	pl.removehandcard(1);}
if (!pl.getkeep().contains("a"))
	{System.out.print(shuf.getcardinstring(0,crd,pl)+" ");
	pl.removehandcard(0);}
if (pl.getkeep().contains("nothing")) //Use exception handling to avoid some error message
	{try{
	{System.out.print(pl.gethandcard(0)+" "+pl.gethandcard(1)+" "+pl.gethandcard(2)+" "+pl.gethandcard(3)+" "+pl.gethandcard(4));
	pl.handcardclear();
	}}
	catch(IndexOutOfBoundsException e1){
	System.out.println("");
	System.out.print("You drop all cards!");
	}
	}

System.out.println("");
}

//Complement new card for player to have 5 cards
void disrtibutenew(Player pl,Shuffler shuf,Card crd){
switch(pl.getkeepnum())
	{
	case 1://The player only keep 1 card
		for(int i=5;i<9;i++)
			pl.addhandcard(shuf.getcardinnum(i,crd));
		break;
	case 2://The player keep 2 cards
		for(int i=5;i<8;i++)
			pl.addhandcard(shuf.getcardinnum(i,crd));
		break;
	case 3://The player keep 3 cards
		for(int i=5;i<7;i++)
			pl.addhandcard(shuf.getcardinnum(i,crd));
		break;
	case 4://The player keep 4 cards
		for(int i=5;i<6;i++)
			pl.addhandcard(shuf.getcardinnum(i,crd));
		break;
	case 5://The player keep all cards, don't have to give him/her new cards
		break;
	case 7://the player type "nothing" to drop all cards, so give him/her 5 new cards.
		for(int i=5;i<10;i++)
			pl.addhandcard(shuf.getcardinnum(i,crd));
		break;
	}
pl.sorthandcard(); //Sort the number of handcard for easier recognition
}

//transform handcard for easier reconization
void transformhandcard(Player pl){
for(int j=0;j<5;j++){
	{if(pl.gethandcard(j)%13==0)
	recordnum.add(13);
	else
	recordnum.add((pl.gethandcard(j))%13);}  //record the number of handcard
	switch(((pl.gethandcard(j))-1)/13)  //record the suit of handcard
	{
		case 0:
			recordsuit.add("C");
			break;
		case 1:
			recordsuit.add("D");
			break;
		case 2:
			recordsuit.add("H");
			break;
		case 3:
			recordsuit.add("S");
			break;
	}
}
recordnumberappear();  //record the appear times for each number (From A to K)
}

//Use an array to record the appearing times of A~K
void recordnumberappear(){
Collections.sort(recordnum);

for(int i=0;i<5;i++){
	switch(recordnum.get(i)){
		case 1:
			numappeartimes[1]++;
			break;
		case 2:
			numappeartimes[2]++;
			break;
		case 3:
			numappeartimes[3]++;
			break;
		case 4:
			numappeartimes[4]++;
			break;
		case 5:
			numappeartimes[5]++;
			break;
		case 6:
			numappeartimes[6]++;
			break;
		case 7:
			numappeartimes[7]++;
			break;
		case 8:
			numappeartimes[8]++;
			break;
		case 9:
			numappeartimes[9]++;
			break;
		case 10:
			numappeartimes[10]++;
			break;
		case 11:
			numappeartimes[11]++;
			break;
		case 12:
			numappeartimes[12]++;
			break;
		case 13:
			numappeartimes[13]++;
			break;}
		}

//Record the repeated card number for recognition of four/three of a kind,fullhouse,two pairs and jackorbetter
for(int i=1;i<14;i++)
	{
	switch(numappeartimes[i])
	{
	case 2:
		numdouble++;
		break;
	case 3:
		numtriple++;
		break;
	case 4:
		numfourtimes++;
		break;
	default:
	System.out.print("");
	}
	}

}

//This method is used for debugging of programm
void printoutc(){
System.out.println();
System.out.println();
for(int i=1;i<14;i++)
	System.out.println(numappeartimes[i]+" ");
System.out.println(numdouble+" "+numtriple+" "+numfourtimes+" ");
System.out.println(recordnum.get(0)+" "+recordnum.get(1)+" "+recordnum.get(2)+" "+recordnum.get(3)+" "+recordnum.get(4));
System.out.println(recordsuit.get(0)+" "+recordsuit.get(1)+" "+recordsuit.get(2)+" "+recordsuit.get(3)+" "+recordsuit.get(4));
System.out.println();
System.out.println();
}

//Judge the type of the handcard
void recognizecard(){
	
	if (royalflush())
		{judge = 10;
		System.out.println("You get a royalflush! ");}
	else if(straightflush())
		{judge = 9;
		System.out.println("You get a straightflush! ");}
	else if(fourofkind())
		{judge = 8;
		System.out.println("You get a four of a kind! ");}
	else if(fullhouse())
		{judge = 7;
		System.out.println("You get a fullhouse! ");}
	else if(flush())
		{judge = 6;
		System.out.println("You get a flush! ");}
	else if(straight())
		{judge = 5;
		System.out.println("You get a straight! ");}
	else if(threeofkind())
		{judge = 4;
		System.out.println("You get a three of a kind! ");}
	else if(twopair())
		{judge = 3;
		System.out.println("You get a two pair! ");}
	else if(jackorbetter())
		{judge = 2;
		System.out.println("You get a Jack or better! ");}
	else
		{judge = 1;
		System.out.println("Sorry, You get nothing! ");}
	}


//methods for recognizing cards---
boolean royalflush(){
	boolean trueorfalse = false;
	if(recordnum.get(0)==1)
		if(recordnum.get(1)==10)
			if(recordnum.get(2)==11)
				if(recordnum.get(3)==12)
					if(recordnum.get(4)==13)
						if(recordsuit.get(0).equals(recordsuit.get(1)))
							if(recordsuit.get(1).equals(recordsuit.get(2)))
								if(recordsuit.get(2).equals(recordsuit.get(3)))
									if(recordsuit.get(3).equals(recordsuit.get(4)))
										trueorfalse = true;
	return trueorfalse;
}
boolean straightflush(){
	boolean trueorfalse = false;
	if(recordnum.get(0)==recordnum.get(1)-1)
		if(recordnum.get(1)-1==recordnum.get(2)-2)
			if(recordnum.get(2)-2==recordnum.get(3)-3)
				if(recordnum.get(3)-3==recordnum.get(4)-4)
					if(recordsuit.get(0).equals(recordsuit.get(1)))
						if(recordsuit.get(1).equals(recordsuit.get(2)))
							if(recordsuit.get(2).equals(recordsuit.get(3)))
								if(recordsuit.get(3).equals(recordsuit.get(4)))
									trueorfalse = true;
	return trueorfalse; 
}
boolean fourofkind(){
	boolean trueorfalse = false;
	if(numfourtimes==1)
		trueorfalse = true;
	return trueorfalse;
}
boolean fullhouse(){
	boolean trueorfalse = false;
	if(numdouble==1 && numtriple==1)
		trueorfalse = true;
	return trueorfalse;
}
boolean flush(){
	boolean trueorfalse = false;
	if(recordsuit.get(0).equals(recordsuit.get(1)))
		if(recordsuit.get(1).equals(recordsuit.get(2)))
			if(recordsuit.get(2).equals(recordsuit.get(3)))
				if(recordsuit.get(3).equals(recordsuit.get(4)))
					trueorfalse = true;
	return trueorfalse;
}
boolean straight(){
	boolean trueorfalse = false;
	if(recordnum.get(0)==recordnum.get(1)-1)
		{if(recordnum.get(1)-1==recordnum.get(2)-2)
			if(recordnum.get(2)-2==recordnum.get(3)-3)
				if(recordnum.get(3)-3==recordnum.get(4)-4)
					trueorfalse = true;}
	else if(recordnum.get(0)==10)
		{if(recordnum.get(1)==11)
			if(recordnum.get(2)==12)
				if(recordnum.get(2)==13)
					trueorfalse = true;}
	return trueorfalse;
		
}
boolean threeofkind(){
	boolean trueorfalse = false;
	if(numtriple==1)
		trueorfalse = true;
	return trueorfalse;
}
boolean twopair(){
	boolean trueorfalse = false;
	if(numdouble==2)
		trueorfalse = true;
	return trueorfalse;
}
boolean jackorbetter(){
	boolean trueorfalse = false;
	if(numdouble==1)
		if(numappeartimes[11]==2)
			trueorfalse = true;
		else if(numappeartimes[12]==2)
			trueorfalse = true;
		else if(numappeartimes[13]==2)
			trueorfalse = true;
		else if(numappeartimes[1]==2)
			trueorfalse = true;
	return trueorfalse;
}
//---methods for recognizing cards

//getter for judge(record the outcome of recognition)
int getjudge(){
	return judge;}

//Clear all game data after finishing one round
void cleargamedata(){
for (int i=1;i<14;i++)
	numappeartimes[i] = 0;
numdouble=0;
numtriple=0;
numfourtimes=0;
recordnum.clear();
recordsuit.clear();
}

}