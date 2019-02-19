import java.util.*;
import java.lang.Iterable;

class Player{
//This class contains actions of players and hand card data dealing

//__Class data member:__
private ArrayList<Integer> handcard = new ArrayList<Integer>();
private String keep = null;

//__Class method:__

//ask which cards the player would like to keep
void keepcard(Computer pc,Player pl,Scanner type,Shuffler shuf,Card crd){
System.out.println("Which cards do you want to keep?");
System.out.println("Key in \"nothing\" if you want to drop all cards");
keep = type.next();
}
//Clear handcards after finishing one round
void handcardclear(){
	handcard.clear();}

//__Method of getter:__
//get the # of that players want to keep,#==7 if players want to drop all cards
int getkeepnum(){
return keep.length();} 

//get the card that players want to keep 
String getkeep(){
return keep;}
//get the element of handcard
int gethandcard(int p){
return handcard.get(p);
}
// add handcard
void addhandcard(int p){
handcard.add(p);
}
//sort handcard
void sorthandcard(){
Collections.sort(handcard);
}

//remove handcard element
void removehandcard(int p){
handcard.remove(p);
}


}