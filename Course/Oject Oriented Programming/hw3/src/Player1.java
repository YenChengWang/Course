import java.util.*;
import java.lang.Iterable;
class Player1 extends Player{
//___class method:__
//Output P1's hand-card
//Override output method
void output(Card cd){
System.out.printf("Player1: ");
for(int i=0;i<numberofhc();i++)
	System.out.print((cd.getcard((int)(getcard(i)))+" "));
System.out.println();
}
}