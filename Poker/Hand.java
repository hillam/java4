import java.awt.Graphics;
import javax.swing.JPanel;

public class Hand{
	
	private Card[] cards;
	private Deck deck;
	private int size;
	private int score;
	private int aces;
	private int y;
	private boolean player;

	public Hand(boolean player){
		cards = new Card[10];
		deck = new Deck();
		size = 0;
		score = 0;
		aces = 0;
		this.player = player;

		//y coord for printing
		//house start at 20,70
		//player start at 20,360
		y = 70;
		if(player)
			y += 290;
	}

	public void reset(){
		cards = new Card[10];
		size = 0;
		score = 0;
		aces = 0;
	}

	//hole is true if the hole card should be hidden
	public void paint(JPanel j,Graphics g,boolean hole){
		//paint each card. card: 115,175.. 2px margin is fine
		if(size > 0){
			for(int i=0;i < size;i++)
				cards[i].paint(j,g,20+(i*117),y,hole);
		}
	}

	public void setDeck(Deck deck){
		this.deck = deck;
	}

	public void drawCard(){
		if(size < cards.length)
			cards[size] = deck.drawCard();
		else{
			//array out of bounds, so expand it, and then try again
			expand();
			drawCard();
		}
		score += cards[size].getValue();
		if(cards[size].getValue() > 10)
			aces ++;
		size ++;

		//Aces become 1's if score is > 21
		int i = 0;
		while(score > 21 && i < aces){
			score -= 10;
			i++;
			aces--;
		}
	}

	private void expand(){
		Card[] bigger = new Card[cards.length * 2];
		for(int i = 0;i < size;i++)
			bigger[i] = cards[i];
		cards = bigger;
	}

	public int getScore(){
		return score;
	}

	public Card[] getCards(){
		return cards;
	}

	public String toString(){
		String str = "";
		for(int i = 0; i < size; i++){
			str += cards[i].getName() + "\n";
		}
		str = str.substring(0,str.length()-1);
		return str;
	}

	//Print cards[] starting from index j
	public String toString(int j){
		String str = "";
		for(int i = j; i < size; i++){
			str += cards[i].getName() + "\n";
		}
		str = str.substring(0,str.length()-1);
		return str;
	}
}