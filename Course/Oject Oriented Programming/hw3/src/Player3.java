import java.util.*;
import java.lang.Iterable;
class Player3 extends Player{
//___class method:__
//Output P3's hand-card
//Override output method
void output(Card cd){
System.out.printf("Player3: ");
for(int i=0;i<numberofhc();i++)
	System.out.print((cd.getcard((int)(getcard(i)))+" "));
System.out.println();
}
}