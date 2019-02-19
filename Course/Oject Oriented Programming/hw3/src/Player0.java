import java.util.*;
import java.lang.Iterable;
class Player0 extends Player{
//___class method:__
//Output P0's hand-card
//Override output method
void output(Card cd){
System.out.printf("Player0: ");
for(int i=0;i<numberofhc();i++)
	System.out.print((cd.getcard((int)(getcard(i)))+" "));
System.out.println();
}
}