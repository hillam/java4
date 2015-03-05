import java.awt.Graphics;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.Arrays;

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

		int[] temps = {pair(),twoPair(),three(),straight(),flush(),
						fullHouse(),four(),straight() + flush()};

		for(int i : temps)
			if(i > score)
				score = i;

		return score;
	}

	private int four(){
		int score = 0;

		int[] values = new int[5];
		int[] matches = new int[5];
		int index = 0;
		for(int i=0;i<size && !Arrays.asList(matches).contains(3);i++){
			if(!Arrays.asList(values).contains(cards[i].getValue())){
				values[index] = cards[i].getValue();
				matches[index] = 0;
				index++;				
			}
			else
				matches[Arrays.asList(values).indexOf(cards[i].getValue())]++;
		}

		index = Arrays.asList(matches).indexOf(3);
		if(index > -1){
			score = 700 + values[index];
		}

		return score;
	}

	//done
	private int fullHouse(){
		int score = 0;

		if(three() > 300 && pair() > 100)
			score = 600 + highCard();

		return score;
	}

	//done
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
			score = 500 + highCard();

		return score;
	}

	//done
	private int straight(){
		int score = 0;
		boolean straight = true; //start assuming true, try to prove it wrong

		if(size > 0) //it should be, but just incase
			for(int i=0;i<size;i++)
				if(sorted[i].getValue() != sorted[0].getValue() + i)
					straight = false;

		if(straight)
			score = 400 + highCard();

		return score;
	}

	//done
	private int three(){
		int score = 0;

		int[] values = new int[5];
		int[] matches = new int[5];
		int index = 0;
		for(int i=0;i<size && !Arrays.asList(matches).contains(2);i++){
			if(!Arrays.asList(values).contains(cards[i].getValue())){
				values[index] = cards[i].getValue();
				matches[index] = 0;
				index++;				
			}
			else
				matches[Arrays.asList(values).indexOf(cards[i].getValue())]++;
		}

		index = Arrays.asList(matches).indexOf(2);
		if(index > -1){
			score = 300 + values[index];
		}

		return score;
	}

	//done
	private int twoPair(){
		int score = 0;

		int[] values = new int[5];
		boolean found = false;
		int index = 0;
		int match = -1;
		// loop through cards while !found
		// if values doesn't contain cards[i].getValue() AND match != cards[i].getValue(), add it and set match = cards[i].getValue()
		// else if match != -1, exit loop and score = 200 + match OR cards[i].getValue() (whichever is larger)
		for(int i=0;i<size && !found;i++){
			if(!Arrays.asList(values).contains(cards[i].getValue()) &&
					match != cards[i].getValue()){
				values[index] = cards[i].getValue();
				match = cards[i].getValue();
				index++;
			}
			else if(match != -1){
				found = true;
				score = 200;
				score += (match > cards[i].getValue() ? match : cards[i].getValue());
			}
		}

		return score;
	}

	//done
	private int pair(){
		int score = 0;

		int[] values = new int[5];
		boolean found = false;
		int index = 0;

		// loop through cards while !found
		// if values doesn't contain cards[i].getValue(), add it
		// else, exit loop and score = 100 + cards[i].getValue()
		for(int i=0;i<size && !found;i++){
			if(!Arrays.asList(values).contains(cards[i].getValue())){
				values[index] = cards[i].getValue();
				index++;
			}
			else{
				found = true;
				score = 100 + cards[i].getValue();
			}
		}

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