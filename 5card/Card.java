import java.awt.*;
import javax.swing.*;

public class Card{

	private int number;
	private int value;
	private int suit;

	private final int[] VALUES = {2,3,4,5,6,7,8,9,10,11,12,13,14};
		/* (int)(x%13) */

	public Card(int number){
		this.number = number;
		value = VALUES[(number%13)];
		suit = (int)(number/52.0/0.25);
	}

	public void paint(JPanel j,boolean vertical, boolean up){
		
	}

	public int getValue(){
		return value;
	}

	public int getSuit(){
		return suit;
	}

	public int getNumber(){
		return number;
	}
}