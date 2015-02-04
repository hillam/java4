public class Card{

	private int number;
	private int value;
	private String name;

	private final int[] VALUES = {11,2,3,4,5,6,7,8,9,10,10,10,10};
		/* (int)(x%13) */
	private final String[] NAMES = {"Ace","2","3","4","5","6","7","8","9","10",
									"Jack","Queen","King"};
	private final String[] SUITS = {"Clubs","Hearts","Spades","Diamonds"};
		/* (int)(x/52.0/.25) */

	public Card(int number){
		this.number = number;
		value = VALUES[(number%13)];
		name = NAMES[(number%13)];
		name += " of " + SUITS[(int)(number/52.0/0.25)];
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