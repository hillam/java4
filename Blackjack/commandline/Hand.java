public class Hand{
	
	private Card[] cards;
	private Deck deck;
	private int size;
	private int score;
	private int aces;

	public Hand(){
		cards = new Card[10];
		deck = new Deck();
		size = 0;
		score = 0;
		aces = 0;
	}

	public void setDeck(Deck deck){
		this.deck = deck;
	}

	public void drawCard(){
		cards[size] = deck.drawCard();
		score += cards[size].getValue();
		if(cards[size].getValue() > 10)
			aces ++;
		size ++;

		//Aces become 1's if score is > 21
		int i = 0;
		while(score > 21 && i < aces){
			score -= 10;
			i++;
		}
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