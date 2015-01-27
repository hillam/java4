public class Hand{
	
	private Card[] cards;
	private Deck deck;
	private int size;
	private int score;

	public Hand(){
		cards = new Card[10];
		deck = new Deck();
		size = 0;
		score = 0;
	}

	public void setDeck(Deck deck){
		this.deck = deck;
	}

	public void drawCard(){
		cards[size] = deck.drawCard();
		score += cards[size].getValue();
		size ++;
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
}