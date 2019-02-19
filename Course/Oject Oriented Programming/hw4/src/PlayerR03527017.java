import java.util.*;
import java.lang.Iterable;
import foop.*;


class PlayerR03527017 extends Player {
	PlayerR03527017(int chips){
		super(chips);
	}
	
	@Override 
	public int make_bet(java.util.ArrayList<Hand> last_table, int total_player, int my_position){
	int BET = 0;
	if (last_table == null)
		{switch(my_position)
		{
		case 0:
			BET = 50;
			break;
		case 1:
			BET = 60;
			break;
		case 2:
			BET = 70;
			break;
		case 3:
			BET = 80;
			break;
		}}
	else if (last_table.size()<12)
		{switch(my_position)
		{
		case 0:
			BET = 80;
			break;
		case 1:
			BET = 120;
			break;
		case 2:
			BET = 90;
			break;
		case 3:
			BET = 100;
			break;
		}}
	else if (last_table.size() >= 12 && last_table.size()<25)
		{switch(my_position)
		{
		case 0:
			BET = 60;
			break;
		case 1:
			BET = 50;
			break;
		case 2:
			BET = 40;
			break;
		case 3:
			BET = 30;
			break;
		}}
	else
		BET = 100;
	return BET;
	}
	
	@Override
	public boolean buy_insurance(Card my_open,Card dealer_open,java.util.ArrayList<Hand> current_table){
	boolean INSURANCE = false;
	if(my_open.getValue() < 4)
		if(my_open.getValue() == 1)
			INSURANCE = false;
		else
			INSURANCE = true;
	else if(4 <= my_open.getValue() && my_open.getValue() < 8 )
		INSURANCE = true;
	else if(8 <= my_open.getValue() && my_open.getValue() < 14) 
		INSURANCE = false;
	
	return INSURANCE;
	}
	
	@Override
	public boolean do_surrender(Card my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
	boolean SURRENDER = false;
	int total_open_value = current_table.get(0).getCards().get(0).getValue()+current_table.get(1).getCards().get(0).getValue()+current_table.get(2).getCards().get(0).getValue()
							+current_table.get(3).getCards().get(0).getValue()+current_table.get(4).getCards().get(0).getValue();
	if(my_open.getValue() - dealer_open.getValue() <= -2)
		SURRENDER = true;
	else if(my_open.getValue() > (dealer_open.getValue() + 2))
		if(total_open_value/5 > my_open.getValue())
			SURRENDER = true;
		if(total_open_value/5 < my_open.getValue())
			SURRENDER = false;
	return SURRENDER;
	}
	
	
	@Override 
	public boolean do_double(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
	boolean DO_DOUBLE = false;
	int total = my_open.getCards().get(0).getValue() + my_open.getCards().get(1).getValue();
	if(total <= 7)
		DO_DOUBLE = true;
	else if(8 < total && total <=16)
		DO_DOUBLE = false;
	else if(16<total && total <22)
		DO_DOUBLE = true;
	return DO_DOUBLE;
	}
	
	@Override //skip split first, assume always no split
	public boolean do_split(java.util.ArrayList<Card> my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
	return false;
	}
	
	@Override
	public boolean hit_me(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
	boolean HIT_ME = false;
	int ttvalue = 0;
	for(int i = 0;i<(my_open).getCards().size();i++)
	{
	int j=0;
	if((my_open).getCards().get(i).getValue()>10)
		j = 10;
	else
		j = (my_open).getCards().get(i).getValue();
	ttvalue += j;
	}
	
	if(ttvalue<17)
		HIT_ME = true;
	else if(ttvalue>17)
		HIT_ME = false;
	
	return HIT_ME;
	}
	
	@Override
	public String toString(){
	
	return String.valueOf(get_chips());
	}
	
}