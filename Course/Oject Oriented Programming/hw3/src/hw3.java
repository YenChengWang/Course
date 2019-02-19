import java.util.*;
import java.lang.Iterable;

//Start the game
public class hw3{

public static void main(String[] args){
//Basic class
Player0 p0 = new Player0();
Player1 p1 = new Player1();
Player2 p2 = new Player2();
Player3 p3 = new Player3();
Card cd = new Card();
Computer pc = new Computer();
Shuffle shuf = new Shuffle();
Drawcards draw = new Drawcards();
//Added class for Game2
Computer2 pc2 = new Computer2();
Shuffle2 shuf2 = new Shuffle2();
Drawcards2 draw2 = new Drawcards2();

//added class for Game3
Computer3 pc3 = new Computer3();
Shuffle3 shuf3 = new Shuffle3();
Drawcards3 draw3 = new Drawcards3();

//Program start
Scanner type = new Scanner(System.in);
System.out.println("Welcome to HW3!");
int x=5;

//Use a loop to let player choose which game to watch
while(x!=0){
	System.out.println("Please type int 1,2,3 to show Game1,Game2,Game3. Otherwise, type 0 to exit.");
	x=type.nextInt();//let the player to type the number of games
	System.out.println("Ok!");
	switch(x)
		{
		case 0:
		System.out.println("Goodbye! The program will stop!");
			break;

		case 1:
			{
			//Game 1
			System.out.println("Hello,welcome to Game1!");
			shuf.distribute(p0,p1,p2,p3);
			//Deal Cards output
			System.out.println("Deal Cards:");
			p0.output(cd);
			p1.output(cd);
			p2.output(cd);
			p3.output(cd);
			//Drop Cards process
			System.out.println("Drop Cards:");
			p0.dropcard();
			p1.dropcard();
			p2.dropcard();
			p3.dropcard();
			//Output the result after dropping card
			p0.output(cd);
			p1.output(cd);
			p2.output(cd);
			p3.output(cd);

			System.out.println("Game Start");
			pc.checkwinner(p0,p1,p2,p3); //Check whether Player0 or Player1 win before drawing a card

			draw.drawingprocess(p0,p1,p2,p3,pc,cd);
			System.out.println("Game"+x+" is over!");
			
			//clear all handcards of player and reset all var.
			p0.removeall();
			p1.removeall();
			p2.removeall();
			p3.removeall();
			shuf.resett();
			draw.resetvar();
			break;
			}

		//Game 2
		case 2:
		{
			System.out.println("Hello,welcome to Game2!");
			shuf2.distribute(p0,p1,p2,p3);
			//Deal Cards output
			System.out.println("Deal Cards:");
			p0.output(cd);
			p1.output(cd);
			p2.output(cd);
			p3.output(cd);
			//Drop Cards process
			System.out.println("Drop Cards:");
			p0.dropcard();
			p1.dropcard();
			p2.dropcard();
			p3.dropcard();
			//Output the result after dropping card
			p0.output(cd);
			p1.output(cd);
			p2.output(cd);
			p3.output(cd);

			System.out.println("Game Start");
			pc2.checkwinner(p0,p1,p2,p3,shuf2,cd); //Check whether Player0 or Player1 win before drawing a card

			draw2.drawingprocess(p0,p1,p2,p3,pc2,shuf2,cd);
			System.out.println("Game"+x+" is over!");
			//clear all handcards of player and reset all var.
			p0.removeall();
			p1.removeall();
			p2.removeall();
			p3.removeall();
			shuf2.resett();
			draw2.resetvar();
			break;
		}


		//Game 3
		case 3:
		{
			System.out.println("Hello,welcome to Game3!");
			shuf3.distribute(p0,p1,p2,p3);
			//Deal Cards output
			System.out.println("Deal Cards:");
			p0.output(cd);
			p1.output(cd);
			p2.output(cd);
			p3.output(cd);
			//Drop Cards process
			System.out.println("Drop Cards:");
			p0.dropcard3();
			p1.dropcard3();
			p2.dropcard3();
			p3.dropcard3();
			//Output the result after dropping card
			p0.output(cd);
			p1.output(cd);
			p2.output(cd);
			p3.output(cd);

			System.out.println("Game Start");
			pc3.checkwinner(p0,p1,p2,p3,shuf3,cd); //Check whether Player0 or Player1 win before drawing a card
			draw3.drawingprocess(p0,p1,p2,p3,pc3,shuf3,cd);
			System.out.println("Game"+x+" is over!");
			//clear all handcards of player and reset all var.
			p0.removeall();
			p1.removeall();
			p2.removeall();
			p3.removeall();
			shuf3.resett();
			draw3.resetvar();
			break;
		}
		}
}
}
}
