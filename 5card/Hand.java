import java.awt.Graphics;
import javax.swing.JPanel;
import java.lang.Math;

public class Hand{
	
	private Card[] cards;
	private Card[] sorted;
	private Deck deck;
	private int size;
	private boolean player;

	public Hand(boolean player){
		cards = new Card[5];
		sorted = new Card[5];
		deck = new Deck();
		size = 0;
		this.player = player;
	}

	public void reset(){
		cards = new Card[5];
		size = 0;
	}

	public void paint(JPanel j,boolean vertical,boolean up){
		j.removeAll();
		if(size > 0){
			cards[0].paint(j,vertical,up);
			for(int i=1;i<size;i++)
				cards[i].paint(j,vertical,true);
		}
	}

	public void setDeck(Deck deck){
		this.deck = deck;
	}

	public boolean drawCard(int n){
		for(int i=0;i<n;i++){
			if(size < cards.length){
				cards[size] = deck.drawCard();
				size ++;
			}
			else
				return false;
		}
		sort();
		return true;
	}

	private void sort(){
		for(int i=0;i<size;i++)
			sorted[i] = cards[i];
		Card[] temp = new Card[sorted.length];
		for(int i=0;i<size;i++){
			temp[i] = sorted[lowCard()];
			sorted[lowCard()] = null;
		}
		this.sorted = temp;
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

	public Card[] getCards(){
		return cards;
	}

	/*--------------------------------------------------------------------------
	Section is for computing hand ranking
	--------------------------------------------------------------------------*/

	// simply meant to compare the rank of two hands
	public int getScore(){
		int score = highCard();

		/*if(size > 0){
			int[] temps = {pair(),flush(),run(),runningFlush(),triple()};

			for(int i : temps)
				if(i > score)
					score = i;
		)*/

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

	private int twoPair(){
		int score = 0;

		int[] values = new int[5];
		boolean found = false;
		int match = -1;
		// loop through cards while !found
		// if values doesn't contain cards[i].getValue() AND match != cards[i].getValue(), add it and set match = cards[i].getValue()
		// else if match != -1, exit loop and score = 200 + match OR cards[i].getValue() (whichever is larger)

		return score;
	}

	private int pair(){
		int score = 0;

		int[] values = new int[5];
		boolean found = false;
		// loop through cards while !found
		// if values doesn't contain cards[i].getValue(), add it
		// else, exit loop and score = 100 + cards[i].getValue()

		return score;
	}

	public int highCard(){
		int value = 0;
		for(int i=0;i<size;i++)
			if(cards[i].getValue() > value)
				value = cards[i].getValue();

		return value;
	}

	/*--------------------------------------------------------------------------
	End hand ranking section
	--------------------------------------------------------------------------*/
}