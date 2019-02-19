import java.util.*;
import java.lang.Iterable;
import foop.*;

class CARD{
	byte low;
//Generate deck ArrayList
CARD(ArrayList<Card> deck){

low = Card.VALUE_LOWER;

for(byte i = 1;i<14;i++)
{
deck.add(new Card(Card.CLUB,low));
deck.add(new Card(Card.HEART,low));
deck.add(new Card(Card.DIAMOND,low));
deck.add(new Card(Card.SPADE,low));
low++;
}
Collections.shuffle(deck);
}
}