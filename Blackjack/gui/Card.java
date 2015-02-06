import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Card{

	private int number;
	private int value;
	private String symbol;
	private int suit;
	private String name;

	private ImageIcon hole = new ImageIcon("images/cardback.jpg");
	private ImageIcon club = new ImageIcon("images/club.jpg");
	private ImageIcon heart = new ImageIcon("images/heart.jpg");
	private ImageIcon spade = new ImageIcon("images/spade.jpg");
	private ImageIcon diamond = new ImageIcon("images/diamond.jpg");

	private final int[] VALUES = {11,2,3,4,5,6,7,8,9,10,10,10,10};
		/* (int)(x%13) */
	private final String[] NAMES = {"Ace","2","3","4","5","6","7","8","9","10",
									"Jack","Queen","King"};	
	private final String[] SYMBOLS = {"A","2","3","4","5","6","7","8","9","10",
									"J","Q","K",};
	private final String[] SUITS = {"Clubs","Hearts","Spades","Diamonds"};
		/* (int)(x/52.0/.25) */

	public Card(int number){
		this.number = number;
		value = VALUES[(number%13)];
		name = NAMES[(number%13)];
		symbol = SYMBOLS[(number%13)];
		suit = (int)(number/52.0/0.25);
		name += " of " + SUITS[suit];
	}

	//down is true if the card is face down
	public void paint(JPanel j,Graphics g,int x,int y,boolean down){
		//paint the correct card at x and y
		if(down)
			hole.paintIcon(j,g,x,y);
		else{
			switch(suit){
				case 0:
					club.paintIcon(j,g,x,y);
					break;
				case 1:
					heart.paintIcon(j,g,x,y);
					break;
				case 2:
					spade.paintIcon(j,g,x,y);
					break;
				case 3:
					diamond.paintIcon(j,g,x,y);
					break;
			}

			//card: 115,175
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
			g.drawString(symbol,x + 5,y + 26);
			g.drawString(symbol,x + 80,y + 165);
		}
	}

	public int getValue(){
		return value;
	}

	public String getName(){
		return name;
	}

	public int getNumber(){
		return number;
	}
}