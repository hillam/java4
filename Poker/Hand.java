import java.awt.Graphics;
import javax.swing.JPanel;
import java.lang.Math;

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
		aces = 0;
	}

	//hole is true if the hole card should be hidden
	public void paint(JPanel j,Graphics g,boolean hole){
		//paint each card. card: 115,175.. 2px margin is fine
		if(size > 0)	
			for(int i=0;i<size;i++)
				cards[i].paint(j,g,20+(i*117),y,hole);
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
		size ++;
		sort();
	}

	private void sort(){
		Card[] sorted = new Card[cards.length];
		for(int i=0;i<size;i++){
			sorted[i] = cards[lowCard()];
			cards[lowCard()] = null;
		}
		cards = sorted;
	}

	// used only for sort()
	private int lowCard(){
		int index = 0;
		int low = 100; // should be higher than any card value
		for(int i=0;i<size;i++)
			if(cards[i] != null)
				if(cards[i].getValue() < low){
					low = cards[i].getValue();
					index = i;
				}
		return index;
	}

	private void expand(){
		Card[] bigger = new Card[cards.length * 2];
		for(int i = 0;i < size;i++)
			bigger[i] = cards[i];
		cards = bigger;
	}

	public Card[] getCards(){
		return cards;
	}

	//
	//
	// Section is for computing hand ranking
	//
	//

	// simply meant to compare the rank of two hands
	public int getScore(){
		int score = highCard();

		if(size > 0){
			int[] temps = {pair(),flush(),run(),runningFlush(),triple()};

			for(int i : temps)
				if(i > score)
					score = i;
		}

		return score;
	}

	private int triple(){
		int score = 0;
		boolean triple = true;

		if(size > 0){
			Card c = cards[0];
			for(int i=1;i<size;i++)
				if(c.getValue() != cards[i].getValue())
					triple = false;
		}

		if(triple)
			score = 500 + highCard();

		return score;
	}

	private int runningFlush(){
		int score = 0;
		boolean rf = true;

		if(size > 0){
			Card c = cards[0];
			for(int i=1;i<size;i++)
				if(c.getSuit() != cards[i].getSuit())
					rf = false;
		}

		if(size == 3){ // it should... but just incase
			int first_diff = Math.abs(cards[0].getValue()-cards[1].getValue());
			int second_diff = Math.abs(cards[1].getValue()-cards[2].getValue());
			if(!(first_diff == 1 && second_diff == 1))
				rf = false;
		}

		if(rf)
			score = 400 + highCard();

		return score;
	}

	private int run(){
		int score = 0;
		boolean run = true;

		if(size == 3){ // it should... but just incase
			int first_diff = Math.abs(cards[0].getValue()-cards[1].getValue());
			int second_diff = Math.abs(cards[1].getValue()-cards[2].getValue());
			if(!(first_diff == 1 && second_diff == 1))
				run = false;
		}

		if(run)
			score = 300 + highCard();

		return score;
	}

	private int flush(){
		int score = 0;
		boolean flush = true;

		if(size > 0){
			Card c = cards[0];
			for(int i=1;i<size;i++)
				if(c.getSuit() != cards[i].getSuit())
					flush = false;
		}

		if(flush)
			score = 200 + highCard();

		return score;
	}

	private int pair(){
		int score = 0;

		if(cards[0].getValue() == cards[1].getValue() ||
				cards[1].getValue() == cards[2].getValue())
			score = 100 + cards[1].getValue();
		else if(cards[0].getValue() == cards[2].getValue())
			score = 100 + cards[0].getValue();

		return score;
	}

	// called by main program to see if the house qualifies (queen or higher)
	public int highCard(){
		int value = 0;
		for(int i=0;i<size;i++)
			if(cards[i].getValue() > value)
				value = cards[i].getValue();

		return value;
	}

	//
	//
	// End hand ranking section
	//
	//

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